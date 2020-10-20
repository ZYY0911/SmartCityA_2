package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.KcAdapter;
import com.example.smartcitya_2.adapter.PLAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 9:52
 */
public
class KcListActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private GridView girdView;

    public static void newInstance(String msg, Context context) {
        Intent intent = new Intent(context, KcListActivity.class);
        intent.putExtra("msg", msg);
        context.startActivity(intent);
    }


    List<String> strings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kc_layout);
        initView();
        title.setText(getIntent().getStringExtra("msg"));
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        strings = new ArrayList<>();
        strings.add("01  前言");
        strings.add("02  马克思主义");
        strings.add("03  马克思主义为什么要中国化");
        strings.add("04  从站起来，到富起来");
        strings.add("05  毛泽东思想");
        strings.add("06  邓小平理论");
        strings.add("07  实事求是");
        final KcAdapter adapter = new KcAdapter(this,strings);
        girdView.setAdapter(adapter);
        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = adapter.getPostion();
                if (index<position){
                    adapter.setPostion(position);
                }
                PlayKCactivity.newInstance(position, strings.get(position), KcListActivity.this);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        girdView = findViewById(R.id.gird_view);
    }
}
