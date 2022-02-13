package com.android.foodify.SharedPreferences;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharedPreferences {

    android.content.SharedPreferences sharedPreferences;
    android.content.SharedPreferences.Editor editor;

    private static String PREF_NAME = "LunchManger";
    private static String IS_FIRST_TIME = "isFirst";
    private static final String PREF_userId = "UserId";

    private static android.content.SharedPreferences sharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getPREF_userId(Context context) {
        return sharedPreferences(context).getString(PREF_userId, "");
    }

    public SharedPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME,0);
        editor = sharedPreferences.edit();
    }

    public void setFirstLunch(boolean isFirst){
        editor.putBoolean(IS_FIRST_TIME,isFirst);
        editor.commit();
    }

    public boolean isFirstTime(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME,true);
    }

    public static void setValues(Context context,String userid) {
        android.content.SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString(PREF_userId, userid);
        editor.apply();
    }

    public static void removeValues(Context context) {

        android.content.SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.remove(PREF_userId);
        editor.clear();
        editor.apply();
    }
}

