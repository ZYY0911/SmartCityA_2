package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.ShowPhotoAdapter;
import com.example.smartcitya_2.bean.LY;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 10:57
 */
public class DetailsActivity extends AppCompatActivity {

    private int index, lx;
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private TextView tvMsg;
    private GridView girdPhoto;
    private TextView tvTime;

    public static void newInstance(int index, int lx, Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("lx", lx);
        context.startActivity(intent);
    }

    private LY ly;
    private AppClient appClient;
    private List<LY> lyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalis_layout);
        initView();
        appClient = (AppClient) getApplication();
        if (getIntent().getIntExtra("lx", 0) == 0) {
            lyList = appClient.getLyList();
        } else {
            lyList = appClient.getSsp();
        }
        ly = lyList.get(getIntent().getIntExtra("index", 1));
        title.setText(ly.getTitle());
        tvMsg.setText(ly.getMsg());
        tvTime.setText(ly.getTime());
        girdPhoto.setAdapter(new ShowPhotoAdapter(this, ly.getBitmaps()));
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
        tvMsg = findViewById(R.id.tv_msg);
        girdPhoto = findViewById(R.id.gird_photo);
        tvTime = findViewById(R.id.tv_time);
    }
}
