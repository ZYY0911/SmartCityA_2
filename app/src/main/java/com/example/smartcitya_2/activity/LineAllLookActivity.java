package com.example.smartcitya_2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.DtLineAdapter;
import com.example.smartcitya_2.bean.DtLine;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 18:58
 */
public class LineAllLookActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ListView leftList;
    private ImageView rightImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_all_layout);
        initView();
        title.setText("地铁总览");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageAdapter.newInstance(R.mipmap.ditie4, LineAllLookActivity.this);
            }
        });

        setVolley();
    }

    List<DtLine> dtLines;

    private void setVolley() {
        VolleyTo volleyTo
                = new VolleyTo();
        volleyTo.setUrl("getAllSubways")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        dtLines = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<DtLine>>() {
                                }.getType());
                        leftList.setAdapter(new DtLineAdapter(LineAllLookActivity.this, dtLines));
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        leftList = findViewById(R.id.left_list);
        rightImage = findViewById(R.id.right_image);
    }
}
