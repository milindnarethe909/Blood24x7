package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 13-01-2018.
 */

public class RegistrationPojo {

    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;

    @SerializedName("client_id")
    String id;

    @SerializedName("otp")
    String otp;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
