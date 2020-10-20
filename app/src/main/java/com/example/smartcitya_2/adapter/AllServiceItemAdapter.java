package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.ServiceInfo;
import com.example.smartcitya_2.util.CircleImageView;
import com.example.smartcitya_2.util.OnClickItem;
import com.example.smartcitya_2.util.RoundRectImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:33
 */
public class AllServiceItemAdapter extends ArrayAdapter<ServiceInfo> {


    public AllServiceItemAdapter(@NonNull Context context, @NonNull List<ServiceInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.service_item, parent, false);
            holder = new ViewHolder();
            holder.itemImage = convertView.findViewById(R.id.item_image);
            holder.itemName = convertView.findViewById(R.id.item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ServiceInfo serviceInfo = getItem(position);
        holder.itemImage.setMyImageUrl(serviceInfo.getIcon());
        holder.itemName.setText(serviceInfo.getServiceName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position, serviceInfo.getServiceName());
            }
        });
        return convertView;
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    static class ViewHolder {

        private CircleImageView itemImage;
        private TextView itemName;
    }

    private void initView() {

    }
}

