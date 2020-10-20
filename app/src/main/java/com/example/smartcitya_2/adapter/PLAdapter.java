package com.example.smartcitya_2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.bean.NewComment;
import com.example.smartcitya_2.bean.UserInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.file.Path;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 20:15
 */
public class PLAdapter extends ArrayAdapter<NewComment> {

    public PLAdapter(@NonNull Context context, @NonNull List<NewComment> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pl_item, parent, false);
            holder = new ViewHolder();
            holder.itemImage = convertView.findViewById(R.id.item_image);
            holder.itemTitle = convertView.findViewById(R.id.item_title);
            holder.itemContext = convertView.findViewById(R.id.item_context);
            holder.itemMsg = convertView.findViewById(R.id.item_msg);
        NewComment comment = getItem(position);
        holder.itemMsg.setText(comment.getCommitTime());
        holder.itemContext.setText(comment.getCommit());
        holder.itemTitle.setText(comment.getReviewer());
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUserInfo")
                .setJsonObject("userid", AppClient.getUserId(comment.getReviewer()))
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        UserInfo userInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , UserInfo.class);
                        try {
                            Glide.with(getContext()).load(userInfo.getAvatar()).into(holder.itemImage);
                        } catch (Exception e){
                            Log.e("aaa", "onResponse: "+e.getMessage() );
                        }
                    }


                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
        return convertView;
    }

    static class ViewHolder {

        private GlideImagView itemImage;
        private TextView itemTitle;
        private TextView itemContext;
        private TextView itemMsg;
    }

    private void initView() {
    }
}
