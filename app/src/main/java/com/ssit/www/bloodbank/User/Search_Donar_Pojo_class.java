package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 28-02-2018.
 */

public class Search_Donar_Pojo_class {

    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("client_email")
    @Expose
    private String clientEmail;
    @SerializedName("client_contact")
    @Expose
    private String clientContact;
    @SerializedName("client_blood_group")
    @Expose
    private String clientBloodGroup;
    @SerializedName("client_gender")
    @Expose
    private String clientGender;
    @SerializedName("client_address")
    @Expose
    private String clientAddress;
    @SerializedName("client_city")
    @Expose
    private String clientCity;
    @SerializedName("client_state")
    @Expose
    private String clientState;
    @SerializedName("client_pic")
    @Expose
    private String clientPic;
    @SerializedName("donate_date")
    @Expose
    private String donateDate;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getClientBloodGroup() {
        return clientBloodGroup;
    }

    public void setClientBloodGroup(String clientBloodGroup) {
        this.clientBloodGroup = clientBloodGroup;
    }

    public String getClientGender() {
        return clientGender;
    }

    public void setClientGender(String clientGender) {
        this.clientGender = clientGender;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    public String getClientPic() {
        return clientPic;
    }

    public void setClientPic(String clientPic) {
        this.clientPic = clientPic;
    }

    public String getDonateDate() {
        return donateDate;
    }

    public void setDonateDate(String donateDate) {
        this.donateDate = donateDate;
    }

}
