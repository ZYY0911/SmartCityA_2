package com.example.smartcitya_2.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.DjdtAdapter;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 9:26
 */
public class DjdtActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutNew;
    private ListView newList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.djdt_layout);

        initView();
        title.setText("党建动态");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setType();
    }

    List<String> strings;

    private void setType() {
        strings = new ArrayList<>();
        strings.add("热点");
        strings.add("综合");

        for (int i = 0; i < strings.size(); i++) {
            final TextView textView = new TextView(this);
            textView.setText(strings.get(i));
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setBackgroundResource(R.drawable.text_line);
            } else {
                textView.setTextColor(Color.parseColor("#333333"));
                textView.setBackgroundResource(R.drawable.text_no_line);
            }
            textView.setTextSize(20);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(20, 0, 20, 0);
            textView.setLayoutParams(layoutParams);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < layoutNew.getChildCount(); j++) {
                        TextView textView1 = (TextView) layoutNew.getChildAt(j);
                        if (j == finalI) {
                            textView1.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textView1.setBackgroundResource(R.drawable.text_line);
                        } else {
                            textView1.setTextColor(Color.parseColor("#333333"));
                            textView1.setBackgroundResource(R.drawable.text_no_line);
                        }
                    }
                    setNewType(textView.getText().toString());
                }
            });
            layoutNew.addView(textView);
        }
        setNewType("热点");
    }

    List<NewList> newLists;

    private void setNewType(String toString) {
        newLists = new ArrayList<>();
        if (toString.equals("热点")) {
            NewList newList = new NewList();
            newList.setTitle("十三五");
            newList.setRecommand(R.mipmap.b);
            newList.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                    "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
            newList.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
            newLists.add(newList);
            NewList newList1 = new NewList();
            newList1.setRecommand(R.mipmap.a);
            newList1.setTitle("十三五规划");
            newList1.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                    "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
            newList1.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
            newLists.add(newList1);
        } else {
            NewList newList = new NewList();
            newList.setTitle("中国");
            newList.setRecommand(R.mipmap.b);
            newList.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                    "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
            newList.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
            newLists.add(newList);
            NewList newList1 = new NewList();
            newList1.setRecommand(R.mipmap.a);
            newList1.setTitle("中国发展");
            newList1.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                    "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
            newList1.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
            newLists.add(newList1);
        }
        newList.setAdapter(new DjdtAdapter(this, newLists));
        newList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DjdtDetailsActivity.newInstance(newLists.get(position), DjdtActivity.this);
            }
        });
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        layoutNew = findViewById(R.id.layout_new);
        newList = findViewById(R.id.new_list);
    }
}

