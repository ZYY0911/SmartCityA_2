package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.ServiceInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 8:55
 */
public class EmptyServiceActivity extends AppCompatActivity {
    private WebView webView;


    public static void newInstance(String id, String url, String name, Context context) {
        Intent intent = new Intent(context, EmptyServiceActivity.class);
        intent.putExtra("info", id);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_service_layout);
        initView();
        if (!getIntent().getStringExtra("url").equals("")) {
            webView.loadUrl(getIntent().getStringExtra("url"));

        } else if (!getIntent().getStringExtra("info").equals("")) {
            VolleyTo volleyTo = new VolleyTo();
            volleyTo.setUrl("service_info")
                    .setDialog(this)
                    .setJsonObject("serviceid", getIntent().getStringExtra("info"))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            ServiceInfo serviceInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                    , ServiceInfo.class);

                            webView.loadUrl(serviceInfo.getUrl());
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        } else {
            setVoley_Type();
        }
    }

    List<String> strings;

    private void setVoley_Type() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllServiceType")
                .setDialog(EmptyServiceActivity.this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            strings.add(jsonArray.optString(i));

                        }
                        setVolley_All();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    private void setVolley_All() {
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            volleyTo.setUrl("getServiceByType")
                    .setJsonObject("serviceType", strings.get(i))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<ServiceInfo> serviceInfoList = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<ServiceInfo>>() {
                                    }.getType());
                            for (int j = 0; j < serviceInfoList.size(); j++) {
                                ServiceInfo serviceInfo = serviceInfoList.get(j);
                                if (serviceInfo.getServiceName().equals(getIntent().getStringExtra("name"))) {
                                    VolleyTo volleyTo = new VolleyTo();
                                    volleyTo.setUrl("service_info")
                                            .setJsonObject("serviceid", serviceInfo.getServiceid())
                                            .setVolleyLo(new VolleyLo() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    ServiceInfo serviceInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                                            , ServiceInfo.class);
                                                    webView.loadUrl(serviceInfo.getUrl());
                                                }

                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {

                                                }
                                            }).start();
                                    return;
                                }
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }

    private void initView() {
        webView = findViewById(R.id.web_view);
    }
}
