package com.example.smartcitya_2.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 11:26
 */
public class MyVideoView extends VideoView {
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0,widthMeasureSpec)
                ,getDefaultSize(0,heightMeasureSpec));
    }
}
