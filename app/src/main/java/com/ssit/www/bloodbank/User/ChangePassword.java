package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 07-02-2018.
 */

public class ChangePassword  {

    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;

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
}
