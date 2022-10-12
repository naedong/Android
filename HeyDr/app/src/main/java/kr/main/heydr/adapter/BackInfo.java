package kr.main.heydr.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class BackInfo {
    private static String Image_KEY = "back";

    private String image;

    public BackInfo() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BackInfo(String image) {
        this.image = image;
    }
    public void Save(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Image_KEY, image)
                .apply();
    }

    public static Map<String, String> Load(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Image_KEY = sp.getString(Image_KEY, "back");

        Map<String, String> map = new HashMap<>();
        map.put("backimage", Image_KEY);
        return map;
    }
}
