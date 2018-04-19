package com.ssit.www.bloodbank.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Android on 06-01-2018.
 */

public class BBSharedPreferenceManager {

    public static void setClientID(String key, String value, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getClientID(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setName(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getName(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setEmail(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getEmail(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setGender(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getGender(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setMobile(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getMobile(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setBloodGp(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getBloodgp(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setAddress(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getAddress(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setCity(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getCity(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setState(String key, String value, Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getState(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void setProfile(String key,String value,Context context){
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getProfile(String key,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");
    }

    public static void setExpireDate(String key,String value,Context context){
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getExpireDate(String key,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");
    }

    public static void setOTP(String key,String value,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getOTP(String key,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");
    }

    public static void setReminderDate(String key,String value,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getReminderDate(String key,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");
    }


    public static void setCurrentDate(String key,String value,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getCurrentDate(String key,Context context){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key,"");
    }

    public static void setLoginStatus(String key,String value,Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getLoginStatus(String key,Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,"false");
    }


    public static void setSkip(String key,String value,Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getSkip(String key,Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,"false");
    }
}
