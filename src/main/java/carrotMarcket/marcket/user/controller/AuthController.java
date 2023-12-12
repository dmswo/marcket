package carrotMarcket.marcket.user.controller;

import carrotMarcket.marcket.global.response.ApiResponse;
import carrotMarcket.marcket.user.request.TokenDto;
import carrotMarcket.marcket.user.request.TokenRequestDto;
import carrotMarcket.marcket.user.request.JoinRequest;
import carrotMarcket.marcket.user.request.LoginRequest;
import carrotMarcket.marcket.user.response.UserResponseDto;
import carrotMarcket.marcket.user.response.myBoardDto;
import carrotMarcket.marcket.user.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(value = "/user", description = "유저 관련 API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ApiOperation(value="유저 회원가입 API", notes="유저가 회원가입을 진행합니다.")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid JoinRequest joinRequest) {
        UserResponseDto signup = authService.signup(joinRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(signup));
    }

    @PostMapping("/login")
    @ApiOperation(value="유저 로그인 API", notes="로그인 후 Access Token 및 Refresh Token을 받습니다.")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenDto login = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(login));
    }

    @PostMapping("/reissue")
    @ApiOperation(value="Token 재발급 API", notes="Token의 유효기간 만료 후 Token을 재발급 받습니다.")
    public ResponseEntity<ApiResponse> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto reissue = authService.reissue(tokenRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(reissue));
    }

    @GetMapping("/myBoardList")
    @ApiOperation(value="내가 쓴글 조회 API", notes="내가 등록한 게시글 전체를 조회합니다.")
    public ResponseEntity<?> myBoardList(@RequestParam Long userId) {
        List<myBoardDto> list = authService.myBoardList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(list));
    }
}