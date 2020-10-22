package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.DtInfo;
import com.example.smartcitya_2.bean.StationInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 18:43
 */
public class DtDetailsActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private TextView tvLine;
    private LinearLayout layoutStation;
    private TextView tvNext;
    private TextView tvTime;

    public static void newInstance(DtInfo dtInfo, Context context) {
        Intent intent = new Intent(context, DtDetailsActivity.class);
        intent.putExtra("info", dtInfo);
        context.startActivity(intent);

    }

    private DtInfo dtInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dtxc_details_layout);
        initView();
        dtInfo = (DtInfo) getIntent().getSerializableExtra("info");
        title.setText(dtInfo.getName());
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title1.setText("线路总览");
        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DtDetailsActivity.this, LineAllLookActivity.class));
            }
        });

        setVolley_Details();

    }


    List<StationInfo> stationInfos;

    private void setVolley_Details() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllStationById")
                .setDialog(DtDetailsActivity.this)
                .setJsonObject("id", dtInfo.getSubwayid())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        stationInfos = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString()
                                , new TypeToken<List<StationInfo>>() {
                                }.getType());
                        setViewItem();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        setVolley_Details();

                    }
                }).start();
    }

    private void setViewItem() {
        tvLine.setText(stationInfos.get(0).getStationname() + "——" + stationInfos.get(stationInfos.size() - 1).getStationname());
        tvNext.setText("即将到站：" + dtInfo.getNextname());
        String nowState = "建国门站";
        int index = 0, nowIndex = 0;
        long distance = 0;
        for (int i = 0; i < stationInfos.size(); i++) {
            if (stationInfos.get(i).getStationname().equals(nowState)) {
                nowIndex = stationInfos.get(i).getStationIndex();
            }
            if (stationInfos.get(i).getStationname().equals(dtInfo.getNextname())) {
                index = stationInfos.get(i).getStationIndex();
            }
        }
        for (int i = 0; i < stationInfos.size(); i++) {
            if (stationInfos.get(i).getStationIndex() > Math.min(index, nowIndex) && stationInfos.get(i).getStationIndex() < (Math.max(index, nowIndex))-1) {
                distance += stationInfos.get(i).getDistance();
            }
        }
        tvTime.setText("剩余时间：" + dtInfo.getTime() + "分钟       间隔：" + (Math.max(index, nowIndex) - Math.min(index, nowIndex)) + "站(距离" + distance + "km)");
        layoutStation.removeAllViews();
        for (int i = 0; i < stationInfos.size(); i++) {
            View view = LayoutInflater.from(DtDetailsActivity.this).inflate(R.layout.details_item, null);
            ViewHolder holder = new ViewHolder();
            holder.itemLayout = view.findViewById(R.id.item_layout);
            holder.itemBg = view.findViewById(R.id.item_bg);
            holder.itemStation = view.findViewById(R.id.item_station);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(15, 0, 15, 0);
            view.setLayoutParams(layoutParams);
            StationInfo stationInfo = stationInfos.get(i);
            holder.itemStation.setText(stationInfo.getStationname());
            if (stationInfo.getStationIndex() < index) {
                holder.itemBg.setBackgroundResource(R.drawable.dt_1);
            } else if (stationInfo.getStationIndex() == index) {
                holder.itemBg.setBackgroundResource(R.drawable.dt_2);
            } else {
                holder.itemBg.setBackgroundResource(R.drawable.dt_3);
            }
            layoutStation.addView(view);
        }
    }


    static class ViewHolder {

        private LinearLayout itemLayout;
        private TextView itemBg;
        private TextView itemStation;
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        tvLine = findViewById(R.id.tv_line);
        layoutStation = findViewById(R.id.layout_station);
        tvNext = findViewById(R.id.tv_next);
        tvTime = findViewById(R.id.tv_time);
    }
}

