package com.example.shipgofunding.user.service;

import com.example.shipgofunding.config.Redis.RedisUtils;
import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PasswordVerfication {

    private final JavaMailSender javaMailSender;
    private final RedisUtils redisUtils;
    private PrincipalUserDetails principalUserDetails;

    public PasswordVerfication(JavaMailSender javaMailSender, RedisUtils redisUtils) {
        this.javaMailSender = javaMailSender;
        this.redisUtils = redisUtils;
    }

    // 인증번호 이메일 보내기
    public void sendMail (String email) throws MessagingException {
        // 코드 생성

        String code = generateCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("chee0630@naver.com"); //보내는사람
        helper.setTo(email); //받는사람
        helper.setSubject("인증 번호 입니다"); //메일제목
        helper.setText("이메일 인증코드: " + code);

        // Redis에 인증 코드 저장 (5분 동안 유지)
        redisUtils.setDataExpire("chee0630@naver.com", code, 60 * 5L);
        javaMailSender.send(mimeMessage);
    }


    private String generateCode() {
        // 랜덤 인증번호 생성
        return UUID.randomUUID().toString().substring(0, 6);
    }
}


