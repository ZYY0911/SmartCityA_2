package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.ServiceInfo;
import com.example.smartcitya_2.util.MyGidrView;
import com.example.smartcitya_2.util.OnClickItem;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:30
 */
public class AllServiceAdapter extends BaseExpandableListAdapter {
    private Map<String, List<ServiceInfo>> map;
    private List<String> strings;

    public AllServiceAdapter(Map<String, List<ServiceInfo>> map, List<String> strings) {
        this.map = map;
        this.strings = strings;
    }

    @Override
    public int getGroupCount() {
        return strings.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_item, parent, false);
        TextView itemText = convertView.findViewById(R.id.item_tv);
        itemText.setGravity(Gravity.CENTER_VERTICAL);
        itemText.setPadding(30, 10, 0, 10);
        itemText.setText(strings.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serive, parent, false);
        MyGidrView girdViewIte = convertView.findViewById(R.id.gird_view_item);
        AllServiceItemAdapter allServiceItemAdapter = new AllServiceItemAdapter(parent.getContext(), map.get(strings.get(groupPosition)));
        girdViewIte.setAdapter(allServiceItemAdapter);
        allServiceItemAdapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                onClickItem.onClick(position, name);
            }
        });
        return convertView;
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
