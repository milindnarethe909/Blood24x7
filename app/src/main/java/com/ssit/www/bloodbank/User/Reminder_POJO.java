package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 14-02-2018.
 */

public class Reminder_POJO {


    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("client_id")
    public String client_id;
    @SerializedName("client_date")
    public String client_date;
    @SerializedName("donate_expiry")
    public String donate_expiry;

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

    public String getClient_date() {
        return client_date;
    }

    public void setClient_date(String client_date) {
        this.client_date = client_date;
    }

    public String getDonate_expiry() {
        return donate_expiry;
    }

    public void setDonate_expiry(String donate_expiry) {
        this.donate_expiry = donate_expiry;
    }
}
