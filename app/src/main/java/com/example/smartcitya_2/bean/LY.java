package com.example.smartcitya_2.bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 10:31
 */
public class LY {
    private List<Bitmap> bitmaps;
    private String title,msg;
    private String time;

    public LY(List<Bitmap> bitmaps, String title, String msg, String time) {
        this.bitmaps = bitmaps;
        this.title = title;
        this.msg = msg;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
