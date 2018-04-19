package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.RegisterUser;
import com.ssit.www.bloodbank.User.RegistrationPojo;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Android on 03-01-2018.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private EditText ed_fullname, ed_email, ed_mobile, ed_address, ed_city, ed_pass, ed_confpass;
    private Spinner sp_state, sp_bloodgp;
    private Button bt_submit, bt_submit2, bt_submit3;

    private LinearLayout ll_first, ll_second, ll_third;

    private RadioGroup rg_check;

    ApiInterface apiInterface;

    AutoCompleteTextView state, blood;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String MobilePattern = "[0-9]{10}";

    private String[] stateArr = {"Select State", "Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
            "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "National Capital Territory of Delhi", "Goa",
            "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh",
            "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim",
            "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};

    private String bloodgp[] = {"Select Blood Group", "A RhD positive (A+)", "A RhD negative (A-)", "B RhD positive (B+)", "B RhD negative (B-)",
            "O RhD positive (O+)", "O RhD negative (O-)", "AB RhD positive (AB+)", "AB RhD negative (AB-)"};

    private String st_confpassword;

    String status, message, id, otp;

    private RegisterUser registerUser;

    int MY_SOCKET_TIMEOUT_MS = 30000;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        registerUser = new RegisterUser();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        ed_fullname = (EditText) findViewById(R.id.ed_fullname);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_mobile = (EditText) findViewById(R.id.ed_mob);
        ed_address = (EditText) findViewById(R.id.ed_add);
        ed_city = (EditText) findViewById(R.id.ed_city);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        ed_confpass = (EditText) findViewById(R.id.ed_confpass);

        // sp_state = (Spinner) findViewById(R.id.sp_state);
//        sp_bloodgp = (Spinner) findViewById(R.id.sp_bloodgp);

        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit2 = (Button) findViewById(R.id.bt_submit2);
        bt_submit3 = (Button) findViewById(R.id.bt_submit3);

        ll_first = (LinearLayout) findViewById(R.id.ll_first);
        ll_second = (LinearLayout) findViewById(R.id.ll_second);
        ll_third = (LinearLayout) findViewById(R.id.ll_third);

        rg_check = (RadioGroup) findViewById(R.id.rg_check);

        state = (AutoCompleteTextView) findViewById(R.id.atv_states_reg);
        blood = (AutoCompleteTextView) findViewById(R.id.atv_blood_reg);

        bt_submit.setOnClickListener(this);
        bt_submit2.setOnClickListener(this);
        bt_submit3.setOnClickListener(this);

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, stateArr);
        state.setAdapter(adapter_state);

        state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerUser.setState(adapterView.getItemAtPosition(i).toString());
            }
        });

        blood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerUser.setBloodgroup(adapterView.getItemAtPosition(i).toString());
            }
        });

        ArrayAdapter<String> adapter_bloodgp = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, bloodgp);
        blood.setAdapter(adapter_bloodgp);

        rg_check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_male:
                        registerUser.setGender("Male");
                        break;

                    case R.id.rb_female:
                        registerUser.setGender("Female");
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
             //   showToast("Submit");
                firstSubmit();
                break;
            case R.id.bt_submit2:
                secondSubmit();
                break;
            case R.id.bt_submit3:
                thirdSubmit();
                break;

        }
    }

    private void firstSubmit() {

        registerUser.setFullname(ed_fullname.getText().toString().trim());
        registerUser.setEmail(ed_email.getText().toString().trim());
        registerUser.setMobile_no(ed_mobile.getText().toString().trim());


        if (TextUtils.isEmpty(registerUser.getFullname())) {
            ed_fullname.setError("Full Name is Required");
        } else if (TextUtils.isEmpty(registerUser.getEmail())) {
            ed_email.setError("Email Id is Required");
        } else if (TextUtils.isEmpty(registerUser.getMobile_no())) {
            ed_mobile.setError("Mobile No. is Required");
        } else if (registerUser.getFullname().length() < 2) {
            ed_fullname.setError("Name length should be minimum 2 characters long");
        } else if (!registerUser.getEmail().matches(emailPattern)) {
            ed_email.setError("Email Id is Invalid");
        } else if (!registerUser.getMobile_no().matches(MobilePattern)) {
            ed_mobile.setError("Mobile number maximum 10 charater");
        } else {
            Boolean result = CheckInternetConnection.onInternetCheck(this);
            if (result) {
                final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Sending Data....", false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        ll_first.setVisibility(View.GONE);
                        ll_second.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(), "Mssage" + name + " " + email + " " + mobile + " " + course + " " + time, Toast.LENGTH_SHORT).show();

                    }
                }, 3000);
            } else {
                showToast("Cannot connect to Internet...Please check your connection!");
            }

        }
    }


    private void secondSubmit() {

        registerUser.setAddress(ed_address.getText().toString().trim());
        registerUser.setCity(ed_city.getText().toString().trim());

        if (TextUtils.isEmpty(registerUser.getAddress())) {
            ed_address.setError("Address is Required");
        } else if (TextUtils.isEmpty(registerUser.getCity())) {
            ed_city.setError("City is Required");
        } else if (TextUtils.isEmpty(registerUser.getState())) {
            state.setError("States is Required");
        } else if (registerUser.getAddress().length() < 2) {
            ed_address.setError("Address length should be minimum 2 characters long");
        } else if (registerUser.getCity().length() < 2) {
            ed_city.setError("City length should be minimum 2 characters long");
        } else {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "Please Wait", "Sending Data", false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    ll_second.setVisibility(View.GONE);
                    ll_third.setVisibility(View.VISIBLE);
                }
            }, 3000);
        }


    }

    private void thirdSubmit() {
        registerUser.setPassword(ed_pass.getText().toString().trim());
        st_confpassword = ed_confpass.getText().toString().trim();


        if (TextUtils.isEmpty(registerUser.getPassword())) {
            ed_pass.setError("Password is Required");
        } else if (TextUtils.isEmpty(st_confpassword)) {
            ed_confpass.setError("Confirm Password is required");
        } else if (TextUtils.isEmpty(registerUser.getBloodgroup())) {
            blood.setError("Blood Group is Required");
        } else if (registerUser.getPassword().length() < 4) {
            ed_pass.setError("Password length should minimum 4 charater");
        } else if (!registerUser.getPassword().equals(st_confpassword)) {
            ed_pass.setError("Password is Same");
            ed_confpass.setError("Password is Same");
            ed_pass.setText("");
            ed_confpass.setText("");
        } else {

            boolean result = CheckInternetConnection.onInternetCheck(this);

            if (result) {
                registration();
            } else {
                showToast("Cannot connect to Internet...Please check your connection!");
            }

        }


    }

    private void registration() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading ", "Please Wait...", false);
        Call<RegistrationPojo> registrationPojoCall = apiInterface.doRegistrationBlod(registerUser.getFullname(), registerUser.getEmail(), registerUser.getMobile_no(),
                registerUser.getBloodgroup(), registerUser.getGender(), registerUser.getAddress(), registerUser.getCity(), registerUser.getState(), registerUser.getPassword());

        registrationPojoCall.enqueue(new Callback<RegistrationPojo>() {
            @Override
            public void onResponse(Call<RegistrationPojo> call, Response<RegistrationPojo> response) {

                progressDialog.dismiss();
                RegistrationPojo registrationPojo = response.body();

                Log.d(TAG, response.code() + "");

                status = registrationPojo.getStatus();
                message = registrationPojo.getMessage();
                id = registrationPojo.getId();
                otp = registrationPojo.getOtp();

                BBSharedPreferenceManager.setClientID("c_id", id, getApplicationContext());
                BBSharedPreferenceManager.setOTP("c_otp", otp, getApplicationContext());

                Log.d(TAG, "onResponse: " + status + message + id + otp);

                if (status.equals("200Ok") && message.equals("Successfully Registered")) {
                    Intent intent = new Intent(RegistrationActivity.this, OTPActivity.class);
                    intent.putExtra("c_otp", otp);
                    startActivity(intent);
                    Log.d(TAG, "Registration OTP = " + otp);
                   // showToast("Successfully Registered");
                }
                if (status.equals("200Ok") && message.equals("Email already registred")) {
                    showToast("Email already registred");
                }

            }


            @Override
            public void onFailure(Call<RegistrationPojo> call, Throwable t) {
                progressDialog.dismiss();

             //   showToast(t.getMessage());
                Log.d(TAG, "On Failure Message = " + t.getMessage());

            }
        });


    }

    class RegistrationAsy extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

    }

    private void checkResopnse() {
        if (status.equals("200Ok") && message.equals("Successfully Registered")) {
            BBSharedPreferenceManager.setClientID("cid", id, getApplicationContext());
          //  showToast("Successfully Registered");
            Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
            intent.putExtra("otp", otp);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    public void showToast(String msg) {
        Toast.makeText(RegistrationActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
