package com.springsecurityproject.SpringSecurityproject.event.listener;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.springsecurityproject.SpringSecurityproject.entity.User;
import com.springsecurityproject.SpringSecurityproject.event.RegistrationCompleteEvent;
import com.springsecurityproject.SpringSecurityproject.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                +token;
        //Actual send VerificationEmail method can be added here.
        log.info("Click the link to verify your Account: {}",url);
    }
}
