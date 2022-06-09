package tech.developerdhairya.securityclient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Event.RegistrationCompleteEvent;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.Service.UserService;
import tech.developerdhairya.securityclient.Service.UserServiceImpl;
import tech.developerdhairya.securityclient.Util.AuthenticationUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AuthenticationUtil util;

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserRegistration registration, HttpServletRequest httpServletRequest){
        UserEntity user=userService.registerUser(registration);
        publisher.publishEvent(new RegistrationCompleteEvent(user,util.getApplicationUrl(httpServletRequest)));
        return true;
    }


    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        return userService.validateVerificationToken(token);
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("email") String email){
        return userService.resendVerificationToken(email);
    }

//    @GetMapping("/resetPassword")
//    public String resetPassword(@RequestBody)





}
