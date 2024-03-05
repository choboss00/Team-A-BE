package com.example.shipgofunding.user.service;

import com.example.shipgofunding.config.Redis.RedisUtils;
import com.example.shipgofunding.config.auth.PrincipalUserDetails;
import com.example.shipgofunding.config.errors.exception.Exception400;
import com.example.shipgofunding.user.repository.UserRepository;
import com.example.shipgofunding.user.request.UserRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class SendVerficationcode {

    private final JavaMailSender javaMailSender;
    private final RedisUtils redisUtils;
    private PrincipalUserDetails principalUserDetails;
    private UserRepository userRepository;
    public SendVerficationcode(JavaMailSender javaMailSender, RedisUtils redisUtils, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.redisUtils = redisUtils;
        this.userRepository =userRepository;
    }

    // 인증번호 이메일 보내기
    public void sendMail (UserRequest.SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException {
        String email = sendEmailRequestDTO.getEmail();
        if (!userRepository.existsByEmail(email)){
            throw new Exception400(null,"존재하는 사용자가 없습니다.");
        }

        // 코드 생성
        else {
        String code = generateCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("chee0630@naver.com"); //보내는사람
        helper.setTo(email); //받는사람
        helper.setSubject("이메일 인증 번호 입니다"); //메일제목
        helper.setText("5분 안에 입력해주세요\n"+ "이메일 인증코드: " + code);

        // Redis에 인증 코드 저장 (5분 동안 유지)
        redisUtils.setDataExpire(email, code, 60 * 5L);
        javaMailSender.send(mimeMessage);

        }
    }

    public void signupsendMail (UserRequest.SendEmailRequestDTO sendEmailRequestDTO) throws MessagingException {
        String email = sendEmailRequestDTO.getEmail();
        // 코드 생성
        String code = generateCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("chee0630@naver.com"); //보내는사람
        helper.setTo(email); //받는사람
        helper.setSubject("이메일 인증 번호 입니다"); //메일제목
        helper.setText("5분 안에 입력해주세요\n" + "이메일 인증코드: " + code);

        // Redis에 인증 코드 저장 (5분 동안 유지)
        redisUtils.setDataExpire(email, code, 60 * 5L);
        javaMailSender.send(mimeMessage);

    }
    public  void VerficationEmail(UserRequest.VerficationRequestDTO verficationRequestDTO){
        String email = verficationRequestDTO.getEmail();
        String Usercode = verficationRequestDTO.getUsercode();
        if(!Usercode.equals(redisUtils.getData(email))){
            throw new Exception400(null,"인증번호를 다시 입력해주세요");
        }
    }


    private String generateCode() {
        // 랜덤 인증번호 생성
        return UUID.randomUUID().toString().substring(0, 6);
    }
}


