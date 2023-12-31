package com.sparta.community.user.controller;

import com.sparta.community.user.dto.AuthcodeDto;
import com.sparta.community.user.service.MailService;
import com.sparta.community.user.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/it/users")
public class MailController {

    private final MailService mailService;
    private final UserService userService;

    // email 중복 체크
    @PostMapping("/signup/confirm-email/{email}")
    @ResponseBody
    public void checkEmail(@PathVariable("email") String email) {
        userService.checkEmail(email);
    }

    // 메일 전송
    @PostMapping("/email/send-email")
    public String sendEmail(@RequestBody String email) throws MessagingException, UnsupportedEncodingException {
        String authcode = mailService.sendEmail(email);
        return authcode;
    }

    // 메일 인증코드 확인
    @PostMapping("/email/confirm-authcode")
    public void confirmAuthcode(@RequestBody AuthcodeDto authcodeDto) {
        mailService.confirmAuthcode(authcodeDto);
    }
}
