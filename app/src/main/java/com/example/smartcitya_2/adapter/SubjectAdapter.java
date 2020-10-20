package com.example.smartcitya_2.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.util.RoundRectImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 10:00
 */
public class SubjectAdapter extends ArrayAdapter<String> {

    public SubjectAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
    }

    int iamge[] = {R.mipmap.a, R.mipmap.d, R.mipmap.e, R.mipmap.c};

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.subject_item, parent, false);
        RoundRectImageView itemImage = convertView.findViewById(R.id.item_image);
        TextView itemName = convertView.findViewById(R.id.item_name);

        itemImage.setImageResource(iamge[position]);
        itemName.setText(getItem(position));

        return convertView;
    }

    private void initView() {

    }
}

