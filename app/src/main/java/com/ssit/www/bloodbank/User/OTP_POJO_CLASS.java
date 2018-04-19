package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 14-02-2018.
 */

public class OTP_POJO_CLASS {


    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("client_id")
    public String client_id;
    @SerializedName("client_name")
    public String client_name;
    @SerializedName("client_email")
    public String client_email;
    @SerializedName("client_contact")
    public String client_contact;
    @SerializedName("client_blood_group")
    public String client_blood_group;
    @SerializedName("client_gender")
    public String client_gender;
    @SerializedName("client_address")
    public String client_address;
    @SerializedName("client_city")
    public String client_city;
    @SerializedName("client_state")
    public String client_state;
    @SerializedName("client_image")
    public String client_image;
    @SerializedName("donate_expiry")
    public String donate_expiry;
    @SerializedName("client_verification_status")
    public String client_verification_status;

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

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_contact() {
        return client_contact;
    }

    public void setClient_contact(String client_contact) {
        this.client_contact = client_contact;
    }

    public String getClient_blood_group() {
        return client_blood_group;
    }

    public void setClient_blood_group(String client_blood_group) {
        this.client_blood_group = client_blood_group;
    }

    public String getClient_gender() {
        return client_gender;
    }

    public void setClient_gender(String client_gender) {
        this.client_gender = client_gender;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getClient_city() {
        return client_city;
    }

    public void setClient_city(String client_city) {
        this.client_city = client_city;
    }

    public String getClient_state() {
        return client_state;
    }

    public void setClient_state(String client_state) {
        this.client_state = client_state;
    }

    public String getClient_image() {
        return client_image;
    }

    public void setClient_image(String client_image) {
        this.client_image = client_image;
    }

    public String getDonate_expiry() {
        return donate_expiry;
    }

    public void setDonate_expiry(String donate_expiry) {
        this.donate_expiry = donate_expiry;
    }

    public String getClient_verification_status() {
        return client_verification_status;
    }

    public void setClient_verification_status(String client_verification_status) {
        this.client_verification_status = client_verification_status;
    }
}
