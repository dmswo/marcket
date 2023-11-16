package carrotMarcket.marcket.user.service;

import carrotMarcket.marcket.global.jwt.TokenProvider;
import carrotMarcket.marcket.user.entity.RefreshToken;
import carrotMarcket.marcket.user.entity.Users;
import carrotMarcket.marcket.user.exception.UserBusinessException;
import carrotMarcket.marcket.user.exception.UserExceptionCode;
import carrotMarcket.marcket.user.repository.RefreshTokenRepository;
import carrotMarcket.marcket.user.repository.UserRepository;
import carrotMarcket.marcket.user.request.TokenDto;
import carrotMarcket.marcket.user.request.TokenRequestDto;
import carrotMarcket.marcket.user.request.JoinRequest;
import carrotMarcket.marcket.user.request.LoginRequest;
import carrotMarcket.marcket.user.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserResponseDto signup(JoinRequest joinRequest) {
        if (userRepository.existsByLoginId(joinRequest.getLoginId())) {
            throw new UserBusinessException(UserExceptionCode.EXIST_USER);
        }

        Users user = joinRequest.toEntity(passwordEncoder.encode(joinRequest.getPassword()));
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public TokenDto login(LoginRequest loginRequest) {
        TokenDto tokenDto = null;
        try {
            // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());

            // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
            //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            tokenDto = tokenProvider.generateTokenDto(authentication);

            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);
        } catch (BadCredentialsException e) {
            throw new UserBusinessException(UserExceptionCode.BAD_CREDENTIALS);
        }

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new UserBusinessException(UserExceptionCode.INCORRECT_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new UserBusinessException(UserExceptionCode.LOGOUT_USER));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new UserBusinessException(UserExceptionCode.INCORRECT_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = null;
        if (tokenProvider.refreshTokenPeriodCheck(refreshToken.getValue())) {
            // 5-1. Refresh Token의 유효기간이 3일 미만일 경우 전체(Access / Refresh) 재발급
            tokenDto = tokenProvider.generateTokenDto(authentication);

            // 6. Refresh Token 저장소 정보 업데이트
            RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
            refreshTokenRepository.save(newRefreshToken);
        } else {
            // 5-2. Refresh Token의 유효기간이 3일 이상일 경우 Access Token만 재발급
            tokenDto = tokenProvider.createAccessToken(authentication);
        }

        // 토큰 발급
        return tokenDto;
    }
}
