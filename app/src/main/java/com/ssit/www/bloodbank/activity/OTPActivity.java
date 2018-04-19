package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.OTP_POJO_CLASS;
import com.ssit.www.bloodbank.User.Response_OTP;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android on 08-01-2018.
 */

public class OTPActivity extends AppCompatActivity {

    private static final String TAG = OTPActivity.class.getSimpleName();
    private EditText ed_otp;
    private Button bt_submitOTP;
    private String otp = null;

    private TextView tv_time_count;

    TextView tv_resendotp;

    AlertDialog dialog;
    TextView tv_name_alert;
    ImageView image_alert;

    int MY_SOCKET_TIMEOUT_MS = 30000;

    ApiInterface apiInterface;
    Boolean result;

    private static CountDownTimer countDownTimer;
    private Runnable runnable;
    private long milliSeconds = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        result = CheckInternetConnection.onInternetCheck(OTPActivity.this);

        ed_otp = (EditText) findViewById(R.id.ed_otp);

        bt_submitOTP = (Button) findViewById(R.id.bt_submitOTP);

        tv_resendotp = (TextView) findViewById(R.id.tv_resendotp);

        tv_time_count=(TextView)findViewById(R.id.tv_time_count);


        timerStart();

        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.login_sucss,null,false);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);

        tv_resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result) {
                    resendOTP();
                } else {
                    showToast("Cannot connect to Internet...Please check your connection!");
                }
            }
        });

        bt_submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                otp = ed_otp.getText().toString().trim();

                Bundle bundle = getIntent().getExtras();
                String st_otp = BBSharedPreferenceManager.getOTP("c_otp", getApplicationContext());

                Log.d(TAG, "Registration OTP = " + st_otp);

                if (TextUtils.isEmpty(otp)) {
                    ed_otp.setError("OTP is reqiured");
                } else if ((otp.length() < 6 || otp.length() > 6)) {
                    ed_otp.setError("OTP minimun 6 charater");
                } else if (!(otp.equals(st_otp))) {
                    ed_otp.setError("OTP is nat match");
                } else {

                    if (result) {
                        sendOTP();
                    } else {
                        showToast("Cannot connect to Internet...Please check your connection!");
                    }
                }

            }
        });
    }

    private void resendOTP() {
        ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Sending OTP.. ", false);

        Call<Response_OTP> response_otpCall = apiInterface.RESPONSE_OTP_CALL(BBSharedPreferenceManager.getClientID("c_id", getApplicationContext()), "123456");
        response_otpCall.enqueue(new Callback<Response_OTP>() {
            @Override
            public void onResponse(Call<Response_OTP> call, Response<Response_OTP> response) {
                Log.d(TAG, "Resopns OTP = " + response);

                Response_OTP response_otp = response.body();

                String status = null, message = null, client_id = null, otp = null;

                status = response_otp.getStatus();
                message = response_otp.getMessage();
                client_id = response_otp.getClient_id();
                otp = response_otp.getOtp();

                if (status.equals("200Ok") && message.equals("Successfully Send")) {

                 //   showToast("Successfully Send");
                    timerStart();
                    BBSharedPreferenceManager.setClientID("c_id", client_id, getApplicationContext());
                    BBSharedPreferenceManager.setOTP("c_otp", otp, getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<Response_OTP> call, Throwable t) {
                String msg = t.getMessage();
                Log.d(TAG, "On Failure Message = " + msg);
           //     showToast(msg);

            }
        });
    }

    private void sendOTP() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please Wait...", false);

        Call<OTP_POJO_CLASS> otp_pojo_classCall = apiInterface.OTP_POJO_CLASS_CALL(BBSharedPreferenceManager.getClientID("c_id", this));
        otp_pojo_classCall.enqueue(new Callback<OTP_POJO_CLASS>() {
            @Override
            public void onResponse(Call<OTP_POJO_CLASS> call, Response<OTP_POJO_CLASS> response) {
                Log.d(TAG, "RESPONSE = " + response);

                progressDialog.dismiss();

                OTP_POJO_CLASS otp_pojo_class = response.body();

                String status = null, message = null, client_id = null,
                        client_name = null, client_email = null, client_contact = null,
                        client_blood_group = null, client_gender = null, client_address = null,
                        client_city = null, client_state = null, client_image = null, donate_expiry = null,
                        client_verification_status = null;

                status = otp_pojo_class.getStatus();
                message = otp_pojo_class.getMessage();
                client_id = otp_pojo_class.getClient_id();
                client_name = otp_pojo_class.getClient_name();
                client_email = otp_pojo_class.getClient_email();
                client_contact = otp_pojo_class.getClient_contact();
                client_blood_group = otp_pojo_class.getClient_blood_group();
                client_gender = otp_pojo_class.getClient_gender();
                client_address = otp_pojo_class.getClient_address();
                client_city = otp_pojo_class.getClient_city();
                client_state = otp_pojo_class.getClient_state();
                client_image = otp_pojo_class.getClient_image();
                donate_expiry = otp_pojo_class.getDonate_expiry();
                client_verification_status = otp_pojo_class.getClient_verification_status();

                Log.d(TAG, "id = " + client_id + " Full Name = " + client_name + " Email = " + client_email +
                        " Gender =" + client_gender + " Blood Gp = " + client_blood_group + " Mobile = " + client_contact +
                        " Address = " + client_address + " City = " + client_city + " State =  " + client_state + " donate_expiry = " + donate_expiry);


                if (status.equals("200Ok") && message.equals("Successfully verified")) {
                    BBSharedPreferenceManager.setClientID("c_id", client_id, getApplicationContext());
                    BBSharedPreferenceManager.setName("c_name", client_name, getApplicationContext());
                    BBSharedPreferenceManager.setEmail("c_email", client_email, getApplicationContext());
                    BBSharedPreferenceManager.setMobile("c_mobile", client_contact, getApplicationContext());
                    BBSharedPreferenceManager.setBloodGp("c_bloodgp", client_blood_group, getApplicationContext());
                    BBSharedPreferenceManager.setGender("c_gender", client_gender, getApplicationContext());
                    BBSharedPreferenceManager.setAddress("c_address", client_address, getApplicationContext());
                    BBSharedPreferenceManager.setCity("c_city", client_city, getApplicationContext());
                    BBSharedPreferenceManager.setState("c_state", client_state, getApplicationContext());
                    BBSharedPreferenceManager.setProfile("c_profile", client_image, getApplicationContext());
                    BBSharedPreferenceManager.setExpireDate("c_expiry", donate_expiry, getApplicationContext());

                    startActivity(new Intent(OTPActivity.this, DashBoard.class));
                    finish();



                }

            }

            @Override
            public void onFailure(Call<OTP_POJO_CLASS> call, Throwable t) {
                progressDialog.dismiss();

                String msg = t.getMessage();
                Log.d(TAG, "On Failure Message = " + msg);
          //      showToast(msg);

            }
        });


    }


    private void timerStart() {
        if (countDownTimer == null) {
            String getMinutes = "1";//Get minutes from edittexf
            //Check validation over edittext
            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds

                startTimer(noOfMinutes);//start countdown
             //   tv_time_count.setText(getString(R.string.stop_timer));//Change Text

            } else
                Toast.makeText(OTPActivity.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
        } else {
            //Else stop timer and change text
            stopCountdown();
           // startTimer.setText(getString(R.string.start_timer));
        }

    }

    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                tv_time_count.setText(hms);//set text
            }

            public void onFinish() {

                tv_time_count.setText(""); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
//                tv_time_count.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(OTPActivity.this,DashBoard.class));
        finish();
    }
}
