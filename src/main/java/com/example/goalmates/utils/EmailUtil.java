package com.example.goalmates.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;
    public void sendMailWithLink(String sendTo, String link){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("GoalMates");
        message.setText("Click on to join GoalMates: " + link);
        mailSender.send(message);
    }

    public  void sendMailWithCode(String sendTo, String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("GoalMates");
        message.setText("Code "+ code);
        mailSender.send(message);
    }

    public void sendInvitationMail(String sendTo, String sendFrom){
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println(sendFrom);
        System.out.println(sendTo);
        message.setTo(sendTo);
        message.setSubject("GoalMates");
        message.setText(sendFrom + " invited you to join GoalMates");
        mailSender.send(message);
    }
}
