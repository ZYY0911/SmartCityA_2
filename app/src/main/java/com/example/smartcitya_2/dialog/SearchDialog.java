package com.example.smartcitya_2.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.EmptyServiceActivity;
import com.example.smartcitya_2.adapter.AllServiceItemAdapter;
import com.example.smartcitya_2.bean.ServiceInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.MyGidrView;
import com.example.smartcitya_2.util.OnClickItem;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:42
 */
public class SearchDialog extends DialogFragment {
    private MyGidrView searchDialog;
    private TextView tvFinish;
    private String msg;

    private AppHomeActivity appHomeActivity;



    public SearchDialog(String msg,AppHomeActivity appHomeActivity) {
        this.msg = msg;
        this.appHomeActivity = appHomeActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_diloag, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVoley_Type();
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }

    List<String> strings;

    private void setVoley_Type() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllServiceType")
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

    List<ServiceInfo> serviceInfos;

    private void setVolley_All() {
        serviceInfos = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            final int finalI1 = i;
            if (finalI == strings.size() - 1) {
                volleyTo.setDialog(getContext());
            }
            volleyTo.setUrl("getServiceByType")
                    .setJsonObject("serviceType", strings.get(i))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<ServiceInfo> serviceInfos1 = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<ServiceInfo>>() {
                                    }.getType());
                            serviceInfos.addAll(serviceInfos1);
                            if (finalI1 == strings.size() - 1) {
                                setAdapter();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }

    }

    private void setAdapter() {
        List<ServiceInfo> serviceInfoList = new ArrayList<>();
        for (int i = 0; i < serviceInfos.size(); i++) {
            ServiceInfo serviceInfo = serviceInfos.get(i);
            if (serviceInfo.getServiceName().contains(msg)) {
                serviceInfoList.add(serviceInfo);
            }
        }
        AllServiceItemAdapter serviceItemAdapter = new AllServiceItemAdapter(getActivity(), serviceInfoList);
        searchDialog.setAdapter(serviceItemAdapter);
        serviceItemAdapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                if (name.equals("地铁查询")) {
                    appHomeActivity.switchFragment(appHomeActivity.map.get("地铁查询"));
                } else if (name.equals("新闻中心")) {
                    appHomeActivity.switchFragment(appHomeActivity.map.get("新闻"));
                } else {
                    EmptyServiceActivity.newInstance("", serviceInfos.get(position).getUrl(), "", getActivity());
                }
                getDialog().dismiss();
            }
        });
    }


    private void initView() {
        searchDialog = getView().findViewById(R.id.search_dialog);
        tvFinish = getView().findViewById(R.id.tv_finish);
    }
}
