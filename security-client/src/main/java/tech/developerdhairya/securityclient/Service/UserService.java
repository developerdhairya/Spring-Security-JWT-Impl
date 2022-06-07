package tech.developerdhairya.securityclient.Service;

import tech.developerdhairya.securityclient.Model.UserRegistration;
import tech.developerdhairya.securityclient.ResponseModel.UseRegistrationRes;

public interface UserService {

    public boolean registerUser(UserRegistration userRegistration);

}
