package com.example.smartcitya_2.bean;

import java.io.Serializable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 11:32
 */
public class OrdeTitle  implements Serializable {

    /**
     * id : 1
     * type : 食品
     * date : 2020-10-01 11:43:53
     * cost : 200
     */

    private int id;
    private String type;
    private String date;
    private int cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
