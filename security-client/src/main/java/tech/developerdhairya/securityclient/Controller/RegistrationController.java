package tech.developerdhairya.securityclient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.ResponseModel.UseRegistrationRes;
import tech.developerdhairya.securityclient.Service.UserService;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserRegistration registration){
        boolean output=userService.registerUser(registration);
        publisher.publishEvent();

    }

}
