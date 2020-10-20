package com.example.smartcitya_2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.EmptyServiceActivity;
import com.example.smartcitya_2.adapter.AllServiceAdapter;
import com.example.smartcitya_2.bean.ServiceInfo;
import com.example.smartcitya_2.dialog.SearchDialog;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.OnClickItem;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 19:15
 */
public class AllServiceFragment extends Fragment {
    private LinearLayout titleLayout;
    private EditText etSearch;
    private Button btSubmit;
    private ExpandableListView expandList;
    private TextView title;
    private TextView title1;

    public AllServiceFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public AllServiceFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static AllServiceFragment newInstance(AppHomeActivity appHomeActivity) {
        return new AllServiceFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_service_framgnet, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("全部服务");
        setVoley_Type();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog searchDialog = new SearchDialog(etSearch.getText().toString());
                searchDialog.show(getChildFragmentManager(), "");

            }
        });

    }

    List<String> strings;

    private void setVoley_Type() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getAllServiceType")
                .setDialog(getActivity())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Util.Rows);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            strings.add(jsonArray.optString(i));

                        }
                        setVolley_All();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        setVoley_Type();

                    }
                }).start();
    }

    Map<String, List<ServiceInfo>> map;


    private void setVolley_All() {
        map = new HashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            volleyTo.setUrl("getServiceByType")
                    .setJsonObject("serviceType", strings.get(i))
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<ServiceInfo> serviceInfos = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                    , new TypeToken<List<ServiceInfo>>() {
                                    }.getType());
                            map.put(strings.get(finalI), serviceInfos);
                            if (map.size() == strings.size() - 1) {
                                setAdapter();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }

    private void setAdapter() {
        AllServiceAdapter allServiceAdapter = new AllServiceAdapter(map, strings);
        expandList.setAdapter(allServiceAdapter);
        allServiceAdapter.setOnClickItem(new OnClickItem() {
            @Override
            public void onClick(int position, String name) {
                if (name.equals("地铁查询")) {
                    appHomeActivity.switchFragment(appHomeActivity.map.get("地铁查询"));
                } else if (name.equals("新闻中心")) {
                    appHomeActivity.switchFragment(appHomeActivity.map.get("新闻"));
                } else {
                    EmptyServiceActivity.newInstance("", "", name, getActivity());
                }
            }
        });

    }

    private void initView() {
        titleLayout = getView().findViewById(R.id.title_layout);
        etSearch = getView().findViewById(R.id.et_search);
        btSubmit = getView().findViewById(R.id.bt_submit);
        expandList = getView().findViewById(R.id.expand_list);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        expandList.setGroupIndicator(null);
    }
}
