package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 10-02-2018.
 */

public class Response_OTP {

    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("client_id")
    public String client_id;
    @SerializedName("otp")
    public String otp;

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
}
