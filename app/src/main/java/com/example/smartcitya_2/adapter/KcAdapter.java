package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 9:54
 */
public class KcAdapter extends ArrayAdapter<String> {
    public KcAdapter(@NonNull Context context,  @NonNull List<String> objects) {
        super(context, 0, objects);
    }


    private int postion=0;

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView itemName;
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.kc_item, parent, false);
        itemName = convertView.findViewById(R.id.item_name);
        if (position==postion){
            itemName.setTextColor(Color.RED);
        }
        itemName.setText(getItem(position));
        return convertView;
    }

}
