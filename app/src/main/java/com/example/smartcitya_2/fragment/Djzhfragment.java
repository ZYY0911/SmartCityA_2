package com.example.smartcitya_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.DjdtActivity;
import com.example.smartcitya_2.activity.DjdtDetailsActivity;
import com.example.smartcitya_2.activity.DyxxActivity;
import com.example.smartcitya_2.activity.EmptyActivity;
import com.example.smartcitya_2.activity.HdActivity;
import com.example.smartcitya_2.activity.JyxcActivity;
import com.example.smartcitya_2.activity.SspActivity;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.Util;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 9:17
 */
public class Djzhfragment extends Fragment {
    private ViewFlipper viewFlipper;
    private LinearLayout layoutDjdt;
    private LinearLayout layoutDyxx;
    private LinearLayout layoutZzhd;
    private LinearLayout layoutJyxc;
    private LinearLayout layoutSsp;
    private TextView title;
    private TextView title1;

    public Djzhfragment() {
    }

    private AppHomeActivity appHomeActivity;

    public Djzhfragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static Djzhfragment newInstance(AppHomeActivity appHomeActivity) {
        return new Djzhfragment(appHomeActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.djzh_fragment, container, false);
    }


    int images[] = {R.mipmap.a, R.mipmap.b, R.mipmap.d, R.mipmap.c, R.mipmap.e};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("智慧党建");
        for (int i = 0; i < images.length; i++) {
            GlideImagView myImagView = new GlideImagView(getActivity());
            myImagView.setImageResource(images[i]);
            myImagView.setScaleType(ImageView.ScaleType.FIT_XY);
            myImagView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            myImagView.setTag(homeImages.get(i).getNewid());
            final int finalI = i;
            final int finalI1 = i;
            myImagView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewList newList = new NewList();
                    newList.setTitle("十三五");
                    newList.setRecommand(images[finalI1]);
                    newList.setAbstractX("随着工作生活信息化、网络化、数据化持续加深，传统的党建党员工方式对党员特别是年轻一代党员的吸引力和凝聚力在降低。智慧党建是运用信息化新技术，整合各方资源，更有效地加强组织管理，提高服务群众水平，扩大党在网络世界存在感和数字化影响力，提高党的执政能力，巩固党的执政基础的新平台、新模式、新形态。\n" +
                            "通过智慧党建系统建设，主要解决党建宣传、学习、管理、资源等方面的基础问题。使党建管理从模糊走向精确，改变了以往手工操作、人工管理等存在的随意性、模糊性不足，通过量化计分、实时智");
                    newList.setPublicTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
                    DjdtDetailsActivity.newInstance(newList, getActivity());
                }
            });
            viewFlipper.addView(myImagView);
        }
        layoutDjdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DjdtActivity.class));
            }
        });

        layoutDyxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DyxxActivity.class));
            }
        });
        layoutZzhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HdActivity.class));
            }
        });

        layoutJyxc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JyxcActivity.class));
            }
        });
        layoutSsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SspActivity.class));
            }
        });
    }

    private void initView() {
        viewFlipper = getView().findViewById(R.id.view_flipper);
        layoutDjdt = getView().findViewById(R.id.layout_djdt);
        layoutDyxx = getView().findViewById(R.id.layout_dyxx);
        layoutZzhd = getView().findViewById(R.id.layout_zzhd);
        layoutJyxc = getView().findViewById(R.id.layout_jyxc);
        layoutSsp = getView().findViewById(R.id.layout_ssp);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
    }
}

