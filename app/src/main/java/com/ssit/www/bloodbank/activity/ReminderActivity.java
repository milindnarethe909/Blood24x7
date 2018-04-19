package com.ssit.www.bloodbank.activity;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssit.www.bloodbank.NotificationService.ScheduleClient;
import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.Reminder_POJO;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android on 14-02-2018.
 */

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ReminderActivity.class.getSimpleName();

    ImageView iv_back;
    TextView tv_date;

    private static String date = null;

    ApiInterface apiInterface;

    String day, month, year;

    TextView tv_donate_date, tv_expiry_date;

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_date = (TextView) findViewById(R.id.tv_date);

        tv_donate_date = (TextView) findViewById(R.id.tv_donate_date);
        tv_expiry_date = (TextView) findViewById(R.id.tv_expiry_date);

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        String currentdate = BBSharedPreferenceManager.getCurrentDate("c_currendate", getApplicationContext());
        String expiry_date = BBSharedPreferenceManager.getExpireDate("c_expiry", getApplicationContext());

        if (expiry_date.equals("0000-00-00")) {
            tv_expiry_date.setText("YYYY-MM-DD");
            tv_donate_date.setText("YYYY-MM-DD");
        } else {
            tv_donate_date.setText(BBSharedPreferenceManager.getReminderDate("c_date", getApplicationContext()));
            tv_expiry_date.setText(expiry_date);
            tv_date.setText("Click Off Date");
        }

        if (currentdate.equals(expiry_date)) {
            tv_date.setEnabled(false);

        } else if (expiry_date.equals("0000-00-00")) {
            tv_date.setEnabled(true);
        }

//        showNotificationAlarm();
        iv_back.setOnClickListener(this);
        tv_date.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(ReminderActivity.this, DashBoard.class));
                finish();
                break;

            case R.id.tv_date:
                boolean result= CheckInternetConnection.onInternetCheck(this);
                if (result) {
                    showDate();
                }else {
                    showToast("Cannot connect to Internet...Please check your connection!");
                }

        }
    }

    private void showDate() {
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s = monthOfYear + 1;
//                String a = dayOfMonth+"/"+s+"/"+year;
                date = year + "-" + s + "-" + dayOfMonth;
             //   showToast("" + date);
                LoadReminder();
            }
        };

        Time date = new Time();
        DatePickerDialog d = new DatePickerDialog(ReminderActivity.this, dpd, date.year, date.month, date.monthDay);
        d.show();

    }

    private void LoadReminder() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Sending Date..", false);

        Call<Reminder_POJO> reminder_pojoCall = apiInterface.REMINDER_POJO_CALL(BBSharedPreferenceManager.getClientID("c_id", getApplicationContext()), date);
        reminder_pojoCall.enqueue(new Callback<Reminder_POJO>() {
            @Override
            public void onResponse(Call<Reminder_POJO> call, Response<Reminder_POJO> response) {
                progressDialog.dismiss();

                Log.d(TAG, "RESPONSE Reminder Date " + response);

                Reminder_POJO reminder_pojo = response.body();

                String status = null, message = null, client_id = null, client_date = null, donate_expiry = null;

                status = reminder_pojo.getStatus();
                message = reminder_pojo.getMessage();
                client_date = reminder_pojo.getClient_date();
                donate_expiry = reminder_pojo.getDonate_expiry();

                Log.d(TAG, " Status = " + status + " message = " + message + " Date = " + client_date + " Expiry = " + donate_expiry);

                if (status.equals("200Ok") && message.equals("Remaider Details")) {

                    BBSharedPreferenceManager.setExpireDate("c_expiry", donate_expiry, getApplicationContext());
                    BBSharedPreferenceManager.setReminderDate("c_date", client_date, getApplicationContext());

                    Log.d(TAG, "Year = " + donate_expiry.substring(0, 4));
                    Log.d(TAG, "Month = " + donate_expiry.substring(5, 7));
                    Log.d(TAG, "Day = " + donate_expiry.substring(8, 10));

                    tv_donate_date.setText(BBSharedPreferenceManager.getReminderDate("c_date", getApplicationContext()));
                    tv_expiry_date.setText(BBSharedPreferenceManager.getExpireDate("c_expiry", getApplicationContext()));

                    tv_date.setEnabled(false);
                    tv_date.setText("Click Off Date");
                    showNotification();

                }

            }

            @Override
            public void onFailure(Call<Reminder_POJO> call, Throwable t) {
                progressDialog.dismiss();
                String msg = t.getMessage();
              //  showToast("" + msg);
                Log.d(TAG, "On Failure Reminder Date = " + msg);

            }
        });
    }

    private void showNotification() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, ReminderActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.blood_256);
        builder.setContentTitle("Blood Bank Notification");
        builder.setContentText("This is blood Bank");
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }


    private void showNotificationAlarm() {
        String expiry_date = BBSharedPreferenceManager.getExpireDate("c_expiry", getApplicationContext());

//        year = expiry_date.substring(0, 4);
//        month = expiry_date.substring(5, 7);
//        day = expiry_date.substring(8, 10);
//        int y = Integer.parseInt(year);
//        int m = Integer.parseInt(month);
//        int d = Integer.parseInt(day);

//        int y=2018,m=05,d=17;

        String dt[]=expiry_date.split("-");




//        Log.d(TAG, "Day1 = " + d + " Month1 = " + m + " Year1 = " + y);

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, Integer.parseInt(dt[0]));
        c.set(java.util.Calendar.MONTH,Integer.parseInt(dt[1])-1);
        c.set(java.util.Calendar.DATE,Integer.parseInt(dt[2]));
        c.set(java.util.Calendar.HOUR_OF_DAY, 12);
        c.set(java.util.Calendar.MINUTE, 15);
        c.set(java.util.Calendar.SECOND, 0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);

        Log.d(TAG, "Expiry Date = " + expiry_date);
        Log.d(TAG, "Day = " + day + " Month = " + month + " Year = " + year);
    }


    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if (scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
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
