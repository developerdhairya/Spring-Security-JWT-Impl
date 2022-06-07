package tech.developerdhairya.securityclient.Event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class RegistrationCompleteEvent extends ApplicationEvent {
    public RegistrationCompleteEvent(Object source) {
        super(source);
    }

    public RegistrationCompleteEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
