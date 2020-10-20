package com.example.smartcitya_2.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:09
 */
public class GlideImagView extends androidx.appcompat.widget.AppCompatImageView {
    public GlideImagView(Context context) {
        super(context);
    }

    public GlideImagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GlideImagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setMyImageUrl(String path) {
        Glide.with(getContext()).load(path).into(this);
    }
}
