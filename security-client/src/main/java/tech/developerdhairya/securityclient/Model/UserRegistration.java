package tech.developerdhairya.securityclient.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserRegistration {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

}
