package com.ssit.www.bloodbank.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssit.www.bloodbank.R;

public class Contact_Us_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_headrg, tv_phone;

    LinearLayout lv_email, lv_phone, lv_callfirst, lv_callsecond, lv_callthree, lv_emailfirst, lv_emailsecond;

    TextView tv_callfirst, tv_callsecond, tv_callthree, tv_first, tv_second, tv_contactus;
    ImageView iv_back;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us_);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DashBoard.class));
                finish();
            }
        });


        tv_headrg = (TextView) findViewById(R.id.tv_headrg);

        lv_email = (LinearLayout) findViewById(R.id.lv_email);
        lv_phone = (LinearLayout) findViewById(R.id.lv_phone);

        lv_phone.setOnClickListener(this);
        lv_email.setOnClickListener(this);


    }


    private void showEmail() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.email_us, null);

        TextView tv_title_email = view.findViewById(R.id.tv_title_email);

        tv_first = view.findViewById(R.id.tv_first);
        tv_second = view.findViewById(R.id.tv_second);

        lv_emailfirst = view.findViewById(R.id.lv_emailfirst);
        lv_emailsecond = view.findViewById(R.id.lv_emailsecond);


        Button btn_emailcancel = view.findViewById(R.id.btn_emailcancel);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_emailcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);

        lv_emailfirst.setOnClickListener(this);
        lv_emailsecond.setOnClickListener(this);


    }

    private void showAlter() {

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.call_us, null);

        TextView tv_title_call = view.findViewById(R.id.tv_title_call);

        tv_callfirst = view.findViewById(R.id.tv_callfirst);
        tv_callsecond = view.findViewById(R.id.tv_callsecond);
        tv_callthree = view.findViewById(R.id.tv_callthree);

        lv_callfirst = view.findViewById(R.id.lv_callfirst);
        lv_callsecond = view.findViewById(R.id.lv_callsecond);
        lv_callthree = view.findViewById(R.id.lv_callthree);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        tv_callfirst.setOnClickListener(this);
        tv_callsecond.setOnClickListener(this);
        tv_callthree.setOnClickListener(this);

        lv_callfirst.setOnClickListener(this);
        lv_callsecond.setOnClickListener(this);
        lv_callthree.setOnClickListener(this);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }

    public void showCall(String msg) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + msg));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();

                } else {

                    //           Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void sendEmail(String email) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = {email};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here...");
        intent.putExtra(Intent.EXTRA_TEXT, "Body of the content here...");
        intent.putExtra(Intent.EXTRA_CC, "hr@ssinfotech.org");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));

    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.lv_phone:
                showAlter();
                break;

            case R.id.lv_email:
                showEmail();
                break;

            case R.id.lv_callfirst:
            case R.id.tv_callfirst:
                showCall("+919657959184");
                break;

            case R.id.lv_callsecond:
            case R.id.tv_callsecond:
                showCall("+917126466161");
                break;

            case R.id.lv_callthree:
            case R.id.tv_callthree:
                showCall("+917122226288");
                break;

            case R.id.lv_emailfirst:
            case R.id.tv_first:
                sendEmail("contact@ssinfotect.org");
                break;

            case R.id.lv_emailsecond:
            case R.id.tv_second:
                sendEmail("hr@ssinfotech.org");

                break;


        }


    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Contact_Us_Activity.this,DashBoard.class));
        finish();
    }
}
