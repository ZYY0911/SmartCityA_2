package com.example.smartcitya_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.OrderDetailsActivity;
import com.example.smartcitya_2.adapter.OrderAdapter;
import com.example.smartcitya_2.bean.OrdeTitle;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 11:26
 */
public class OrderFragment extends Fragment {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ListView listOrder;

    public OrderFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public OrderFragment(AppHomeActivity appHomeActivity) {

        this.appHomeActivity = appHomeActivity;
    }

    public static OrderFragment newInstance(AppHomeActivity appHomeActivity) {
        return new OrderFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("我的订单");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("个人中心"));
            }
        });
        setVolley();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setVolley();
        }
        super.onHiddenChanged(hidden);
    }

    List<String> string;

    private void setVolley() {
        string = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllOrderType")
                .setDialog(getActivity())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            string.add(jsonArray.optString(i));
                        }
                        setVolley_title();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    List<OrdeTitle> ordeTitles;

    private void setVolley_title() {
        ordeTitles = new ArrayList<>();
        for (int i = 0; i < string.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            volleyTo.setUrl("getOrderByType")
                    .setJsonObject("type", string.get(i))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<OrdeTitle> orderTitles1 = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<OrdeTitle>>() {
                                    }.getType());
                            ordeTitles.addAll(orderTitles1);
                            if (finalI == string.size() - 1) {
                                listOrder.setAdapter(new OrderAdapter(getActivity(), orderTitles1));
                                listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                                        intent.putExtra("info", ordeTitles.get(position));
                                        startActivity(intent);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        listOrder = getView().findViewById(R.id.list_order);
    }
}
