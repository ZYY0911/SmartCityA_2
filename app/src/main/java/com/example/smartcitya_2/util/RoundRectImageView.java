package com.example.smartcitya_2.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 10:02
 */
public class RoundRectImageView extends androidx.appcompat.widget.AppCompatImageView {

    public RoundRectImageView(Context context) {
        super(context);
    }

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        if (width >= 12 && height > 12) {
//            Path path = new Path();
//            //四个圆角
//            path.moveTo(20, 0);
//            path.lineTo(width - 20, 0);
//            path.quadTo(width, 0, width, 20);
//            path.lineTo(width, height - 20);
//            path.quadTo(width, height, width - 20, height);
//            path.lineTo(20, height);
//            path.quadTo(0, height, 0, height - 20);
//            path.lineTo(0, 20);
//            path.quadTo(0, 0, 20, 0);
//            canvas.clipPath(path);
          Path path = new Path();
          path.addRoundRect(new RectF(0,0,width,height),20,20, Path.Direction.CCW);
       //   path.addCircle(width/2,height/2,height/2,Path.Direction.CCW);
          canvas.clipPath(path);
        }
        super.onDraw(canvas);

    }
}
