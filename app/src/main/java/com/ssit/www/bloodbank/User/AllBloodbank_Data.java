package com.ssit.www.bloodbank.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 19-02-2018.
 */

public class AllBloodbank_Data {

    @SerializedName("bb_state")
    @Expose
    private String bbState;
    @SerializedName("bb_city")
    @Expose
    private String bbCity;
    @SerializedName("bb_district")
    @Expose
    private String bbDistrict;
    @SerializedName("bb_name")
    @Expose
    private String bbName;
    @SerializedName("bb_address")
    @Expose
    private String bbAddress;
    @SerializedName("bb_pincode")
    @Expose
    private String bbPincode;
    @SerializedName("bb_contact")
    @Expose
    private String bbContact;

    public String getBbState() {
        return bbState;
    }

    public void setBbState(String bbState) {
        this.bbState = bbState;
    }

    public String getBbCity() {
        return bbCity;
    }

    public void setBbCity(String bbCity) {
        this.bbCity = bbCity;
    }

    public String getBbDistrict() {
        return bbDistrict;
    }

    public void setBbDistrict(String bbDistrict) {
        this.bbDistrict = bbDistrict;
    }

    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public String getBbAddress() {
        return bbAddress;
    }

    public void setBbAddress(String bbAddress) {
        this.bbAddress = bbAddress;
    }

    public String getBbPincode() {
        return bbPincode;
    }

    public void setBbPincode(String bbPincode) {
        this.bbPincode = bbPincode;
    }

    public String getBbContact() {
        return bbContact;
    }

    public void setBbContact(String bbContact) {
        this.bbContact = bbContact;
    }

}
