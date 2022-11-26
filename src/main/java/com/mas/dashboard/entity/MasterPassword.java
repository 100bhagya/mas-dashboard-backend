package com.mas.dashboard.entity;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MasterPassword {
    private String masterPassword = null;
    public String getMasterPassword() { return masterPassword; }
    public void setMasterPassword(String masterPassword) {this.masterPassword = masterPassword;}

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void setMasterPasswordToNull(){
        this.masterPassword = null;
    }
}
