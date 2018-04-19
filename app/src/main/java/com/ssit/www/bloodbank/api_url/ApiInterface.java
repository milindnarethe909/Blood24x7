package com.ssit.www.bloodbank.api_url;

import com.ssit.www.bloodbank.User.ChangePassword;
import com.ssit.www.bloodbank.User.ChangePicPojo_class;
import com.ssit.www.bloodbank.User.ForgetEmailPojo;
import com.ssit.www.bloodbank.User.LoginPojoClass;
import com.ssit.www.bloodbank.User.OTP_POJO_CLASS;
import com.ssit.www.bloodbank.User.People_Need_GSON_Class;
import com.ssit.www.bloodbank.User.RegistrationPojo;
import com.ssit.www.bloodbank.User.Reminder_POJO;
import com.ssit.www.bloodbank.User.Response_OTP;
import com.ssit.www.bloodbank.User.Search_Donar_GSON_Class;
import com.ssit.www.bloodbank.User.allBlood_Bank_Pojo_class;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Android on 13-01-2018.
 */

public interface  ApiInterface {

    @FormUrlEncoded
    @POST("client_registration.php")
    Call<RegistrationPojo> doRegistrationBlod(@Field("full_name") String full_name, @Field("email_id") String email_id,
                                              @Field("contact") String contact, @Field("blood_group") String blood_group,
                                              @Field("gender") String gender, @Field("address") String address, @Field("city") String city,
                                              @Field("state") String state, @Field("password") String password);


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginPojoClass> doLogin(@Field("email_id") String email_id, @Field("password") String password);

    @FormUrlEncoded
    @POST("forgotpassword.php")
    Call<ForgetEmailPojo> doForgetEmail(@Field("client_email") String email_id);

    @FormUrlEncoded
    @POST("chengepassword.php")
    Call<ChangePassword> CHANGE_PASSWORD_CALL(@Field("client_id") String client_id,@Field("client_passwod") String client_passwod);

    @FormUrlEncoded
    @POST("changeprofilepic.php")
    Call<ChangePicPojo_class> CHANGE_PIC_POJO_CLASS_CALL(@Field("client_id") String client_id,@Field("image") String image,@Field("image_name") String image_name);

    @FormUrlEncoded
    @POST("resendotp.php")
    Call<Response_OTP> RESPONSE_OTP_CALL(@Field("client_id") String client_id,@Field("otp") String otp);

    @FormUrlEncoded
    @POST("search_blood_bank.php")
    Call<allBlood_Bank_Pojo_class> ALL_BLOOD_ITEM_CALL(@Field("state") String state, @Field("city") String city);

    @FormUrlEncoded
    @POST("confirmnumber.php")
    Call<OTP_POJO_CLASS>  OTP_POJO_CLASS_CALL(@Field("client_id") String client_id);

    @FormUrlEncoded
    @POST("remainder.php")
    Call<Reminder_POJO> REMINDER_POJO_CALL(@Field("client_id") String client_id,@Field("date") String date);

    @FormUrlEncoded
    @POST("donar_search.php")
    Call<Search_Donar_GSON_Class> SEARCH_DONAR_GSON_CLASS_CALL(@Field("client_id") String client_id,@Field("blood_group") String blood_group,@Field("state") String state,@Field("city") String city);

    @FormUrlEncoded
    @POST("people_in_need.php")
    Call<People_Need_GSON_Class> PEOPLE_NEED_GSON_CLASS_CALL(@Field("blood_group") String blood_group,@Field("state") String state,@Field("city") String city);
}
