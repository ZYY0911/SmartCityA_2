package com.example.smartcitya_2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.LyAdapter;
import com.example.smartcitya_2.bean.LY;

import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 10:38
 */
public class WdlyActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ListView listView;
    private TextView tvNull;
    private List<LY> lyList;
    private AppClient appClient;
    private List<LY> ssp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdly_layout);
        initView();
        appClient = (AppClient) getApplication();
        if (getIntent().getIntExtra("index", 0) == 0) {
            title.setText("我的留言");
            lyList = appClient.getLyList();
            Collections.reverse(lyList);
            listView.setAdapter(new LyAdapter(this, lyList));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailsActivity.newInstance(position,0,WdlyActivity.this);
                }
            });
            listView.setEmptyView(tvNull);
        } else {
            title.setText("随手拍");
            ssp = appClient.getSsp();
            Collections.reverse(ssp);
            listView.setAdapter(new LyAdapter(this, ssp));
            listView.setEmptyView(tvNull);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailsActivity.newInstance(position,1,WdlyActivity.this);
                }
            });
        }

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        listView = findViewById(R.id.list_view);
        tvNull = findViewById(R.id.tv_null);
    }
}
