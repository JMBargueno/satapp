package com.dmtroncoso.satapp.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager(){

    }

    private static SharedPreferences getSharedPreferences(){
        return MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setSomeIntValue(String dataLabel, int dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(dataLabel, dataValue);
        editor.commit();
    }

    public static  int getSomeIntValue(String dataLabel){
        return getSharedPreferences().getInt(dataLabel, 0);
    }
}
