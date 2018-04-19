package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.ChangePassword;
import com.ssit.www.bloodbank.User.ForgetEmailPojo;
import com.ssit.www.bloodbank.User.ForgetUser;
import com.ssit.www.bloodbank.User.Response_OTP;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android on 09-01-2018.
 */

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG=ForgetPassword.class.getSimpleName();

    private LinearLayout ll_first, ll_second, ll_three;
    private EditText ed_forgetEmail, ed_forgetOTP, ed_forgetPass, ed_forgetConf;

    private Button bt_forgetSubmit, bt_forgetOtp, bt_forgetPASS;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String otp = "100000", id = null;

    int MY_SOCKET_TIMEOUT_MS = 30000;

    ForgetUser forgetUser;

    ApiInterface apiInterface;

    Boolean result;

    private static String OTP = null;

    TextView tv_resend_otp;

    private static CountDownTimer countDownTimer;
    private Runnable runnable;
    private long milliSeconds = 0;
    TextView tv_time_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        forgetUser = new ForgetUser();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        result = CheckInternetConnection.onInternetCheck(ForgetPassword.this);

        tv_time_count=(TextView)findViewById(R.id.tv_time_count);

        ll_first = (LinearLayout) findViewById(R.id.ll_first);
        ll_second = (LinearLayout) findViewById(R.id.ll_second);
        ll_three = (LinearLayout) findViewById(R.id.ll_three);

        ed_forgetEmail = (EditText) findViewById(R.id.ed_forgetEmail);
        ed_forgetOTP = (EditText) findViewById(R.id.ed_forgetOTP);
        ed_forgetPass = (EditText) findViewById(R.id.ed_forgetpassword);
        ed_forgetConf = (EditText) findViewById(R.id.ed_forgetConf);

        bt_forgetSubmit = (Button) findViewById(R.id.bt_forgetSubmit);
        bt_forgetOtp = (Button) findViewById(R.id.bt_forgetSubmitOTP);
        bt_forgetPASS = (Button) findViewById(R.id.bt_forgetSubmitPASS);

        tv_resend_otp=(TextView)findViewById(R.id.tv_resend_otp);
        tv_resend_otp.setOnClickListener(this);

        bt_forgetSubmit.setOnClickListener(this);
        bt_forgetOtp.setOnClickListener(this);
        bt_forgetPASS.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_forgetSubmit:
             //   showToast("Submit");
                sendEmail();
                break;
            case R.id.bt_forgetSubmitOTP:
          //      showToast("Submit OTP");
                if (result) {
                    sendOTP();
                } else {
                    showToast("Cannot connect to Internet...Please check your connection!");
                }

                break;
            case R.id.bt_forgetSubmitPASS:
                if (result) {
                    showPassword();
                } else {
                    showToast("Cannot connect to Internet...Please check your connection!");
                }
            //    showToast("Final Submit");
                break;
            case R.id.tv_resend_otp:
                if (result){
                    showResendOtp();
                }else {
                    showToast("Cannot connect to Internet...Please check your connection!");
                }
        }
    }


    private void showPassword() {

        forgetUser.setPassword(ed_forgetPass.getText().toString().trim());
        forgetUser.setConfPass(ed_forgetConf.getText().toString().trim());

        if (TextUtils.isEmpty(forgetUser.getPassword())) {
            ed_forgetPass.setError("Password is required");
        } else if (TextUtils.isEmpty(forgetUser.getConfPass())) {
            ed_forgetConf.setError("Conf Password is required");
        } else if (forgetUser.getPassword().length() < 4) {
            ed_forgetPass.setError("Password length should minimum 4 charater");
        } else if (!(forgetUser.getPassword().equals(forgetUser.getConfPass()))) {
            ed_forgetPass.setError("Password is Same");
            ed_forgetConf.setError("Password is Same");
            ed_forgetPass.setText("");
            ed_forgetConf.setText("");
        } else {
            sendPASSServer();
        }

    }


    private void sendEmail() {

        forgetUser.setEmail(ed_forgetEmail.getText().toString().trim());

        if (TextUtils.isEmpty(forgetUser.getEmail())) {
            ed_forgetEmail.setError("Email is required");
        } else if (!forgetUser.getEmail().matches(emailPattern)) {
            ed_forgetEmail.setError("Email Id is Invalid");
        } else {
            if (result) {
                sendEmailServer();
            } else {
                showToast("Cannot connect to Internet...Please check your connection!");
            }

        }


    }


    private void sendEmailServer() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please Wait...", false);

        final Call<ForgetEmailPojo> forgetEmailPojoCall = apiInterface.doForgetEmail(forgetUser.getEmail());
        forgetEmailPojoCall.enqueue(new Callback<ForgetEmailPojo>() {
            @Override
            public void onResponse(Call<ForgetEmailPojo> call, Response<ForgetEmailPojo> response) {
                progressDialog.dismiss();

                ForgetEmailPojo forgetEmailPojo=response.body();
                Log.d(TAG,"Forget Email Response >>> "+response);

                String status=null,message=null,client_id=null,otp=null,client_contact=null,client_email=null;

                status=forgetEmailPojo.getStatus();
                message=forgetEmailPojo.getMessage();
                client_id=forgetEmailPojo.getClient_id();
                otp=forgetEmailPojo.getOtp();
                client_contact=forgetEmailPojo.getClient_contact();
                client_email=forgetEmailPojo.getClient_email();

                Log.d(TAG,"Status = "+status+" Message = "+message+" Client_id = "+client_id+" OTP = "+otp);

                if (status.equals("200Ok")&&message.equals("Successfully Sent")){
                    BBSharedPreferenceManager.setClientID("c_id",client_id,getApplicationContext());
                    OTP=otp;
                    BBSharedPreferenceManager.setEmail("c_email",client_email,getApplicationContext());
                    BBSharedPreferenceManager.setMobile("c_mobile",client_contact,getApplicationContext());
                    ll_first.setVisibility(View.GONE);
                    ll_second.setVisibility(View.VISIBLE);
                    timerStart();
                //    showToast("Successfully Sent");
//                    showToast(""+otp);
                }else if (status.equals("200Ok")&&message.equals("email not registered")){
                    showToast("Email Not Registered");
                    ed_forgetEmail.setError("Email Id is Not Registered");
                }

            }

            @Override
            public void onFailure(Call<ForgetEmailPojo> call, Throwable t) {
                progressDialog.dismiss();
                String message = t.getMessage();
                Log.d("failure = ", message);
             //   showToast(message);

            }
        });


//        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading ", "Sending OTP", false);
//
//        StringRequest request = new StringRequest(Request.Method.POST, "http://wingsnagpur.com/blood_bank/forgotpassword.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                JSONObject jsonObject = null;
//
//                try {
//                    jsonObject = new JSONObject(response);
//
//                    String status = null, message = null;
//
//                    status = jsonObject.getString("status");
//                    message = jsonObject.getString("message");
//
//                    if (jsonObject.has("status") && jsonObject.has("message")) {
//                        if (status.equals("200Ok") && message.equals("Successfully Sent")) {
//                            id = jsonObject.getString("id");
//                            otp = jsonObject.getString("otp");
//
//                            BBSharedPreferenceManager.setClientID("cid", id, getApplicationContext());
//
//                            ll_first.setVisibility(View.GONE);
//                            ll_second.setVisibility(View.VISIBLE);
//                        }
//                        if (status.equals("200Ok") && message.equals("email is not Registered")) {
//                            showToast("Email is not Registered");
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//
//                String message = null;
//
//                if (error instanceof NetworkError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof ServerError) {
//                    message = "The server could not be found. Please try again after some time!!";
//                } else if (error instanceof AuthFailureError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof ParseError) {
//                    message = "Parsing error! Please try again after some time!!";
//                } else if (error instanceof NoConnectionError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof TimeoutError) {
//                    message = "Connection TimeOut! Please check your internet connection.";
//                }
//                showToast(message);
//
//
//            }
//        }) {
//
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put(KEY_EMAIL, forgetUser.getEmail());
//                return params;
//            }
//
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
    }


    private void sendOTP() {


        forgetUser.setOtp(ed_forgetOTP.getText().toString().trim());

        if (TextUtils.isEmpty(forgetUser.getOtp())) {
            ed_forgetOTP.setError("OTP is Required");
        } else if ((forgetUser.getOtp().length() < 6 || forgetUser.getOtp().length() > 6)) {
            ed_forgetOTP.setError("OTP is minimum 6 digit");
        } else if (!(OTP.equals(forgetUser.getOtp()))) {
            ed_forgetOTP.setError("Wrong OTP");
        } else {

            final ProgressDialog progressDialog=ProgressDialog.show(this,"Loading","Please wait...",false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    ll_second.setVisibility(View.GONE);
                    ll_three.setVisibility(View.VISIBLE);
                }
            },3000);
//            sendPASSServer();
        }


    }

    private void sendPASSServer() {

        final ProgressDialog dialog=ProgressDialog.show(this,"Loading","Please Wait...",false);


        Call<ChangePassword> changePasswordCall=apiInterface.CHANGE_PASSWORD_CALL(BBSharedPreferenceManager.getClientID("c_id",getApplicationContext()),forgetUser.getPassword());

        changePasswordCall.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                dialog.dismiss();

                Log.d(TAG,"Change Password >>>>>>>> "+response);

                ChangePassword changePassword=response.body();

                String status=null,message=null;

                status=changePassword.getStatus();
                message=changePassword.getMessage();

                Log.d(TAG,"Status = "+status+" Message = "+message);

                if (status.equals("200Ok")&&message.equals("Successfully Changed")){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    //showToast("Successfully Changed");
                    finish();

                }
                if (status.equals("200Ok")&&message.equals("oops! Please try again!")){
               //     showToast("oops! Please try again!");
                }

            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                dialog.dismiss();
                String failure=t.getMessage();
                Log.d(TAG,"Failure Message Change Password"+failure);
             //   showToast(failure);

            }
        });

    }


    private void showResendOtp() {
        final ProgressDialog progressDialog=ProgressDialog.show(this,"Loading","Please wait...",false);

        Call<Response_OTP> response_otpCall=apiInterface.RESPONSE_OTP_CALL(BBSharedPreferenceManager.getClientID("c_id",getApplicationContext()),OTP);
        response_otpCall.enqueue(new Callback<Response_OTP>() {
            @Override
            public void onResponse(Call<Response_OTP> call, Response<Response_OTP> response) {
                progressDialog.dismiss();
                Log.d(TAG,"Response OTP ... "+response);

                Response_OTP response_otp=response.body();

                String status=null,message=null,client_id=null,otp=null;

                status=response_otp.getStatus();
                message=response_otp.getMessage();
                client_id=response_otp.getClient_id();
                otp=response_otp.getOtp();

                Log.d(TAG,"Status = "+status+" Message = "+message+" Client_id = "+client_id+" OTP = "+otp);

                if (status.equals("200Ok")&&message.equals("Successfully Send")){
                    BBSharedPreferenceManager.setClientID("c_id",client_id,getApplicationContext());
                    OTP=otp;
                    timerStart();
                //    showToast("Successfully Send");
//                    ll_second.setVisibility(View.GONE);
//                    ll_three.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_OTP> call, Throwable t) {
                progressDialog.dismiss();
                String error=t.getMessage();
                //showToast(error);


            }
        });
    }


    private void showToast(String msg) {
        Toast.makeText(ForgetPassword.this, "" + msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ForgetPassword.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
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
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                tv_time_count.setText(hms);//set text
            }

            public void onFinish() {

                tv_time_count.setText(""); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
//                tv_time_count.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgetPassword.this,LoginActivity.class));
        finish();
    }
}
