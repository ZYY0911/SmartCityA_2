package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.util.MyImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:03
 */
public class ImageAdapter extends AppCompatActivity {
    private ImageView image;

    public static void newInstance(int image, Context context) {
        Intent intent = new Intent(context, ImageAdapter.class);
        intent.putExtra("info", image);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        initView();
        image.setImageResource(getIntent().getIntExtra("info", R.mipmap.ditie4));
        image.setOnTouchListener(new MyImageView(image));
    }

    private void initView() {
        image = findViewById(R.id.image);
    }
}
