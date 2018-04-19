package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.ssit.www.bloodbank.Adapter.Search_Donar_Adapter;
import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.Search_Donar_GSON_Class;
import com.ssit.www.bloodbank.User.Search_Donar_Pojo_class;
import com.ssit.www.bloodbank.User.Search_class;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android on 10-01-2018.
 */

public class Search_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG=Search_Activity.class.getSimpleName();

    private static final String bloodgp[] = {"Select Blood Group", "A RhD positive (A+)", "A RhD negative (A-)",
            "B RhD positive (B+)", "B RhD negative (B-)",
            "O RhD positive (O+)", "O RhD negative (O-)", "AB RhD positive (AB+)", "AB RhD negative (AB-)"};

    AutoCompleteTextView atv_states_search;
    EditText ed_city_search;
    Spinner sp_bloodgp_search;

    Button bt_search;

    ArrayAdapter<String> state;
    ArrayAdapter<String> bg;

    String[] states;

    Search_class search_class;

    ImageView iv_back;

    LinearLayout ll_first_search, ll_second_search;

    ApiInterface apiInterface;

    Search_Donar_Adapter search_donar_adapter;

    RecyclerView recyclerView;

    List<Search_Donar_Pojo_class> list=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        search_class = new Search_class();

        atv_states_search = (AutoCompleteTextView) findViewById(R.id.atv_states_search);
        ed_city_search = (EditText) findViewById(R.id.ed_city_search);
        sp_bloodgp_search = (Spinner) findViewById(R.id.sp_bloodgp_search);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        bt_search = (Button) findViewById(R.id.bt_search_search);

        ll_first_search = (LinearLayout) findViewById(R.id.ll_first_search);
        ll_second_search = (LinearLayout) findViewById(R.id.ll_second_search);

        recyclerView=(RecyclerView)findViewById(R.id.rc_search) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_donar_adapter=new Search_Donar_Adapter(getApplicationContext(),list);
        recyclerView.setAdapter(search_donar_adapter);


        bt_search.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        states = getResources().getStringArray(R.array.states);
        state = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, states);
        atv_states_search.setThreshold(1);
        atv_states_search.setAdapter(state);

        bg = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, bloodgp);
        sp_bloodgp_search.setAdapter(bg);

        atv_states_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search_class.setState(adapterView.getItemAtPosition(i).toString());
            }
        });

        sp_bloodgp_search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                search_class.setBloodgp(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search_search:
                validationCheck();
                break;
            case R.id.iv_back:
                startActivity(new Intent(Search_Activity.this, DashBoard.class));
                finish();
                break;
        }

    }

    private void validationCheck() {


        search_class.setCity(ed_city_search.getText().toString().trim());
        if (TextUtils.isEmpty(search_class.getState())) {
            atv_states_search.setError("Sates is Required");
        } else if (TextUtils.isEmpty(search_class.getCity())) {
            ed_city_search.setError("City is Reqiured");
        } else if (search_class.getBloodgp().equalsIgnoreCase("Select Blood Group") || search_class.getBloodgp().equals("")) {
            showToast("Please Select Your Blood Group");
        } else {
            boolean check= CheckInternetConnection.onInternetCheck(this);
            if (check) {
                search();
            }else {
                showToast("Cannot connect to Internet...Please check your connection!");
            }

        }
    }

    private void search() {
        final ProgressDialog progressDialog=ProgressDialog.show(this,"Loding Data","Please Wait...",false);

        Call<Search_Donar_GSON_Class> search_donar_gson_classCall=apiInterface.SEARCH_DONAR_GSON_CLASS_CALL(BBSharedPreferenceManager.getClientID("c_id",getApplicationContext()),search_class.getBloodgp(),search_class.getState(),search_class.getCity());
        search_donar_gson_classCall.enqueue(new Callback<Search_Donar_GSON_Class>() {
            @Override
            public void onResponse(Call<Search_Donar_GSON_Class> call, Response<Search_Donar_GSON_Class> response) {
                progressDialog.dismiss();

                Search_Donar_GSON_Class gson_class=response.body();
                String status=null,message=null;
                status=gson_class.getStatus();
                message=gson_class.getMessage();
                Log.d(TAG,"Status = "+status+" Message =  "+message);

                if (status.equals("200Ok")&&message.equals("Donor Details")){
                    list.addAll(gson_class.getData());
                    search_donar_adapter.notifyDataSetChanged();
                    ll_first_search.setVisibility(View.GONE);
                    ll_second_search.setVisibility(View.VISIBLE);
//                    showToast("Donor Details");

                }
                if (status.equals("200Ok")&&message.equals("Donor Details not Found")){

                    showToast("Donor Details not Found");
                }
            }

            @Override
            public void onFailure(Call<Search_Donar_GSON_Class> call, Throwable t) {
                progressDialog.dismiss();

                String msg=t.getMessage();
                Log.d(TAG,"Search OnFailure Message = "+msg);
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,DashBoard.class));
        finish();
    }
}
