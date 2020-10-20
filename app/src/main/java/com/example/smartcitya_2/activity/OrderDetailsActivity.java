package com.example.smartcitya_2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.OrderDetailAdapter;
import com.example.smartcitya_2.bean.OrdeTitle;
import com.example.smartcitya_2.bean.OrderDetails;
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
 * @Create by 张瀛煜 on 2020/10/18 at 11:34
 */
public class OrderDetailsActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ListView listView;
    private TextView tvDdh;
    private TextView tvTime;
    private TextView tvMoney;
    private OrdeTitle ordeTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_layout);
        initView();
        title.setText("订单详情");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ordeTitle = (OrdeTitle) getIntent().getSerializableExtra("info");
        setVolley();
        tvTime.setText("交易日期：" + ordeTitle.getDate().replace(".0", ""));
        tvDdh.setText("订单号：" + ordeTitle.getId());
        tvMoney.setText("总金额：" + ordeTitle.getCost());
    }

    List<OrderDetails> orderDetails;
    private void setVolley() {
        VolleyTo volleyTo  = new VolleyTo();
        volleyTo.setUrl("getOrderById")
                .setJsonObject("id",ordeTitle.getId())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        orderDetails = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<OrderDetails>>() {
                                }.getType());
                        listView.setAdapter(new OrderDetailAdapter(OrderDetailsActivity.this, orderDetails));
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
        listView = findViewById(R.id.list_view);
        tvDdh = findViewById(R.id.tv_ddh);
        tvTime = findViewById(R.id.tv_time);
        tvMoney = findViewById(R.id.tv_money);
    }
}
