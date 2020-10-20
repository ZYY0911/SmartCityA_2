package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.PLAdapter;
import com.example.smartcitya_2.bean.NewComment;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.GlideImagView;
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
 * @Create by 张瀛煜 on 2020/10/19 at 9:37
 */
public class DjdtDetailsActivity extends AppCompatActivity {

    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ImageView ivImage;
    private TextView tvMsg;
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
        setContentView(R.layout.djdt_details);
        initView();
        newList = (NewList) getIntent().getSerializableExtra("info");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        title.setText(newList.getTitle());
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMsg.setText(newList.getAbstractX());
        ivImage.setImageResource(newList.getRecommand());
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
                        adapter = new PLAdapter(DjdtDetailsActivity.this, newComments);
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
        tvPl = findViewById(R.id.tv_pl);
        plList = findViewById(R.id.pl_list);
        etPl = findViewById(R.id.et_pl);
    }
}
