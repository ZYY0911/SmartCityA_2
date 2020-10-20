package com.example.smartcitya_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.activity.MotifImag;
import com.example.smartcitya_2.bean.UserInfo;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.GlideImagView;
import com.example.smartcitya_2.util.Util;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 11:13
 */
public class MyInfoFragment extends Fragment {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private LinearLayout layoutInfo;
    private GlideImagView ivPhoto;
    private EditText etName;
    private EditText etSex;
    private EditText etTel;
    private EditText etIds;
    private Button btSave;
    private Integer images[] = {R.mipmap.user1, R.mipmap.user2,
            R.mipmap.user3, R.mipmap.user4, R.mipmap.user5, R.mipmap.user6, R.mipmap.user7, R.mipmap.user8};

    public MyInfoFragment() {

    }

    private AppHomeActivity appHomeActivity;

    public MyInfoFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static MyInfoFragment newInstance(AppHomeActivity appHomeActivity) {
        return new MyInfoFragment(appHomeActivity);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.motif_info_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("个人信息");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("个人中心"));
            }
        });
        setView();
        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotifImag.class);
                intent.putExtra("info", userInfo.getAvatar());
                startActivityForResult(intent, 1);

            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("setUserInfo")
                        //{userid:"1","name":"","avatar":"","phone":"phone","id":"1","gender":""}
                        .setJsonObject("userid", AppClient.getUserId(AppClient.username))
                        .setJsonObject("name", etName.getText().toString())
                        .setJsonObject("avatar", layoutInfo.getTag() == null ? "" : layoutInfo.getTag().toString())
                        .setJsonObject("phone", etTel.getText().toString())
                        .setJsonObject("id", "")
                        .setJsonObject("gender", etSex.getText().toString())
                        .setDialog(getActivity())
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showDialog("修改成功", getActivity());
                                } else {
                                    Util.showDialog("修改失败", getActivity());
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        }).start();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    ivPhoto.setImageResource(images[data.getIntExtra("index", 0)]);
                    layoutInfo.setTag(data.getStringExtra("date"));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            setView();
        }
        super.onHiddenChanged(hidden);

    }

    UserInfo userInfo;

    private void setView() {
        userInfo = ((MyCenterFragment) appHomeActivity.map.get("个人中心")).userInfo;
        ivPhoto.setMyImageUrl(userInfo.getAvatar());
        etName.setText(userInfo.getName());
        etSex.setText(userInfo.getSex());
        etTel.setText(userInfo.getPhone());
      //  layoutInfo.setTag(userInfo.getAvatar().substring(userInfo.getAvatar().lastIndexOf("/")));
        StringBuilder builder = new StringBuilder(userInfo.getId());
        builder.replace(2, 14, "************");
        etIds.setText(builder.toString());
    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        layoutInfo = getView().findViewById(R.id.layout_info);
        ivPhoto = getView().findViewById(R.id.iv_photo);
        etName = getView().findViewById(R.id.et_name);
        etSex = getView().findViewById(R.id.et_sex);
        etTel = getView().findViewById(R.id.et_tel);
        etIds = getView().findViewById(R.id.et_ids);
        btSave = getView().findViewById(R.id.bt_save);
    }
}


