package com.example.smartcitya_2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.NewAdapter3;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 20:02
 */
public class NewDetailsActivity extends AppCompatActivity {

    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private GlideImagView ivImage;
    private TextView tvMsg;
    private MyListView plList;
    private MyListView tjList;
    private EditText etPl;
    private NewList newList;
    private TextView tvPl;

    public static void newInstance(NewList newList, Context context) {
        Intent intent = new Intent(context, NewDetailsActivity.class);
        intent.putExtra("info", newList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_details);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        newList = (NewList) getIntent().getSerializableExtra("info");
        title.setText(newList.getTitle());
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMsg.setText(newList.getAbstractX());
        ivImage.setMyImageUrl(newList.getPicture());
        setVolley_Pl();
        setVolley_TJ();
        etPl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    VolleyTo volleyTo = new VolleyTo();
                    volleyTo.setUrl("publicComit")
                            //{"username":"user1","newsid":"1","commit":"评论","commitTime":"yyyy.MM.dd HH:mm:ss"}
                            .setJsonObject("username", AppClient.username)
                            .setJsonObject("newsid", newList.getNewsid())
                            .setJsonObject("commit", etPl.getText().toString())
                            .setDialog(NewDetailsActivity.this)
                            .setJsonObject("commitTime", Util.simpleDate("yyyy.MM.dd HH:mm:ss", new Date()))
                            .setVolleyLo(new VolleyLo() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    if (jsonObject.optString("RESULT").equals("S")) {
                                        Util.showToast("评论成功", NewDetailsActivity.this);
                                        setVolley_Pl();
                                    } else {
                                        Util.showToast("评论失败", NewDetailsActivity.this);
                                    }
                                }

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Util.showToast("评论成功", NewDetailsActivity.this);

                                }
                            }).start();

                }
                return false;
            }
        });
    }


    List<Integer> integers;

    private void setVolley_TJ() {
        integers = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getRecommend")
                .setDialog(NewDetailsActivity.this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            integers.add(jsonArray.optJSONObject(i).optInt("newsid"));
                        }
                        setVolley_All();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<NewList> allNewList;

    private void setVolley_All() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getNEWsList")
                .setDialog(NewDetailsActivity.this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        allNewList = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<NewList>>() {
                                }.getType());
                        setTjList();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setTjList() {
        for (int i = allNewList.size() - 1; i >= 0; i--) {
            NewList newList1 = allNewList.get(i);
            if (newList1.getNewsid().equals(newList.getNewsid())) {
                allNewList.remove(i);
            } else if (!integers.contains(Integer.parseInt(newList1.getNewsid()))) {
                allNewList.remove(i);
            }
        }
        Random random = new Random();
        int index = random.nextInt(3) + 1;
        if (index > allNewList.size()) {
            index = allNewList.size();
        }
        final List<NewList> newListList = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            newListList.add(allNewList.get(i));
        }
        tjList.setAdapter(new NewAdapter3(NewDetailsActivity.this, newListList));
        tjList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewDetailsActivity.newInstance(newListList.get(position), NewDetailsActivity.this);
                finish();
            }
        });

    }

    List<NewComment> newComments;

    private void setVolley_Pl() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getCommitById")
                .setJsonObject("id", newList.getNewsid())
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
                        plList.setAdapter(new PLAdapter(NewDetailsActivity.this, newComments));
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
        plList = findViewById(R.id.pl_list);
        tjList = findViewById(R.id.tj_list);
        etPl = findViewById(R.id.et_pl);
        tvPl = findViewById(R.id.tv_pl);
    }
}
