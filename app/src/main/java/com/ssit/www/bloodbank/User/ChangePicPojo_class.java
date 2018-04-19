package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 09-02-2018.
 */

public class ChangePicPojo_class {


    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("client_pic")
    public String client_pic;

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

    public String getClient_pic() {
        return client_pic;
    }

    public void setClient_pic(String client_pic) {
        this.client_pic = client_pic;
    }
}
