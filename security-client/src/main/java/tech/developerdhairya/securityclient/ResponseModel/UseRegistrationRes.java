package tech.developerdhairya.securityclient.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UseRegistrationRes {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;



}
