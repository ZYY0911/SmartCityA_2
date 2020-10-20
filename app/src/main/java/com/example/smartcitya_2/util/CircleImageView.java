package com.example.smartcitya_2.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 10:02
 */
public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int width, height;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addCircle(width / 2, height / 2, Math.min(width, height) / 2, Path.Direction.CCW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }

    public void setMyImageUrl(String path) {
        Glide.with(getContext()).load(path).into(this);
    }
}
