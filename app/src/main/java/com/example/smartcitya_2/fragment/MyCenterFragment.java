package com.example.smartcitya_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.bean.UserInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 10:41
 */
public class MyCenterFragment extends Fragment {
    private TextView title;
    private TextView title1;
    private GlideImagView ivPhoto;
    private TextView tvName;
    private LinearLayout layoutInfo;
    private LinearLayout layoutOrder;
    private LinearLayout layoutPwd;
    private LinearLayout layoutYjfk;
    private Button btExit;

    public MyCenterFragment() {
    }

    private AppHomeActivity appHomeActivity;

    public MyCenterFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static MyCenterFragment newInstance(AppHomeActivity appHomeActivity) {
        return new MyCenterFragment(appHomeActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_center_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("个人中心");
        setVolley();
        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("个人信息"));
            }
        });
        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("我的订单"));
            }
        });

        layoutPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("修改密码"));
            }
        });
        layoutYjfk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("意见反馈"));
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setVolley();
        }
        super.onHiddenChanged(hidden);
    }

    public UserInfo userInfo;

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("getUserInfo")
                .setDialog(getActivity())
                .setJsonObject("userid", AppClient.getUserId(AppClient.username))
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userInfo = new Gson().fromJson(jsonObject.optJSONArray(Util.Rows).optJSONObject(0).toString()
                                , UserInfo.class);
                        tvName.setText("昵称：" + userInfo.getName());
                        ivPhoto.setMyImageUrl(userInfo.getAvatar());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void initView() {
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        ivPhoto = getView().findViewById(R.id.iv_photo);
        tvName = getView().findViewById(R.id.tv_name);
        layoutInfo = getView().findViewById(R.id.layout_info);
        layoutOrder = getView().findViewById(R.id.layout_order);
        layoutPwd = getView().findViewById(R.id.layout_pwd);
        layoutYjfk = getView().findViewById(R.id.layout_yjfk);
        btExit = getView().findViewById(R.id.bt_exit);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AppHomeActivity.class));
                appHomeActivity.finish();
            }
        });
    }
}
