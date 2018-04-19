package com.ssit.www.bloodbank.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssit.www.bloodbank.Helper.Utilies;
import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = DashBoard.class.getSimpleName();

    CardView cd_search, cd_people, cd_remainder, cd_share, cd_bloodblank, cd_contact;
    ImageView iv_search, iv_people, iv_remainder, iv_share, iv_bloodblank, iv_contact;

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        if (!(BBSharedPreferenceManager.getSkip("c_skip", getApplicationContext()).equals("true"))) {
            showNote();
        }

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.d(TAG, "Current Date Dashboard = " + date);
        BBSharedPreferenceManager.setCurrentDate("c_currendate", date, getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        //toolbar.setTitle("My Toolbar");
        // toolbar.setTitleTextColor(0xFFFFFFFF);

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);


        cd_search = (CardView) findViewById(R.id.cd_search);
        cd_people = (CardView) findViewById(R.id.cd_people);
        cd_remainder = (CardView) findViewById(R.id.cd_reminder);
        cd_share = (CardView) findViewById(R.id.cd_sharing);
        cd_bloodblank = (CardView) findViewById(R.id.cd_bloodbank);
        cd_contact = (CardView) findViewById(R.id.cd_contact);

//


        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_people = (ImageView) findViewById(R.id.iv_people);
        iv_remainder = (ImageView) findViewById(R.id.iv_reminder);
        iv_share = (ImageView) findViewById(R.id.iv_sharing);
        iv_bloodblank = (ImageView) findViewById(R.id.iv_bloodbank);
        iv_contact = (ImageView) findViewById(R.id.iv_contact);


        cd_search.setOnClickListener(this);
        cd_people.setOnClickListener(this);
        cd_remainder.setOnClickListener(this);
        cd_share.setOnClickListener(this);
        cd_bloodblank.setOnClickListener(this);
        cd_contact.setOnClickListener(this);

        iv_search.setOnClickListener(this);
        iv_people.setOnClickListener(this);
        iv_remainder.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_bloodblank.setOnClickListener(this);
        iv_contact.setOnClickListener(this);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuContact:
                startActivity(new Intent(this, Contact_Us_Activity.class));
                finish();
                break;

            case R.id.menuHelp:
                showHelp();
                break;

            case R.id.menuLogout:
                logout();
                break;

            case R.id.menuProfile:
                startActivity(new Intent(DashBoard.this, ProfileActivity.class));
                finish();
                break;

        }
        return true;
    }

    private void showHelp() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.help_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);

        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(this);

        dialog = builder.create();
        dialog.show();
    }

    private void logout() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.logout_screen, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);

        Button yes = (Button) view.findViewById(R.id.bt_yes);
        Button no = (Button) view.findViewById(R.id.bt_no);

        dialog = builder.create();
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BBSharedPreferenceManager.setLoginStatus("c_Login", "false", getApplicationContext());
                BBSharedPreferenceManager.setSkip("c_skip", "false", getApplicationContext());
                startActivity(new Intent(DashBoard.this, LoginActivity.class));
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    private void showNote() {

        //  Toast.makeText(this,"Skip",Toast.LENGTH_SHORT).show();

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.note_screen, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(true);

        TextView tv_skip = (TextView) view.findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(this);

        dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cd_search:
            case R.id.iv_search:
//                showToast("Search");
                startActivity(new Intent(DashBoard.this, Search_Activity.class));
                finish();
                break;
            case R.id.cd_people:
            case R.id.iv_people:
                startActivity(new Intent(DashBoard.this, PeopleInNeed.class));
                finish();
//                showToast("People");
                break;

            case R.id.cd_reminder:
            case R.id.iv_reminder:
//                showToast("reminder");
                startActivity(new Intent(DashBoard.this, ReminderActivity.class));
                finish();
                break;

            case R.id.cd_sharing:
            case R.id.iv_sharing:
//                showToast("Share");
                sharing();
                break;

            case R.id.cd_bloodbank:
            case R.id.iv_bloodbank:
                startActivity(new Intent(DashBoard.this, AllBloodBank.class));
                finish();
                break;

            case R.id.cd_contact:
            case R.id.iv_contact:
//                showToast("Contact");
                boolean result = CheckInternetConnection.onInternetCheck(this);
                if (result) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    } else {
                        startActivity(new Intent(DashBoard.this, MapsActivity.class));
                    }
                } else {
                    showToast("Cannot connect to Internet...Please check your connection!");

                }
                break;

            case R.id.iv_cancel:
                startActivity(new Intent(DashBoard.this, DashBoard.class));
                finish();
                break;

            case R.id.tv_skip:
                dialog.dismiss();
                BBSharedPreferenceManager.setSkip("c_skip", "true", getApplicationContext());
                break;

        }

    }

    private void sharing() {
        try {
//            final String appPackageName = getPackageName();
            String name = BBSharedPreferenceManager.getName("c_name", getApplicationContext());
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, name + " We are happy to help you for instent blood at : https://goo.gl/i7giUj");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
        }
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }
}
