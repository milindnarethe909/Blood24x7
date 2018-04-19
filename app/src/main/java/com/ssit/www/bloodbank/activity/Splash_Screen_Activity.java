package com.ssit.www.bloodbank.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Screen_Activity extends AppCompatActivity {

    long Delay=3000;
    TextView tv_title_splash;
    ImageView imageView;
    Animation up,down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen_);

        tv_title_splash=(TextView)findViewById(R.id.tv_title_splash);
        imageView=(ImageView)findViewById(R.id.image_splash);

        up= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        down=AnimationUtils.loadAnimation(this,R.anim.downtoup);

        tv_title_splash.startAnimation(down);
        imageView.startAnimation(up);


//
//        Animation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(1000); //You can manage the blinking time with this parameter
//        anim.setStartOffset(50);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        tv_title_splash.startAnimation(anim);

        Timer Runtimer=new Timer();

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                finish();
               if (BBSharedPreferenceManager.getLoginStatus("c_Login",getApplicationContext()).equals("true")){
                   startActivity(new Intent(getApplicationContext(),DashBoard.class));
                   finish();
               }else {
                   startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                   finish();
               }

            }
        };
        Runtimer.schedule(task,Delay);
    }

    private void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
