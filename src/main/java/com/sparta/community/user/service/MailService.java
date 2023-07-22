package com.sparta.community.user.service;

import com.sparta.community.user.dto.AuthcodeDto;
import com.sparta.community.user.entity.SignupAuth;
import com.sparta.community.user.repository.SignupAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
@Transactional
public class MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final SignupAuthRepository signupAuthRepository;
    private String authcode;

    // 인증 코드 생성
    public String createCode() {

        StringBuffer str = new StringBuffer("");

        // 랜덤 숫자 생성
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            str.append(random);
        }

        return str.toString();
    }

    // 메일 양식 생성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        authcode = createCode(); // 코드 생성

        // 보내는 사람
        String setFrom = "chaghani9898@gmail.com";
        // 받는 사람
        String toEmail = email;
        // 제목
        String subject = "[인증번호] 회원가입 인증 번호";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
        message.setSubject(subject); // 제목 설정
        message.setFrom(setFrom); // 보내는 이메일 설정
        message.setText(setContext(authcode), "utf-8", "html");

        return message;
    }

    // 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        // 메일 전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        // 실제 메일 전송
        emailSender.send(emailForm);
        return authcode; // 인증코드 반환
    }


    // context 설정
    private String setContext(String authcode) {
        Context context = new Context();
        context.setVariable("authcode", authcode);

        return templateEngine.process("mail", context); // mail.html 호출
    }

    // 1. 인증 완료 시 인증 된 이메일만 넣어두는 entity (SignupAuth)를 저장한다.
    // 2. 회원가입 하려는 이메일을 SignupAuth 에서 findbyemail로 찾는다.
    // 3. findbyemail 값이 없을 때, 회원가입을 못하게 에러메세지를 보낸다.
    // 4. else문에 완료 코드 작성
    public void confirmAuthcode(AuthcodeDto authcodeDto) {
        if (!authcode.equals(authcodeDto.getAuthCode())) {
            throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");
        }
        signupAuthRepository.save(new SignupAuth(authcodeDto.getEmail()));
//        if(signupAuthRepository.findByEmail(email).ifPresent()) {
//            throw new IllegalArgumentException("이메일 인증을 해주세요.");
//        }
    }
}
