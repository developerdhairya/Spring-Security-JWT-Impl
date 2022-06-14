package tech.developerdhairya.securityclient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Entity.VerificationTokenEntity;
import tech.developerdhairya.securityclient.Model.ChangePassword;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.Repository.UserRepository;
import tech.developerdhairya.securityclient.Repository.VerificationTokenRepository;
import tech.developerdhairya.securityclient.Util.AuthenticationUtil;

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

    //enable user
    public String validateVerificationToken(String token) {
        VerificationTokenEntity verificationTokenEntity = verificationTokenRepository.findByToken(token);
        if (verificationTokenEntity == null) {
            return "Verification token is Invalid";
        }

        if (util.checkTokenExpiry(verificationTokenEntity)) {
            return "Token is expired";
        }


        UserEntity userEntity = verificationTokenEntity.getUserEntity();
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        verificationTokenRepository.delete(verificationTokenEntity);
        return "User validation Successful";

    }


    public String resendVerificationToken(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            return "No user exists with the given email-address";
        }
        if (userEntity.isEnabled()) {
            return "User has already been verified";
        }
        VerificationTokenEntity token = verificationTokenRepository.findByUserEntity(userEntity);

        if (!util.checkTokenExpiry(token)) {
            //DO mail user the same token
            return "The token has been resent to your registered email id";
        }

        //delete expired token
        verificationTokenRepository.delete(token);

        //create new token
        VerificationTokenEntity newToken = new VerificationTokenEntity(UUID.randomUUID().toString(), userEntity);
        verificationTokenRepository.save(newToken);

        //DO mail user the newly generated token.

        return "New Token has been sent to your registered email ID";
    }


    public String resetPassword(ChangePassword changePassword) {
        if (!changePassword.getCurrentPassword().equals(changePassword.getConfirmCurrentPassword())) {
            return "Passwords dont match";
        }
        String currentEncodedPassword = passwordEncoder.encode(changePassword.getCurrentPassword());
        UserEntity userEntity = userRepository.findByEmail(changePassword.getEmail());
        if (userEntity.getPassword().equals(currentEncodedPassword)) {
            String newEncodedPassword = passwordEncoder.encode(changePassword.getCurrentPassword());
            userEntity.setPassword(newEncodedPassword);
            userRepository.save(userEntity);
            return "Success";
        }
        return "Current Password is invalid";

    }


}
