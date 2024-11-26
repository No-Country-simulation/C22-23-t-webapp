package com.SegundasHuellas.backend.pets;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class InitialData {



    @EventListener
    public void handleApplicationStartedEvent(ApplicationStartedEvent event) {


    }
}
