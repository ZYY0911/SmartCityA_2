package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.PLAdapter;
import com.example.smartcitya_2.bean.NewComment;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.MyListView;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/19 at 10:13
 */
public class DhDetailsActivity extends AppCompatActivity {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ImageView ivImage;
    private TextView tvMsg;
    private TextView tvBm;
    private TextView tvSj;
    private TextView tvPl;
    private MyListView plList;
    private EditText etPl;
    private NewList newList;

    public static void newInstance(NewList newList, Context context) {
        Intent intent = new Intent(context, DjdtDetailsActivity.class);
        intent.putExtra("info", newList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dh_details);

        initView();
        newList = (NewList) getIntent().getSerializableExtra("info");
        title.setText(newList.getTitle());
        title1.setText("报名活动");
        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showToast("保存成功", DhDetailsActivity.this);
            }
        });
        tvMsg.setText(newList.getAbstractX());
        tvBm.setText("报名人数：" + newList.getAudienceCount());
        tvSj.setText("日期：" + newList.getPublicTime());
        setVolley_Pl();
        etPl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    NewComment newComment = new NewComment();
                    newComment.setCommit(etPl.getText().toString());
                    newComment.setCommitTime(Util.simpleDate("yyyy-MM-dd HH:mm:ss", new Date()));
                    newComment.setNum(1);
                    newComment.setReviewer("abc");
                    newComments.add(0, newComment);
                    adapter.notifyDataSetChanged();
                    tvPl.setText("评论(" + newComments.size() + ")");
                    etPl.setText("");
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    List<NewComment> newComments;
    PLAdapter adapter;

    private void setVolley_Pl() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getCommitById")
                .setJsonObject("id", "1")
                .setDialog(this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        newComments = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<NewComment>>() {
                                }.getType());
                        tvPl.setText("评论(" + newComments.size() + ")");
                        Collections.sort(newComments, new Comparator<NewComment>() {
                            @Override
                            public int compare(NewComment o1, NewComment o2) {
                                return o2.getNum() - o1.getNum();
                            }
                        });
                        adapter = new PLAdapter(DhDetailsActivity.this, newComments);
                        plList.setAdapter(adapter);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        itemImage = findViewById(R.id.item_image);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        ivImage = findViewById(R.id.iv_image);
        tvMsg = findViewById(R.id.tv_msg);
        tvBm = findViewById(R.id.tv_bm);
        tvSj = findViewById(R.id.tv_sj);
        tvPl = findViewById(R.id.tv_pl);
        plList = findViewById(R.id.pl_list);
        etPl = findViewById(R.id.et_pl);
    }
}
