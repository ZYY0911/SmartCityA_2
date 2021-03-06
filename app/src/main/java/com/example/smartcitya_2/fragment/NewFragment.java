package com.example.smartcitya_2.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.NewDetailsActivity;
import com.example.smartcitya_2.adapter.NewAdapter2;
import com.example.smartcitya_2.bean.HomeImage;
import com.example.smartcitya_2.bean.NewDetails;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:51
 */
public class NewFragment extends Fragment {
    private TextView title;
    private TextView title1;
    private ViewFlipper newFlipper;
    private LinearLayout layoutNew;
    private ListView listView;

    public NewFragment() {
    }

    private AppHomeActivity appHomeActivity;

    public NewFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static NewFragment newInstance(AppHomeActivity appHomeActivity) {
        return new NewFragment(appHomeActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_framgent, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("新闻");
        setVolely_Image();
        setVolley_New_List();

    }


    private void setVolley_New_List() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getNEWsList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        newLists = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<NewList>>() {
                                }.getType());
                        setVolley_New();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<String> strings;

    private void setVolley_New() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllNewsType")
                .setDialog(getActivity())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            strings.add(jsonArray.optJSONObject(i).optString("newstype"));
                        }
                        for (int i = 0; i < strings.size(); i++) {
                            final TextView textView = new TextView(getActivity());
                            textView.setText(strings.get(i));
                            if (i == 0) {
                                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                                textView.setBackgroundResource(R.drawable.text_line);
                            } else {
                                textView.setTextColor(Color.parseColor("#333333"));
                                textView.setBackgroundResource(R.drawable.text_no_line);
                            }
                            textView.setTextSize(20);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            layoutParams.setMargins(20, 0, 20, 0);
                            textView.setLayoutParams(layoutParams);
                            final int finalI = i;
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int j = 0; j < layoutNew.getChildCount(); j++) {
                                        TextView textView1 = (TextView) layoutNew.getChildAt(j);
                                        if (j == finalI) {
                                            textView1.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            textView1.setBackgroundResource(R.drawable.text_line);
                                        } else {
                                            textView1.setTextColor(Color.parseColor("#333333"));
                                            textView1.setBackgroundResource(R.drawable.text_no_line);
                                        }
                                    }
                                    setNewType(textView.getText().toString());
                                }
                            });
                            layoutNew.addView(textView);
                        }
                        setNewType(strings.get(0));
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    List<NewList> newLists;

    List<NewDetails> newDetails;

    private void setNewType(String text) {
        final List<NewList> newLists1 = new ArrayList<>();
        for (int i = 0; i < newLists.size(); i++) {
            NewList newList = newLists.get(i);
            if (newList.getNewsType().equals(text)) {
                newLists1.add(newList);
            }
        }
        newDetails = new ArrayList<>();
        for (int i = 0; i < newLists1.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            if (i == newLists1.size() - 1) {
                volleyTo.setDialog(getActivity());
            }
            volleyTo.setUrl("getNewsInfoById")

                    .setJsonObject("newsid", newLists1.get(i).getNewsid())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            NewDetails newDetails1 = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                    , NewDetails.class);
                            newDetails.add(newDetails1);
                            if (newDetails.size() == newLists1.size()) {
                                for (int j = 0; j < newLists1.size(); j++) {
                                    NewList newList = newLists1.get(j);
                                    for (int k = 0; k < newDetails.size(); k++) {
                                        NewDetails newDetail = newDetails.get(k);
                                        if (newList.getNewsid().equals(newDetail.getNewsid())) {
                                            newList.setPublicTime(newDetail.getPublicTime());
                                            newList.setSubject(newDetail.getSubject());
                                            newList.setRecommand(newDetail.getRecommand());
                                            newList.setPraiseCount(newDetail.getPraiseCount());
                                            newList.setAudienceCount(newDetail.getAudienceCount());
                                            newLists1.set(j, newList);
                                        }
                                    }
                                }
                                Collections.sort(newLists1, new Comparator<NewList>() {
                                    @Override
                                    public int compare(NewList o1, NewList o2) {
                                        Date date = null, date1 = null;
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        try {
                                            date = format.parse(o1.getPublicTime());
                                            date1 = format.parse(o2.getPublicTime());

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        return date1.compareTo(date);
                                    }
                                });
                                listView.setAdapter(new NewAdapter2(getActivity(), newLists1));
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        NewDetailsActivity.newInstance(newLists1.get(position), getActivity());
                                    }
                                });

                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }


    }


    List<HomeImage> homeImages;

    private void setVolely_Image() {
        final VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getImages")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        homeImages = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<HomeImage>>() {
                                }.getType());
                        for (int i = 0; i < homeImages.size(); i++) {
                            GlideImagView glideImagView = new GlideImagView(getActivity());
                            glideImagView.setMyImageUrl(homeImages.get(i).getPath());
                            glideImagView.setScaleType(ImageView.ScaleType.FIT_XY);
                            final int finalI = i;
                            glideImagView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    VolleyTo volleyTo1  =new VolleyTo();
                                    volleyTo1.setUrl("getNEWSContent")
                                            .setJsonObject("newsid", homeImages.get(finalI).getNewid())
                                            .setVolleyLo(new VolleyLo() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    NewList newDetails1 = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                                            , NewList.class);
                                                    NewDetailsActivity.newInstance(newDetails1, getActivity());

                                                }

                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {

                                                }
                                            }).start();
                                }
                            });
                            newFlipper.addView(glideImagView);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    private void initView() {
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        newFlipper = getView().findViewById(R.id.new_flipper);
        layoutNew = getView().findViewById(R.id.layout_new);
        listView = getView().findViewById(R.id.list_view);
    }
}



