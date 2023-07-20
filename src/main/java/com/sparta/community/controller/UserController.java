package com.sparta.community.controller;

import com.sparta.community.dto.SignupRequestDto;
import com.sparta.community.dto.UserInfoDto;
import com.sparta.community.dto.UserUpdateDto;
import com.sparta.community.security.UserDetailsImpl;
import com.sparta.community.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅에 사용
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/signup-page";
        }

        userService.signup(requestDto);

        return "redirect:/api/login-page";
    }

    // 회원 관련 정보 받기
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

    // 회원정보 조회
    // AuthenticationPrincipal로 로그인한 정보 가져오기
    @GetMapping("/it/profiles/")
    public String findByUsername (@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
       // UserInfoDto userInfoDto = userService.findByUsername(username);
        userDetails.getUser();
        model.addAttribute("user", userDetails.getUser());
        return "view-user-info";

    }

    // 회원정보 수정
    // 프로필 창 띄어주는 컨트롤러 호출 redirect:~
    @PostMapping("/it/profiles/")
    public String updateUserInfo(@RequestBody UserUpdateDto userUpdateDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "redirect:/api/it/profiles";
    }

}

