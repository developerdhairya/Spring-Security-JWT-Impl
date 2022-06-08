package tech.developerdhairya.securityclient.Event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import tech.developerdhairya.securityclient.Entity.UserEntity;

import java.time.Clock;

@Getter @Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserEntity userEntity;
    private String applicationUrl;

    public RegistrationCompleteEvent(UserEntity userEntity,String applicationUrl) {
        super(userEntity);
        this.userEntity=userEntity;
        this.applicationUrl=applicationUrl;
    }



}
