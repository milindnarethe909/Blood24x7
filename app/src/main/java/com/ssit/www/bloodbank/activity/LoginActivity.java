package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

import com.ssit.www.bloodbank.Manifest;
import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.LoginPojoClass;
import com.ssit.www.bloodbank.User.loginUser;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView tv_registration, tv_forget;
    private EditText ed_username, ed_password;
    private Button bt_signin;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    loginUser loginuser;
    int MY_SOCKET_TIMEOUT_MS = 30000;
    public int PERMISSION_ALL = 1;

    ApiInterface apiInterface;

    AlertDialog dialog;

    public  String[] PERMISSION={android.Manifest.permission.INTERNET,
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        
        if(!hasPremission(this,PERMISSION)){
            ActivityCompat.requestPermissions(this,PERMISSION,PERMISSION_ALL);
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        loginuser = new loginUser();

        tv_registration = (TextView) findViewById(R.id.tv_registration);
        tv_forget = (TextView) findViewById(R.id.tv_forget);

        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_password = (EditText) findViewById(R.id.ed_password);

        bt_signin = (Button) findViewById(R.id.bt_signin);

        tv_registration.setOnClickListener(this);
        tv_forget.setOnClickListener(this);

        bt_signin.setOnClickListener(this);
        
        

    }

    private boolean hasPremission(Context context, String[] Permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && Permission != null) {
            for (String permission : Permission) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;

                }

            }

        }
        return true;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_registration:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
            case R.id.tv_forget:
               // showToast("Forget Password");
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
//                finish();
                break;
            case R.id.bt_signin:
                signIn();
           //     showToast("Sign In");
                break;

            case R.id.bt_ok_alert:
                startActivity(new Intent(LoginActivity.this,DashBoard.class));
                finish();
                break;

        }

    }

    private void signIn() {

        loginuser.setUsername(ed_username.getText().toString().trim());
        loginuser.setPassword(ed_password.getText().toString().trim());

        if (TextUtils.isEmpty(loginuser.getUsername())) {
            ed_username.setError("Email is Required");
        } else if (TextUtils.isEmpty(loginuser.getPassword())) {
            ed_password.setError("Password is required");
        } else if (!loginuser.getUsername().matches(emailPattern)) {
            ed_username.setError("Email is Invalid");
        } else if (loginuser.getPassword().length() < 4) {
            ed_password.setError("Password length should minimum 4 charater");
        } else {

            boolean result = CheckInternetConnection.onInternetCheck(this);

            if (result) {
                Login();
          } else {
               showToast("Cannot connect to Internet...Please check your connection!");
            }
        }


    }

    private void Login() {


        // show it
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please Wait", false);

        Call<LoginPojoClass> loginPojoClassCall = apiInterface.doLogin(loginuser.getUsername(), loginuser.getPassword());

        loginPojoClassCall.enqueue(new Callback<LoginPojoClass>() {
            @Override
            public void onResponse(Call<LoginPojoClass> call, Response<LoginPojoClass> response) {

                progressDialog.dismiss();

                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.login_sucss,null,false);

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setView(view);
                builder.setCancelable(false);

                if (response.isSuccessful()) {
                    LoginPojoClass loginPojoClass = response.body();

                    Log.d(TAG, "Result >>>>>>>>>>>>>>>> " + response);

                    String status = null, message = null, client_id = null,
                            client_name = null, client_email = null, client_contact = null,
                            client_blood_group = null, client_gender = null, client_address = null,
                            client_city = null, client_state = null, client_image = null, donate_expiry = null,
                            client_verification_status = null;

                    status = loginPojoClass.getStatus();
                    message = loginPojoClass.getMessage();
                    client_id = loginPojoClass.getClient_id();
                    client_name = loginPojoClass.getClient_name();
                    client_email = loginPojoClass.getClient_email();
                    client_contact = loginPojoClass.getClient_contact();
                    client_blood_group = loginPojoClass.getClient_blood_group();
                    client_gender = loginPojoClass.getClient_gender();
                    client_address = loginPojoClass.getClient_address();
                    client_city = loginPojoClass.getClient_city();
                    client_state = loginPojoClass.getClient_state();
                    client_image = loginPojoClass.getClient_image();
                    donate_expiry = loginPojoClass.getDonate_expiry();
                    client_verification_status = loginPojoClass.getClient_verification_status();

                    Log.d(TAG, "id = " + client_id + " Full Name = " + client_name + " Email = " + client_email +
                            " Gender =" + client_gender + " Blood Gp = " + client_blood_group + " Mobile = " + client_contact +
                            " Address = " + client_address + " City = " + client_city + " State =  " + client_state + " donate_expiry = " + donate_expiry + " verification = " + client_verification_status);


                    if (status.equals("200Ok") && message.equals("Loged in Successfully") && client_verification_status.equals("verified")) {
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
                        BBSharedPreferenceManager.setLoginStatus("c_Login", "true", getApplicationContext());
                       // startActivity(new Intent(LoginActivity.this, DashBoard.class));
                      //  finish();



                        dialog=builder.create();
                        dialog.show();

                        Button ok=(Button)view.findViewById(R.id.bt_ok_alert);
                        ok.setOnClickListener(LoginActivity.this);


                    }
                    if (status.equals("200Ok") && message.equals("Loged in Successfully") && client_verification_status.equals("not verified")) {
                        startActivity(new Intent(LoginActivity.this, OTPActivity.class));
//                        showToast("Not verified");
                        finish();
                    }


                    if (status.equals("200Ok") && message.equals("Email and Password does not match")) {


                        showToast("Email and Password does not match");
                    }
                    if (status.equals("200Ok") && message.equals("Email not registred")) {
                        //showToast("Email Not Registred");
                        dialog=builder.create();
                        dialog.show();

                        Button ok=(Button)view.findViewById(R.id.bt_ok_alert);
                        ImageView image=(ImageView)view.findViewById(R.id.image_alert);
                        TextView textView=(TextView)view.findViewById(R.id.tv_name_alert);

                        image.setBackgroundResource(R.drawable.cancel);
                        textView.setText("Email and Password does not match");
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                }else {
                    try {
                        JSONObject object=new JSONObject(response.errorBody().string());
                        String msg=object.getString("message");
                        Log.d(TAG,"JSON OBJECT = "+msg);
                        showToast(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginPojoClass> call, Throwable t) {

                progressDialog.dismiss();

                Log.d(TAG, t.getMessage());

            }
        });


//        final ProgressDialog progressDialog = ProgressDialog.show(this, "Login User", "Please Wait", false);
//
//        StringRequest request = new StringRequest(Request.Method.POST,"http://ssinfotech.org/blood_bank/login.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                JSONObject jsonObject = null;
//
//                try {
//                    jsonObject = new JSONObject(response);
//                    Log.d(TAG,response);
//
//                    String status = null, message = null, id = null,
//                            fullname = null, email = null, mobile = null, gender = null,
//                            bloodgp = null, addrerss = null, city = null, state = null;
//
//                    if (jsonObject.has("status") && jsonObject.has("message") && jsonObject.has("id")) {
//                        if (status.equals("200Ok") && message.equals("Loged in Successfully")) {
//                            id = jsonObject.getString("id");
//                            fullname = jsonObject.getString("Name");
//                            email = jsonObject.getString("Email");
//                            mobile = jsonObject.getString("Mobile");
//                            gender = jsonObject.getString("Gender");
//                            bloodgp = jsonObject.getString("blood_group");
//                            addrerss = jsonObject.getString("address");
//                            city = jsonObject.getString("city");
//                            state = jsonObject.getString("state");
//
//                            BBSharedPreferenceManager.setClientID("cid", id, getApplicationContext());
//                            BBSharedPreferenceManager.setName("name", fullname, getApplicationContext());
//                            BBSharedPreferenceManager.setEmail("email", email, getApplicationContext());
//                            BBSharedPreferenceManager.setMobile("mobile", mobile, getApplicationContext());
//                            BBSharedPreferenceManager.setGender("gender", gender, getApplicationContext());
//                            BBSharedPreferenceManager.setBloodGp("bloodgp", bloodgp, getApplicationContext());
//                            BBSharedPreferenceManager.setAddress("address", addrerss, getApplicationContext());
//                            BBSharedPreferenceManager.setCity("city", city, getApplicationContext());
//                            BBSharedPreferenceManager.setState("state", state, getApplicationContext());
//
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//
//                        }
//                        if (status.equals("200Ok") && message.equals("Email and Password does not match")) {
//                            showToast("Email and Password does not match");
//                        }
//
//                    }
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
//                Log.d(TAG, String.valueOf(error));
//
//                String message = null;
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
//            }
//        }) {
//
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("KEY_EMAIL", loginuser.getUsername());
//                params.put("KEY_PASSWORD", loginuser.getPassword());
//                return params;
//
//            }
//
//        };
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);

    }

    public void showToast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }
}
