package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ssit.www.bloodbank.Adapter.All_Blood_Bank_Adapter;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.AllBloodBank_class;
import com.ssit.www.bloodbank.User.AllBloodbank_Data;
import com.ssit.www.bloodbank.User.allBlood_Bank_Pojo_class;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBloodBank extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AllBloodBank.class.getSimpleName();

    ImageView iv_back, iv_back1;

    AutoCompleteTextView atv_states;
    EditText ed_city;

    Button bt_search;

    RecyclerView recyclerView;

    LinearLayout ll_recycle, ll_allsearch;

    AllBloodBank_class allBloodBank_class;

    ArrayAdapter<String> stringArrayAdapter;

    String[] state;

    ApiInterface apiInterface;

    All_Blood_Bank_Adapter adapter;

    List<AllBloodbank_Data> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_blood_bank);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back1 = (ImageView) findViewById(R.id.iv_back1);

        allBloodBank_class = new AllBloodBank_class();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        atv_states = (AutoCompleteTextView) findViewById(R.id.atv_states);
        ed_city = (EditText) findViewById(R.id.ed_city);

        ll_allsearch = (LinearLayout) findViewById(R.id.ll_allsearch);
        ll_recycle = (LinearLayout) findViewById(R.id.ll_recycle);

        bt_search = (Button) findViewById(R.id.bt_search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new All_Blood_Bank_Adapter(this, list);


        state = getResources().getStringArray(R.array.states);
        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, state);
        atv_states.setAdapter(stringArrayAdapter);
        atv_states.setThreshold(1);

        atv_states.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                allBloodBank_class.setStates(adapterView.getItemAtPosition(i).toString());
            }
        });

        iv_back.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        iv_back1.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(AllBloodBank.this, DashBoard.class));
//                showToast("all blood bank");
                finish();
                break;

            case R.id.bt_search:
                validation();
                break;

            case R.id.iv_back1:
                list.clear();
                ll_allsearch.setVisibility(View.VISIBLE);
                ll_recycle.setVisibility(View.GONE);
                recyclerView.removeAllViewsInLayout();
                recyclerView.removeAllViews();
                break;
        }
    }

    private void validation() {
        allBloodBank_class.setCity(ed_city.getText().toString().trim());


        if (TextUtils.isEmpty(allBloodBank_class.getStates())) {
            atv_states.setError("State is Required");
        } else if (TextUtils.isEmpty(allBloodBank_class.getCity())) {
            ed_city.setError("City is Required");
        } else if (allBloodBank_class.getStates().length() < 2) {
            atv_states.setError("States is minimum 2 charater");
        } else if (allBloodBank_class.getCity().length() < 2) {
            ed_city.setError("City is minimum 2 charater");
        } else {
            boolean result = CheckInternetConnection.onInternetCheck(this);
            if (result) {
                sendData();
            } else {
                showToast("Cannot connect to Internet...Please check your connection!");
            }
        }


    }


    private void sendData() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Loading", "Please wait...", false);

        Call<allBlood_Bank_Pojo_class> allBlood_bank_pojo_classCall = apiInterface.ALL_BLOOD_ITEM_CALL(allBloodBank_class.getStates(), allBloodBank_class.getCity());
        allBlood_bank_pojo_classCall.enqueue(new Callback<allBlood_Bank_Pojo_class>() {
            @Override
            public void onResponse(Call<allBlood_Bank_Pojo_class> call, Response<allBlood_Bank_Pojo_class> response) {
                progressDialog.dismiss();

                allBlood_Bank_Pojo_class allBlood_bank_pojo_class = response.body();

                String status = null, message = null;

                status = allBlood_bank_pojo_class.getStatus();
                message = allBlood_bank_pojo_class.getMessage();

                Log.d(TAG, "Status = " + status + " Message = " + message + " List Data ");

                if (status.equals("200Ok") && message.equals("Blood Bank Details")) {

                    list.addAll(allBlood_bank_pojo_class.getData());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    ll_allsearch.setVisibility(View.GONE);
                    ll_recycle.setVisibility(View.VISIBLE);
                }

                if (status.equals("200Ok") && message.equals("Blood Bank Details not Found")) {
                    showToast("Blood Bank Details not Found");
                }

            }

            @Override
            public void onFailure(Call<allBlood_Bank_Pojo_class> call, Throwable t) {
                progressDialog.dismiss();
                String msg = t.getMessage();
                Log.d(TAG, msg);

            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AllBloodBank.this, DashBoard.class));
        finish();
    }
}
