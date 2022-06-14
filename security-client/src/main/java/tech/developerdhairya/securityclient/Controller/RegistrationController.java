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
import tech.developerdhairya.securityclient.Service.SecurityService;
import tech.developerdhairya.securityclient.Service.UserServiceImpl;
import tech.developerdhairya.securityclient.Util.AuthenticationUtil;
import tech.developerdhairya.securityclient.Util.JWTUtil;

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
    private JWTUtil jwtUtil;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserRegistration registration, HttpServletRequest httpServletRequest) {
        try {
            UserEntity user = userService.registerUser(registration);
            publisher.publishEvent(new RegistrationCompleteEvent(user, util.getApplicationUrl(httpServletRequest)));
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }

    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        return userService.validateVerificationToken(token);
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("email") String email) {
        return userService.resendVerificationToken(email);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ChangePassword changePassword) {
        return userService.resetPassword(changePassword);
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        System.out.println("test");

        try {

            System.out.println(jwtRequest.getUsername()+jwtRequest.getPassword());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(),
                    jwtRequest.getPassword()
            );
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid credentials"+e);
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails
                = securityService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtil.generateToken(userDetails);

        System.out.println("moo");
        return new JwtResponse(token);

    }

}
