//package com.sparta.community.user.controller;
//
//import com.sparta.community.user.service.MailService2;
//import com.sparta.community.user.service.UserService2;
//import jakarta.mail.MessagingException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.UnsupportedEncodingException;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class MailController2 {
//
//    private final MailService2 mailService2;
//    private final UserService2 userService2;
//
//    // email 중복 체크
//    @PostMapping("/signup/confirm-email/{email}")
//    @ResponseBody
//    public void checkEmail(@PathVariable("email") String email) {
//        userService2.checkEmail(email);
//    }
//
//    // 메일 전송
//    @PostMapping("/email/send-email")
//    public String sendEmail(@RequestBody String email) throws MessagingException, UnsupportedEncodingException {
//        String authcode = mailService2.sendEmail(email);
//        return authcode;
//    }
//
//    // 메일 인증코드 확인
//    @PostMapping("/email/confirm-authcode")
//    public void confirmAuthcode(@RequestBody String input) {
//        mailService2.confirmAuthcode(input);
//    }
//}
