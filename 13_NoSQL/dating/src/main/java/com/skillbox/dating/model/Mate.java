package com.skillbox.dating.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Mate implements Serializable {

    private String fullName;

    private String registrationDate;

    private boolean promo;

    public Mate(){}

    public Mate(String registrationDate, String fullName){
        this.registrationDate = registrationDate;
        this.fullName = fullName;
        this.promo = false;
    }

    public Mate (String fromRedis){
        String[] params = fromRedis.split(",");
        this.registrationDate = params[0].substring(params[0].indexOf("=") + 1);
        this.fullName = params[1].substring(params[1].indexOf("=") + 1);
        this.promo = params[2].contains("true");
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate.toString();
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo){
        this.promo = promo;
    }

    @Override
    public String toString() {
        return "Mate{" +
                "registrationDate=" + registrationDate +
                ", fullName=" + fullName +
                ", promo=" + promo +
                "}";
    }
}
