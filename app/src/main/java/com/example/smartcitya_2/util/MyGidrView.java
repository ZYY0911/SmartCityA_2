package com.example.smartcitya_2.util;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:08
 */
public class MyGidrView extends GridView {
    public MyGidrView(Context context) {
        super(context);
    }

    public MyGidrView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGidrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
