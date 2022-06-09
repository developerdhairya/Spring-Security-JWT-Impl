package tech.developerdhairya.securityclient.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity @Data @NoArgsConstructor
public class VerificationTokenEntity {

    public VerificationTokenEntity(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
        this.expirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }


    private Date calculateExpirationTime(int expirationTime){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());       //Setting calender time
        calendar.add(Calendar.MINUTE,expirationTime);        //Adding EXPIRATION_TIME to total time
        return calendar.getTime();
    }

    private static final int EXPIRATION_TIME=10;


    //Entity Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private UserEntity userEntity;




}
