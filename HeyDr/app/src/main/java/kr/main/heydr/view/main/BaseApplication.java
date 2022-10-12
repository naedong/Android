package kr.main.heydr.view.main;


import android.Manifest;
import android.app.Application;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import kr.main.heydr.utils.PreferenceUtils;

public class BaseApplication extends Application {

    private static final String APP_ID = "0A54FF20-DC82-46F8-B073-C9206FCA4756"; // US-1 Demo
    public static final String VERSION = "3.0.40";

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(getApplicationContext());



    }
}
