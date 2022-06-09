package tech.developerdhairya.securityclient.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tech.developerdhairya.securityclient.Util.AuthenticationUtil;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity @Data @NoArgsConstructor
public class VerificationTokenEntity {

    @Autowired
    private static final int EXPIRATION_TIME=10;
    private static AuthenticationUtil util;


    public VerificationTokenEntity(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
        this.expirationTime=util.calculateExpirationTime(EXPIRATION_TIME);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private UserEntity userEntity;




}
