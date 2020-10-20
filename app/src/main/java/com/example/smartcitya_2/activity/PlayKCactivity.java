package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.PLAdapter;
import com.example.smartcitya_2.bean.NewComment;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
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
 * @Create by 张瀛煜 on 2020/10/19 at 9:56
 */
public class PlayKCactivity extends AppCompatActivity {

    private TextView title;
    private TextView title1;
    private VideoView videoView;
    private TextView tvPl;
    private ListView plList;
    private ImageView itemImage;
    private EditText etPl;

    public static void newInstance(int index, String title, Context context) {
        Intent intent = new Intent(context, PlayKCactivity.class);
        intent.putExtra("info", index);
        intent.putExtra("title", title);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_kc_layout);
        initView();
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
        title.setText(getIntent().getStringExtra("title"));
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (getIntent().getIntExtra("info", 0)) {
            case 0:
            case 5:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car1));
                break;
            case 4:
            case 1:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car2));
                break;
            case 2:
            case 6:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car3));
                break;
            case 3:
            case 7:
            default:
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car4));
                break;
        }
        videoView.start();
        setVolley_Pl();

    }

    List<NewComment> newComments;
    PLAdapter adapter;

    private void setVolley_Pl() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getCommitById")
                .setJsonObject("id", "1")
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
                        adapter = new PLAdapter(PlayKCactivity.this, newComments);
                        plList.setAdapter(adapter);
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        videoView = findViewById(R.id.video_view);
        tvPl = findViewById(R.id.tv_pl);
        plList = findViewById(R.id.pl_list);
        itemImage = findViewById(R.id.item_image);
        etPl = findViewById(R.id.et_pl);
    }
}
