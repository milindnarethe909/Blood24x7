package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android on 28-02-2018.
 */

public class Search_Donar_GSON_Class {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Search_Donar_Pojo_class> data = null;

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

    public List<Search_Donar_Pojo_class> getData() {
        return data;
    }

    public void setData(List<Search_Donar_Pojo_class> data) {
        this.data = data;
    }

}
