package tech.developerdhairya.securityclient.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChangePassword {

    private String email;
    private String currentPassword;
    private String confirmCurrentPassword;

}
