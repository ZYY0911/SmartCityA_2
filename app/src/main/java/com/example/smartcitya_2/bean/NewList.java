package com.example.smartcitya_2.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 10:24
 */
public class NewList implements Serializable {

    /**
     * newsid : 1
     * newsType : 时政
     * picture : http://192.168.43.149:8080/mobileA/images/1.jpg
     * abstract : 10月1日，中华人民共和国成立71周年，天安门广场将举行2020年国庆升旗仪式。今年国庆的升旗仪式，有什么特殊的意义？又会有哪些新亮点？戳直播，看五星红旗在天安门广场冉冉升起，一起祝福祖国！
     * title : 天安门广场举行国庆升旗仪式
     * url : http://mbd.baidu.com/webpage?type=live&action=liveshow&source=search&room_id=3941827688
     */

    private String newsid;
    private String newsType;
    private String picture;
    @SerializedName("abstract")
    private String abstractX;
    private String title;
    private String url;
    /**
     * publicTime : 2020-10-01 06:00:00
     * subject : 国庆专题
     * recommand : 1
     * praiseCount : 500
     * audienceCount : 600
     */

    private String publicTime;
    private String subject;
    private int recommand;
    private int praiseCount;
    private String audienceCount;


    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getRecommand() {
        return recommand;
    }

    public void setRecommand(int recommand) {
        this.recommand = recommand;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(String audienceCount) {
        this.audienceCount = audienceCount;
    }
}
