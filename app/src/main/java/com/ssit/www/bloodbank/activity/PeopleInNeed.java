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

import com.ssit.www.bloodbank.Adapter.People_Need_Adapter;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.People_Need_GSON_Class;
import com.ssit.www.bloodbank.User.People_Need_POJO_Class;
import com.ssit.www.bloodbank.User.Search_class;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleInNeed extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG=PeopleInNeed.class.getSimpleName();
    private static final String bloodgp[] = {"Select Blood Group", "A RhD positive (A+)", "A RhD negative (A-)", "B RhD positive (B+)", "B RhD negative (B-)",
            "O RhD positive (O+)", "O RhD negative (O-)", "AB RhD positive (AB+)", "AB RhD negative (AB-)"};

    AutoCompleteTextView atv_states_people;
    EditText ed_city_people;
    Spinner sp_bloodgp_people;

    Button bt_search;

    ArrayAdapter<String> state;
    ArrayAdapter<String> bg;

    String[] states;

    Search_class search_class;

    ImageView iv_back;

    LinearLayout ll_first_people, ll_second_people;

    People_Need_Adapter adapter;

    List<People_Need_POJO_Class> list=new ArrayList<>();

    ApiInterface apiInterface;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_in_need);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        search_class = new Search_class();

        atv_states_people = (AutoCompleteTextView) findViewById(R.id.atv_states_people);
        ed_city_people = (EditText) findViewById(R.id.ed_city_people);
        sp_bloodgp_people = (Spinner) findViewById(R.id.sp_bloodgp_people);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        bt_search = (Button) findViewById(R.id.bt_search_people);

        ll_first_people = (LinearLayout) findViewById(R.id.ll_first_people);
        ll_second_people = (LinearLayout) findViewById(R.id.ll_second_people);

        recyclerView=(RecyclerView)findViewById(R.id.rc_people);

        adapter=new People_Need_Adapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        bt_search.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        states = getResources().getStringArray(R.array.states);
        state = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, states);
        atv_states_people.setThreshold(1);
        atv_states_people.setAdapter(state);

        bg = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, bloodgp);
        sp_bloodgp_people.setAdapter(bg);

        atv_states_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search_class.setState(adapterView.getItemAtPosition(i).toString());
            }
        });

        sp_bloodgp_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            case R.id.bt_search_people:
                validationCheck();
                break;
            case R.id.iv_back:
                startActivity(new Intent(PeopleInNeed.this, DashBoard.class));
                finish();
                break;
        }

    }

    private void validationCheck() {


        search_class.setCity(ed_city_people.getText().toString().trim());
        if (TextUtils.isEmpty(search_class.getState())) {
            atv_states_people.setError("Sates is Required");
        } else if (TextUtils.isEmpty(search_class.getCity())) {
            ed_city_people.setError("City is Reqiured");
        } else if (search_class.getBloodgp().equalsIgnoreCase("Select Blood Group") || search_class.getBloodgp().equals("")) {
            showToast("Please Select Your Blood Group");
        } else {
            boolean check= CheckInternetConnection.onInternetCheck(this);
            if (check){
                searchData();
            }else {
                showToast("Cannot connect to Internet...Please check your connection!");
            }
            Log.d(TAG,"State = " + search_class.getState() + " City " + search_class.getCity() + " Blood = " + search_class.getBloodgp());
        }
    }

    private void searchData() {

        final ProgressDialog progressDialog=ProgressDialog.show(this,"Loading","Please wait...",false);

        Call<People_Need_GSON_Class> call=apiInterface.PEOPLE_NEED_GSON_CLASS_CALL(search_class.getBloodgp(),search_class.getState(),search_class.getCity());
        call.enqueue(new Callback<People_Need_GSON_Class>() {
            @Override
            public void onResponse(Call<People_Need_GSON_Class> call, Response<People_Need_GSON_Class> response) {
                progressDialog.dismiss();

                People_Need_GSON_Class people_need_gson_class=response.body();
                String status,message;
                status=people_need_gson_class.getStatus();
                message=people_need_gson_class.getMessage();

                Log.d(TAG,"Status = "+status+" Message = "+message);

                if (status.equals("200Ok")&&message.equals("People in Need Details")){
                    list.addAll(people_need_gson_class.getData());
                    adapter.notifyDataSetChanged();
                    ll_first_people.setVisibility(View.GONE);
                    ll_second_people.setVisibility(View.VISIBLE);

                }
                if (status.equals("200Ok")&&message.equals("People in Need Details not Found")){
                    showToast("People in Need Details not Found");
                }
            }

            @Override
            public void onFailure(Call<People_Need_GSON_Class> call, Throwable t) {

                progressDialog.dismiss();
                Log.d(TAG,"People in need OnFaliure Message = "+t.getMessage());
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PeopleInNeed.this,DashBoard.class));
        finish();
    }
}
