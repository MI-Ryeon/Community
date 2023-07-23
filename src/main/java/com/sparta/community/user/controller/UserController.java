package com.sparta.community.user.controller;

import com.sparta.community.common.dto.ApiResponseDto;
import com.sparta.community.common.security.UserDetailsImpl;
import com.sparta.community.user.dto.ProfileRequestDto;
import com.sparta.community.user.dto.ProfileResponseDto;
import com.sparta.community.user.dto.SignupRequestDto;
import com.sparta.community.user.dto.UserInfoDto;
import com.sparta.community.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/it/users")
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
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        String email = userDetails.getUser().getEmail();
        String oneLiner = userDetails.getUser().getOneLiner();
        String imgUrl = userDetails.getUser().getImgUrl();

        return new UserInfoDto(username, email, oneLiner, imgUrl);
    }

    // 프로필 보기
    @GetMapping("/profile")
    @ResponseBody
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.getUser().getId());
    }

    // 회원정보 수정
    // 프로필 창 띄어주는 컨트롤러 호출 redirect:~
    @PutMapping("/profile")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> updateProfile(@RequestBody ProfileRequestDto profileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userService.updateProfile(profileRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("프로필 수정 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 가능합니다", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/my-page")
    public String myPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        String email = userDetails.getUser().getEmail();
        String username = userDetails.getUsername();
        String oneLiner = userDetails.getUser().getOneLiner();

        model.addAttribute("email", email);
        model.addAttribute("username", username);
        model.addAttribute("oneLiner", oneLiner);

        return "profile";
    }

    // username 중복 체크
    @PostMapping("/signup/confirm-username/{username}")
    @ResponseBody
    public void checkUsername(@PathVariable("username") String username) {
        userService.checkUsername(username);
    }
}