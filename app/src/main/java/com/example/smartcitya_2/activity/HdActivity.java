package com.example.smartcitya_2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.DjdtAdapter;
import com.example.smartcitya_2.adapter.HdAdapter;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 10:05
 */
public class HdActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_layout);
        initView();
        title.setText("组织活动");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setNewType();
    }

    List<NewList> newLists;

    private void setNewType() {
        newLists = new ArrayList<>();
        final NewList newList = new NewList();
        newList.setTitle("百人书法比赛");
        newList.setRecommand(R.mipmap.hda);
        newList.setAudienceCount("1000");
        newList.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
        newList.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
        newLists.add(newList);
        NewList newList1 = new NewList();
        newList1.setRecommand(R.mipmap.a);
        newList1.setTitle("金秋健步走");
        newList.setAudienceCount("10");
        newList1.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
        newList1.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
        newLists.add(newList1);
        listView.setAdapter(new HdAdapter(this, newLists));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DhDetailsActivity.newInstance(newLists.get(position), HdActivity.this);
            }
        });
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        listView = findViewById(R.id.list_view);
    }
}
