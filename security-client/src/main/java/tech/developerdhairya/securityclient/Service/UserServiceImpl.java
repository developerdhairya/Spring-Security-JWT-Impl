package tech.developerdhairya.securityclient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Entity.VerificationTokenEntity;
import tech.developerdhairya.securityclient.Model.ChangePassword;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.Repository.UserRepository;
import tech.developerdhairya.securityclient.Repository.VerificationTokenRepository;
import tech.developerdhairya.securityclient.Util.AuthenticationUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AuthenticationUtil util;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserEntity registerUser(UserRegistration userRegistration) {
        String password = userRegistration.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userRegistration.getFirstName());
        userEntity.setLastName(userRegistration.getLastName());
        userEntity.setEmail(userRegistration.getEmail());
        userEntity.setRole("USER");
        userEntity.setPassword(encodedPassword);
        return userRepository.save(userEntity);

    }

    @Override
    public void saveUserVerfificationToken(String token, UserEntity userEntity) {
        VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(token, userEntity);
        verificationTokenRepository.save(verificationTokenEntity);

    }

    public String validateVerificationToken(String token) {
        VerificationTokenEntity verificationTokenEntity = verificationTokenRepository.findByToken(token);
        if (verificationTokenEntity == null) {
            return "Verification token is Invalid";
        } else {
            UserEntity userEntity = verificationTokenEntity.getUserEntity();
            Calendar calendar = Calendar.getInstance();
            if (verificationTokenEntity.getExpirationTime().getTime() - calendar.getTime().getTime() >= 0) {
                userEntity.setEnabled(true);
                userRepository.save(userEntity);
                return "User validation Successful";
            }
            verificationTokenRepository.delete(verificationTokenEntity);
            return "Token is expired";
        }
    }

    public String resendVerificationToken(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity.isEnabled()) {
            return "Invalid Request";
        }
        VerificationTokenEntity token = verificationTokenRepository.findByUserEntity(userEntity);
        if (!util.checkTokenExpiry(token)) {
            Calendar calendar = Calendar.getInstance();
            token.setExpirationTime(calendar.getTime());
            verificationTokenRepository.save(token);
            //mail to user the token
            return "Token has been sent to your registered email ID";
        }
        VerificationTokenEntity verificationToken = new VerificationTokenEntity(UUID.randomUUID().toString(), userEntity);
        verificationTokenRepository.save(verificationToken);
        return "Success";
    }


    public String resetPassword(ChangePassword changePassword) {
        if(!changePassword.getCurrentPassword().equals(changePassword.getConfirmCurrentPassword())){
            return "Passwords dont match";
        }
        String currentEncodedPassword=passwordEncoder.encode(changePassword.getCurrentPassword());
        UserEntity userEntity=userRepository.findByEmail(changePassword.getEmail());
        if(userEntity.getPassword().equals(currentEncodedPassword)){
            String newEncodedPassword=passwordEncoder.encode(changePassword.getCurrentPassword());
            userEntity.setPassword(newEncodedPassword);
            userRepository.save(userEntity);
            return "Success";
        }
        return "Current Password is invalid";

    }


}
