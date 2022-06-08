package tech.developerdhairya.securityclient.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity @Data @NoArgsConstructor
public class VerificationToken {

    public VerificationToken(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
        this.expirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationTime(int expirationTime){
        Calendar calendar=Calendar.getInstance();
        //Setting calender time
        calendar.setTimeInMillis(new Date().getTime());
        //Adding EXPIRATION_TIME to total time
        calendar.add(Calendar.MINUTE,expirationTime);
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
