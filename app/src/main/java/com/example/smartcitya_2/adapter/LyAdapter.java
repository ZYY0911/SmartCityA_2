package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.LY;
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
public class LyAdapter extends ArrayAdapter<LY> {

    public LyAdapter(@NonNull Context context, @NonNull List<LY> objects) {
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
        LY ly = getItem(position);
        Log.i("aaa", "getView: "+ly.getBitmaps().size());
        if (ly.getBitmaps().size()==0){
            Log.i("aaa", "getView: GONE");
            itemImage.setVisibility(View.GONE);
        }else {
            Log.i("aaa", "getView: VISIBLE");
            itemImage.setVisibility(View.VISIBLE);
            itemImage.setImageBitmap(ly.getBitmaps().get(0));
        }
        itemTitle.setText(ly.getTitle());
        itemContext.setText(ly.getMsg());

        itemMsg.setText(" 日期：" + ly.getTime());
        itemMsg.setGravity(Gravity.RIGHT);

        return convertView;
    }


}
