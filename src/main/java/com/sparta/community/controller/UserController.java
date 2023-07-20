package com.sparta.community.controller;

import com.sparta.community.dto.ApiResponseDto;
import com.sparta.community.dto.AuthRequestDto;
import com.sparta.community.exception.RestApiException;
import com.sparta.community.jwt.JwtUtil;
import com.sparta.community.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/it/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/login-page")
    public String loginPage(){
        return "index";
    }

    @GetMapping("/signup-page")
    public String signupPage(){
        return "signup";
    }

    @GetMapping("/idCheck")
    public ResponseEntity<ApiResponseDto> idCheck(@RequestBody String username){
        userService.idCheck(username);
        System.out.println(username + "확인");
        return  ResponseEntity.status(201).body(new ApiResponseDto("아이디 중복확인 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signUp(@Valid @RequestBody AuthRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    //로그인
//    @PostMapping("/logins")
//    public ResponseEntity<ApiResponseDto> login(@RequestBody AuthRequestDto loginRequestDto, HttpServletResponse response) {
//        userService.login(loginRequestDto);
//        //JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
//        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(), loginRequestDto.getRole()));
//
//        return ResponseEntity.status(201).body(new ApiResponseDto("로그인 성공", HttpStatus.CREATED.value()));
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<RestApiException> handleException(MethodArgumentNotValidException ex) {
        RestApiException restApiException = new RestApiException(ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }
}