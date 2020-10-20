package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.util.GlideImagView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:59
 */
public class DjdtAdapter extends ArrayAdapter<NewList> {
    public DjdtAdapter(@NonNull Context context, @NonNull List<NewList> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        GlideImagView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemTitle = convertView.findViewById(R.id.item_title);
        TextView itemContext = convertView.findViewById(R.id.item_context);
        TextView itemMsg = convertView.findViewById(R.id.item_msg);
        NewList newList = getItem(position);
        itemImage.setImageResource(newList.getRecommand());
        itemTitle.setText(newList.getTitle());
        itemContext.setText(newList.getAbstractX());
        itemMsg.setText("时间：" + newList.getPublicTime());
        itemMsg.setGravity(Gravity.RIGHT);
        return convertView;

    }
}

