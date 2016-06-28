package com.tanmay.augmentedrealitydemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TaNMay on 20/06/16.
 */
public class LocalStorage {

    private static LocalStorage instance = null;
    SharedPreferences sharedPreferences;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("Reg", 0);
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalStorage.class) {
                if (instance == null) {
                    instance = new LocalStorage(context);
                }
            }
        }
        return instance;
    }

    public void setAlarms(String alarms) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ALARMS", alarms);
        editor.commit();
    }

    public String getAlarms() {
        if (sharedPreferences.contains("ALARMS")) {
            return sharedPreferences.getString("ALARMS", null);
        } else {
            return null;
        }
    }
}
