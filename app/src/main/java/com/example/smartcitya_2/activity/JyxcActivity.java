package com.example.smartcitya_2.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.TakePhotoAdapter;
import com.example.smartcitya_2.bean.LY;
import com.example.smartcitya_2.util.OnClickItem;
import com.example.smartcitya_2.util.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 10:19
 */
public class JyxcActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private EditText etTitle;
    private EditText etMsg;
    private GridView girdPhoto;
    private Button btSubmit;
    private AppClient appClient;
    private List<LY> lyList;

    List<Bitmap> bitmaps;
    TakePhotoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jyxc_layout);
        initView();
        appClient = (AppClient) getApplication();
        lyList = appClient.getLyList();
        title.setText("建言献策");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title1.setText("我的留言");
        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JyxcActivity.this, WdlyActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);

            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmaps.remove(bitmaps.size() - 1);
                List<Bitmap> choose = new ArrayList<>();
                for (int i = 0; i < bitmaps.size(); i++) {
                    choose.add(bitmaps.get(i));
                }
                lyList.add(new LY(choose, etTitle.getText().toString()
                        , etMsg.getText().toString(), Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date())));
                etMsg.setText("");
                etTitle.setText("");
                bitmaps.clear();
                bitmaps.add(null);
                adapter.notifyDataSetChanged();
                Util.showToast("提交成功", JyxcActivity.this);
            }
        });
        bitmaps = new ArrayList<>();
        bitmaps.add(null);
        adapter = new TakePhotoAdapter(this, bitmaps);
        girdPhoto.setAdapter(adapter);
        adapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                bitmaps.add(0, bitmap);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        etTitle = findViewById(R.id.et_title);
        etMsg = findViewById(R.id.et_msg);
        girdPhoto = findViewById(R.id.gird_photo);
        btSubmit = findViewById(R.id.bt_submit);
    }
}
