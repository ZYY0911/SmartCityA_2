package com.example.smartcitya_2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartcitya_2.bean.LY;
import com.example.smartcitya_2.bean.UserNum;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/11 at 19:58
 */
public class AppClient extends Application {
    public static SharedPreferences sharedPreferences;
    public static final String IP = "IP";
    public static final String PORT = "port";
    private static RequestQueue requestQueue;
    public static final String username = "abc";
    public static final String IsFirst = "isFirst";
    private static List<AppCompatActivity> appCompatActivities = new ArrayList<>();
    private List<LY> lyList = new ArrayList<>();
    private List<LY> Ssp = new ArrayList<>();

    public List<LY> getSsp() {
        return Ssp;
    }

    public List<LY> getLyList() {
        return lyList;
    }

    public static void add(AppCompatActivity appCompatActivity) {
        appCompatActivities.add(appCompatActivity);
    }

    public static void finAll() {
        for (int i = 0; i < appCompatActivities.size(); i++) {
            AppCompatActivity appCompatActivity = appCompatActivities.get(i);
            if (!appCompatActivity.isFinishing()) {
                appCompatActivity.finish();
            }
        }
        appCompatActivities.clear();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("aaa", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        setVolley();
    }

    private static List<UserNum> userNums;

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUsers")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userNums = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<UserNum>>() {
                                }.getType());

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    public static String getUserId(String username) {
        for (int i = 0; i < userNums.size(); i++) {
            UserNum userNum = userNums.get(i);
            if (userNum.getUsername().equals(username)) {
                return userNum.getUserid();
            }
        }
        return "1";
    }


    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }

    public static void add(ImageRequest imageRequest) {
        requestQueue.add(imageRequest);
    }

}
