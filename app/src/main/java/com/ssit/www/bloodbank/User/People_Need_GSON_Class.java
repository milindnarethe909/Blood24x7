package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android on 07-03-2018.
 */

public class People_Need_GSON_Class {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<People_Need_POJO_Class> data = null;

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

    public List<People_Need_POJO_Class> getData() {
        return data;
    }

    public void setData(List<People_Need_POJO_Class> data) {
        this.data = data;
    }
}
