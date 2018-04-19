package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android on 10-02-2018.
 */

public class allBlood_Bank_Pojo_class {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<AllBloodbank_Data> data = null;

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

    public List<AllBloodbank_Data> getData() {
        return data;
    }

    public void setData(List<AllBloodbank_Data> data) {
        this.data = data;
    }

}
