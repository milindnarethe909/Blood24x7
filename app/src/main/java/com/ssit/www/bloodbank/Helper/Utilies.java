package com.ssit.www.bloodbank.Helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Android on 19-02-2018.
 */

public class Utilies {

    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 122;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 123;
    public static final int MY_PERMISSIONS_REQUEST_INTERNET = 124;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE = 125;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 126;


    public static boolean checkpremmision(final Context context) {
        String contextname = context.getClass().getSimpleName();
        int currentviresion = Build.VERSION.SDK_INT;

        if (currentviresion >= Build.VERSION_CODES.LOLLIPOP) {
            switch (contextname) {
                case "DashBoard":
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);

                                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, MY_PERMISSIONS_REQUEST_ACCESS_NETWORK_STATE);

                                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                                        }
                                    }
                                }
                            }
                        } else {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                        return false;
                    } else {
                        return true;
                    }

                default:
                    return false;

            }
        } else {
            return true;
        }
    }
}
