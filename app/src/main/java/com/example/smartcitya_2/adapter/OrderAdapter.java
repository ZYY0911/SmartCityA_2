package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.media.tv.TvContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.OrdeTitle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 11:33
 */
public class OrderAdapter extends ArrayAdapter<OrdeTitle> {
    public OrderAdapter(@NonNull Context context, @NonNull List<OrdeTitle> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
            holder = new ViewHolder();

            holder.itemNum = convertView.findViewById(R.id.item_num);
            holder.itemType = convertView.findViewById(R.id.item_type);
            holder.itemTime = convertView.findViewById(R.id.item_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrdeTitle ordeTitle = getItem(position);
        holder.itemNum.setText(ordeTitle.getId() + "");
        holder.itemTime.setText(ordeTitle.getDate());
        holder.itemType.setText(ordeTitle.getType());
        return convertView;
    }

    static class ViewHolder {
        private TextView itemNum;
        private TextView itemType;
        private TextView itemTime;

    }

}
