package tech.developerdhairya.securityclient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Event.RegistrationCompleteEvent;
import tech.developerdhairya.securityclient.Model.ChangePassword;
import tech.developerdhairya.securityclient.Model.JwtRequest;
import tech.developerdhairya.securityclient.Model.JwtResponse;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.Service.UserDetailsService;
import tech.developerdhairya.securityclient.Service.UserServiceImpl;
import tech.developerdhairya.securityclient.Util.AuthenticationUtil;
import tech.developerdhairya.securityclient.Util.JWTUtility;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AuthenticationUtil util;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailsService userDetailsService;

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

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ChangePassword changePassword){
        return userService.resetPassword(changePassword);
    }


    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails
                = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);

    }

}
