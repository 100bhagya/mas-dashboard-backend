package com.mas.dashboard.entity;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MasterPassword {
    private String masterPassword = null;
    public String getMasterPassword() { return masterPassword; }
    public void setMasterPassword(String masterPassword) {this.masterPassword = masterPassword;}

    @Scheduled(fixedDelay = 10000)
    public void setMasterPasswordToNull(){
        this.masterPassword = null;
    }
}
