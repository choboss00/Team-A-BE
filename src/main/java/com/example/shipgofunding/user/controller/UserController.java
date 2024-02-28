package com.example.shipgofunding.user.controller;

import com.example.shipgofunding.config.jwt.TokenProvider;
import com.example.shipgofunding.config.utils.ApiResponseBuilder;
import com.example.shipgofunding.user.request.UserRequest.LoginRequestDTO;
import com.example.shipgofunding.user.request.UserRequest.SignupRequestDTO;
import com.example.shipgofunding.user.response.UserResponse.LoginResponseWithTokenDTO;
import com.example.shipgofunding.user.response.UserResponse.LoginResponseDTO;
import com.example.shipgofunding.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;

import static com.example.shipgofunding.config.jwt.TokenProvider.TOKEN_PREFIX;


@Tag(name = "User", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDTO requestDTO, Errors errors) {
        userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.successWithNoContent());
    }

    @Operation(summary = "로그인", description = "로그인을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponseDTO.class)))
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO requestDTO, Errors errors, HttpServletResponse response) {
        //TO-DO : 로그인 로직 구현하기
        LoginResponseWithTokenDTO loginDTO = userService.login(requestDTO);

        String accessToken = TokenProvider.TOKEN_PREFIX + loginDTO.getToken();

        // jwt 헤더에 담기
        response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseBuilder.success(loginDTO.getLoginResponseDTO()));
    }

}