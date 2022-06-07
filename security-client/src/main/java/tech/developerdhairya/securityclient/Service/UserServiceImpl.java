package tech.developerdhairya.securityclient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.Repository.UserRepository;
import tech.developerdhairya.securityclient.ResponseModel.UseRegistrationRes;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean registerUser(UserRegistration userRegistration) {


        String password=userRegistration.getPassword();
        String encodedPassword=passwordEncoder.encode(password);

        UserEntity userEntity=new UserEntity();
        userEntity.setFirstname(userRegistration.getFirstname());
        userEntity.setLastName(userRegistration.getLastname());
        userEntity.setEmail(userRegistration.getEmail());
        userEntity.setRole("USER");
        userEntity.setPassword(encodedPassword);

        userRepository.save(userEntity);
        return true;
    }
}
