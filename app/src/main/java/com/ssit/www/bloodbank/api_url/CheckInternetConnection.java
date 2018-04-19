package com.ssit.www.bloodbank.api_url;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Android on 15-01-2018.
 */


public class CheckInternetConnection {


    public static boolean onInternetCheck(Activity  activity) {
        boolean check=false;

        ConnectivityManager connectivityManager= (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()==NetworkInfo.State.CONNECTED){

            check=true;
//            Login();
        }else {
            check=false;
//            showToast("Cannot connect to Internet...Please check your connection!");
        }
        return check;
    }


}
