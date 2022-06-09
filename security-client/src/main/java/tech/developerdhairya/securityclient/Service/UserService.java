package tech.developerdhairya.securityclient.Service;

import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Model.UserRegistration;

public interface UserService {

    UserEntity registerUser(UserRegistration userRegistration);

    void saveUserVerfificationToken(String token, UserEntity userEntity);
}
