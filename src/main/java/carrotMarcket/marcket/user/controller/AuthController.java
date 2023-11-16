package carrotMarcket.marcket.user.controller;

import carrotMarcket.marcket.global.response.ApiResponse;
import carrotMarcket.marcket.user.request.TokenDto;
import carrotMarcket.marcket.user.request.TokenRequestDto;
import carrotMarcket.marcket.user.request.JoinRequest;
import carrotMarcket.marcket.user.request.LoginRequest;
import carrotMarcket.marcket.user.response.UserResponseDto;
import carrotMarcket.marcket.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid JoinRequest joinRequest) {
        UserResponseDto signup = authService.signup(joinRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(signup));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenDto login = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(login));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto reissue = authService.reissue(tokenRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successResponse(reissue));
    }
}