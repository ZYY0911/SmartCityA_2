package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.NewDetails;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 10:27
 */
public class NewAdapter extends ArrayAdapter<NewList> {

    public NewAdapter(@NonNull Context context, @NonNull List<NewList> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);

        GlideImagView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemTitle = convertView.findViewById(R.id.item_title);
        TextView itemContext = convertView.findViewById(R.id.item_context);
        final TextView itemMsg = convertView.findViewById(R.id.item_msg);
        NewList newList = getItem(position);
        itemImage.setMyImageUrl(newList.getPicture());
        itemTitle.setText(newList.getTitle());
        itemContext.setText(newList.getAbstractX());
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getNewsInfoById")
                .setJsonObject("newsid", newList.getNewsid())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        NewDetails newDetails = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , NewDetails.class);
                        itemMsg.setText("评论：" + newDetails.getAudienceCount() + " 日期：" + newDetails.getPublicTime());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        return convertView;
    }


}
