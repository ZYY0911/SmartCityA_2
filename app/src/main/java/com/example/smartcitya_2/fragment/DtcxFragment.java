package com.example.smartcitya_2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.DtDetailsActivity;
import com.example.smartcitya_2.adapter.DtcxAdapter;
import com.example.smartcitya_2.bean.DtInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 18:32
 */
public class DtcxFragment extends Fragment {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private ListView listDt;

    public DtcxFragment() {
    }

    private AppHomeActivity appHomeActivity;

    public DtcxFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static DtcxFragment newInstance(AppHomeActivity appHomeActivity) {
        return new DtcxFragment(appHomeActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dtcx_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("地铁查询");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.backClick();
            }
        });
        setVolley();
    }


    List<DtInfo> dtInfos;

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getSubwaysByStation")
                .setDialog(getActivity())
                .setJsonObject("stationName", "建国门站")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        dtInfos = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).toString()
                                , new TypeToken<List<DtInfo>>() {
                                }.getType());
                        Collections.sort(dtInfos, new Comparator<DtInfo>() {
                            @Override
                            public int compare(DtInfo o1, DtInfo o2) {
                                return o1.getTime()-o2.getTime();
                            }
                        });
                        setListAdapter();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        setVolley();

                    }
                }).start();
    }

    private void setListAdapter() {
        listDt.setAdapter(new DtcxAdapter(getActivity(), dtInfos));
        listDt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DtDetailsActivity.newInstance(dtInfos.get(position), getActivity());
            }
        });

    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        listDt = getView().findViewById(R.id.list_dt);
    }
}
