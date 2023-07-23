package com.sparta.community.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.community.common.jwt.JwtUtil;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.user.dto.SignupRequestDto;
import com.sparta.community.user.dto.UserInfoDto;
import com.sparta.community.user.service.KakaoService;
import com.sparta.community.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅에 사용
@Controller
@RequestMapping("/it/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup-page")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/it/users/signup-page";
        }


        userService.signup(requestDto);

        return "redirect:/it/users/login-page";
    }

    // 회원 관련 정보 받기
    // 마이페이지 인가?
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();

        return new UserInfoDto(username);
    }

    // username 중복 체크
    @PostMapping("/signup/confirm-username/{username}")
    @ResponseBody
    public void checkUsername(@PathVariable("username") String username) {
        userService.checkUsername(username);
    }

    @GetMapping("/users/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";

    }



}