package com.ssit.www.bloodbank.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ssit.www.bloodbank.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    CardView cd_search, cd_people, cd_remainder, cd_share, cd_bloodblank, cd_contact;
    ImageView iv_search, iv_people, iv_remainder, iv_share, iv_bloodblank, iv_contact;

//    TextView tv_bloodbank;

//    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd_search = (CardView) findViewById(R.id.cd_search);
        cd_people = (CardView) findViewById(R.id.cd_people);
        cd_remainder = (CardView) findViewById(R.id.cd_reminder);
        cd_share = (CardView) findViewById(R.id.cd_sharing);
        cd_bloodblank = (CardView) findViewById(R.id.cd_bloodbank);
        cd_contact = (CardView) findViewById(R.id.cd_contact);

//        toolbar=(Toolbar) findViewById(R.id.toolbar);


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cd_search:
            case R.id.iv_search:
                showToast("Search");
                startActivity(new Intent(MainActivity.this, Search_Activity.class));
                break;
            case R.id.cd_people:
            case R.id.iv_people:
                startActivity(new Intent(MainActivity.this, Search_Activity.class));
                showToast("People");
                break;

            case R.id.cd_reminder:
            case R.id.iv_reminder:
                showToast("reminder");
                break;

            case R.id.cd_sharing:
            case R.id.iv_sharing:
                showToast("Share");
                sharing();
                break;

            case R.id.cd_bloodbank:
            case R.id.iv_bloodbank:
                showToast("Blood Bank");
                break;

            case R.id.cd_contact:
            case R.id.iv_contact:
                showToast("Contact");
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;

        }
    }

    private void sharing() {
        try {
            final String appPackageName = getPackageName();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out Aap Ka Sewak App at: https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
