package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.DtInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 18:41
 */
public class DtcxAdapter extends ArrayAdapter<DtInfo> {

    public DtcxAdapter(@NonNull Context context, @NonNull List<DtInfo> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dtcx_item, parent, false);
            holder = new ViewHolder();
            holder.itemName = convertView.findViewById(R.id.item_name);
            holder.itemNext = convertView.findViewById(R.id.item_next);
            holder.itemSj = convertView.findViewById(R.id.item_sj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DtInfo dtInfo = getItem(position);
        holder.itemName.setText(dtInfo.getName());
        holder.itemNext.setText("下一站：" + dtInfo.getNextname());
        holder.itemSj.setText("到达本站时间：" + dtInfo.getTime() + "分钟");
        return convertView;
    }


    static class ViewHolder {

        private TextView itemName;
        private TextView itemNext;
        private TextView itemSj;
    }

    private void initView() {
    }
}
