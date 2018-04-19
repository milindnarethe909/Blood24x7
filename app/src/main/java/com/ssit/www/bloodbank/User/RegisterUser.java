package com.ssit.www.bloodbank.User;

/**
 * Created by Android on 03-01-2018.
 */

public class RegisterUser {

    private String fullname;
    private String email;
    private String mobile_no;
    private String address;
    private String city;
    private String state;
    private String password;
    private String bloodgroup;
    private String gender;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", password='" + password + '\'' +
                ", bloodgroup='" + bloodgroup + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
