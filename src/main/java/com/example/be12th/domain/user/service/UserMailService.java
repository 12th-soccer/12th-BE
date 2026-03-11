package com.example.be12th.domain.user.service;

import com.example.be12th.domain.user.presentation.dto.request.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class UserMailService {
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${spring.mail.username}")
    private String senderEmail;

    private String createCode(){
        return String.valueOf((int)(Math.random()*900000)+100000);
    }

    public void saveCode(String email, String code){
        redisTemplate.opsForValue()
                .set(email, code, 5, TimeUnit.MINUTES);
    }

    public void execute(EmailRequest emailRequest){

        String code = createCode();

        saveCode(emailRequest.getEmail(),code);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailRequest.getEmail());
        mailMessage.setFrom(senderEmail);
        mailMessage.setSubject("12th 이메일 인증");
        mailMessage.setText("인증코드 : " + code);

        javaMailSender.send(mailMessage);
    }


}
