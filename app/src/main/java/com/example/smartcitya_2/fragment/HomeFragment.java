package com.example.smartcitya_2.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.EmptyActivity;
import com.example.smartcitya_2.activity.EmptyServiceActivity;
import com.example.smartcitya_2.activity.NewDetailsActivity;
import com.example.smartcitya_2.adapter.HomeServiceAdapter;
import com.example.smartcitya_2.adapter.NewAdapter;
import com.example.smartcitya_2.adapter.SubjectAdapter;
import com.example.smartcitya_2.bean.HomeImage;
import com.example.smartcitya_2.bean.HomeService;
import com.example.smartcitya_2.bean.NewList;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.MyGidrView;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.MyListView;
import com.example.smartcitya_2.util.OnClickItem;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:57
 */
public class HomeFragment extends Fragment {
    private ViewFlipper viewFlipper;
    private MyGidrView serviceGird;
    private MyGidrView subjectGird;
    private LinearLayout layoutNew;
    private MyListView newList;

    public HomeFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public HomeFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static HomeFragment newInstance(AppHomeActivity appHomeActivity) {
        return new HomeFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        setVolley_Image();
        setVolley_Service();
        setVolley_Seuject();
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

    private void setNewType(String text) {
        List<NewList> newLists1 = new ArrayList<>();
        for (int i = 0; i < newLists.size(); i++) {
            NewList newList = newLists.get(i);
            if (newList.getNewsType().equals(text)) {
                newLists1.add(newList);
            }
        }
        newList.setAdapter(new NewAdapter(getActivity(), newLists1));


    }


    private void setVolley_Seuject() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllSubject")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String arr = jsonObject.optString(Util.Rows).replace("[", "").replace("]", "");
                        String[] arrs = arr.split(",");
                        final List<String> list = new ArrayList<>();
                        list.addAll(Arrays.asList(arrs));
                        subjectGird.setAdapter(new SubjectAdapter(getActivity(), list));
                        subjectGird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                EmptyActivity.newInstance(list.get(position), getActivity());
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    List<HomeService> homeServices;

    private void setVolley_Service() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getServiceInOrder")
                .setJsonObject("order", 2)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        homeServices = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<HomeService>>() {
                                }.getType());
                        HomeServiceAdapter adapter = new HomeServiceAdapter(getActivity(), homeServices);
                        serviceGird.setAdapter(adapter);
                        /**
                         *
                         */
                        adapter.setOnClickItem(new OnClickItem() {
                            @Override
                            public void onClick(int position, String name) {
                                if (name.equals("地铁查询")) {
                                    appHomeActivity.switchFragment(appHomeActivity.map.get("地铁查询"));
                                } else if (name.equals("新闻中心")) {
                                    appHomeActivity.switchFragment(appHomeActivity.map.get("新闻"));
                                }else if (name.equals("更多服务")) {
                                    appHomeActivity.switchFragment(appHomeActivity.map.get("全部服务"));
                                }else {
                                    EmptyServiceActivity.newInstance(homeServices.get(position).getId(), "","" ,getActivity());
                                }
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    List<HomeImage> homeImages;

    private void setVolley_Image() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getImages")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        homeImages = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<HomeImage>>() {
                                }.getType());
                        for (int i = 0; i < homeImages.size(); i++) {
                            GlideImagView myImagView = new GlideImagView(getActivity());
                            myImagView.setMyImageUrl(homeImages.get(i).getPath());
                            myImagView.setScaleType(ImageView.ScaleType.FIT_XY);
                            myImagView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                         //   myImagView.setTag(homeImages.get(i).getNewid());
                            final int finalI = i;
                            myImagView.setOnClickListener(new View.OnClickListener() {
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
                            viewFlipper.addView(myImagView);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        viewFlipper = getView().findViewById(R.id.view_flipper);
        serviceGird = getView().findViewById(R.id.service_gird);
        subjectGird = getView().findViewById(R.id.subject_gird);
        layoutNew = getView().findViewById(R.id.layout_new);
        newList = getView().findViewById(R.id.new_list);
    }
}
