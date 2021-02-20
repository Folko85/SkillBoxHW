package com.skillbox.dating.model;

import java.io.Serializable;


public class Mate implements Serializable {

    private String fullName;

    private String registrationDate;

    private boolean promo;

    public Mate(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isPromo() {
        return promo;
    }

    public void setPromo(boolean promo){
        this.promo = promo;
    }

}
