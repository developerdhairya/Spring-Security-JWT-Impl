package tech.developerdhairya.securityclient.Service;

import tech.developerdhairya.securityclient.Entity.UserEntity;
import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.ResponseModel.UseRegistrationRes;

public interface UserService {

    public UserEntity registerUser(UserRegistration userRegistration);

    void saveUserVerfificationToken(String token, UserEntity userEntity);
}
