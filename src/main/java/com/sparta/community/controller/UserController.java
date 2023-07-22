package com.sparta.community.controller;

import com.sparta.community.dto.*;
import com.sparta.community.security.UserDetailsImpl;
import com.sparta.community.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/user/signup-page")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup-page";
        }

        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }

    // 회원 관련 정보 받기
    @GetMapping("/user/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();

        return new UserInfoDto(username);
    }

    // username 중복 체크
    @PostMapping("/user/signup/confirm-username/{username}")
    @ResponseBody
    public void checkUsername(@PathVariable("username") String username) {
        userService.checkUsername(username);
    }


    // 프로필 보기
    @GetMapping("/it/user/profile")
    @ResponseBody
    public ProfileResponseDto getProfile (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.getUser().getId());
    }

    // 회원정보 수정
    // 프로필 창 띄어주는 컨트롤러 호출 redirect:~
    @PutMapping("/it/user/profiles")
    @ResponseBody
    public ResponseEntity<ApiResult> updateProfile(@RequestBody ProfileRequestDto profileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try{
            userService.updateProfile(profileRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResult("프로필 수정 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResult("작성자만 수정 가능합니다", HttpStatus.BAD_REQUEST.value()));
        }
    }


    @GetMapping("/user/mypage")
    @ResponseBody
    public String mypage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        String email = userDetails.getUser().getEmail();
        String username = userDetails.getUsername();
        String oneLiner = userDetails.getUser().getOneLiner();

        model.addAttribute("email", email);
        model.addAttribute("username", username);
        model.addAttribute("oneLiner", oneLiner);

        return "profile";
    }


}

