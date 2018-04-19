package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 07-02-2018.
 */

public class ForgetEmailPojo {

    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("client_id")
    public String client_id;
    @SerializedName("otp")
    public String otp;
    @SerializedName("client_contact")
    public String client_contact;
    @SerializedName("client_email")
    public String client_email;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(String client_contact) {
        this.client_contact = client_contact;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }
}
