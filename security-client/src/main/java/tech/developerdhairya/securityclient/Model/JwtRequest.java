package tech.developerdhairya.securityclient.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class JwtRequest {

    private String username;
    private String password;

}
