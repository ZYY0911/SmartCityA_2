package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.HomeService;
import com.example.smartcitya_2.bean.ServiceInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.CircleImageView;
import com.example.smartcitya_2.util.OnClickItem;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:50
 */
public class HomeServiceAdapter extends ArrayAdapter<HomeService> {

    public HomeServiceAdapter(@NonNull Context context, @NonNull List<HomeService> objects) {
        super(context, 0, objects);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.service_item, parent, false);
        final CircleImageView itemImage = convertView.findViewById(R.id.item_image);
        final TextView itemName = convertView.findViewById(R.id.item_name);
        if (position == 9) {
            itemName.setText("更多服务");
            itemImage.setImageResource(R.mipmap.more_service);
        } else {
            HomeService homeService = getItem(position);
            VolleyTo volleyTo = new VolleyTo();
            volleyTo.setUrl("service_info")
                    .setJsonObject("serviceid", homeService.getId())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            ServiceInfo serviceInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                    , ServiceInfo.class);
                            itemImage.setMyImageUrl(serviceInfo.getIcon());
                            itemName.setText(serviceInfo.getServiceName());
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClick(position, itemName.getText().toString());
            }
        });
        return convertView;
    }

    private OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    private void initView() {


    }
}

