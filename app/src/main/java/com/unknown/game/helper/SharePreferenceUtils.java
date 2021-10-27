package com.unknown.game.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePreferenceUtils {

    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public SharePreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(Const.COIN, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static int getIntData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int r = preferences.getInt(key, 0);
        return r;
    }

    public static void insertIntData(Context context, String key, int value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void insertDoubleData(Context context, String key, float value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }


    public static void insertStringData(Context context, String key, String value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static boolean getBooleanData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean r = preferences.getBoolean(key, false);
        return r;
    }

    public static void insertBooleanData(Context context, String key, boolean value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getStringData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String r = preferences.getString(key, "");
        return r;
    }
}

