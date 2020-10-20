package com.example.smartcitya_2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.PhotoAdapter;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.MyGidrView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 11:20
 */
public class MotifImag extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutInfo;
    private GlideImagView ivPhoto;
    private MyGidrView girdView;
    private Integer images[] = {R.mipmap.user1, R.mipmap.user2,
            R.mipmap.user3, R.mipmap.user4, R.mipmap.user5, R.mipmap.user6, R.mipmap.user7, R.mipmap.user8};
    List<Integer> integers;
    private int index = 0;
    private String infos;
    String imageInfo = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motif_layout);
        initView();
        title.setText("修改头像");
        integers = new ArrayList<>();
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        infos = getIntent().getStringExtra("info");
        ivPhoto.setMyImageUrl(infos);
        for (int i = 0; i < images.length; i++) {
            integers.add(images[i]);
        }
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("date", imageInfo);
                intent.putExtra("index", index);
                setResult(ivPhoto.getTag() == null ? RESULT_CANCELED : RESULT_OK, intent);
                finish();
            }
        });
        girdView.setAdapter(new PhotoAdapter(MotifImag.this, integers));
        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ivPhoto.setImageResource(integers.get(position));
                imageInfo = "user" + (position + 1) + ".png";
                index = position;
            }
        });
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        layoutInfo = findViewById(R.id.layout_info);
        ivPhoto = findViewById(R.id.iv_photo);
        girdView = findViewById(R.id.gird_view);
    }
}
