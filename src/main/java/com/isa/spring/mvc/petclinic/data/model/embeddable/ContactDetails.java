package com.isa.spring.mvc.petclinic.data.model.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;

@Embeddable
public class ContactDetails {
    @Column
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @Column
    @Digits(fraction = 0, integer = 10)
    private String mobilePhone;

    @Column
    private String emailAddress;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
