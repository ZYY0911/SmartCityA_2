package com.example.smartcitya_2.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.net.VolleyLo;
import com.example.smartcitya_2.net.VolleyTo;
import com.example.smartcitya_2.util.Util;

import org.json.JSONObject;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 18:27
 */
public class YjfkFragment extends
        Fragment {
    private ImageView itemImage;
    private TextView title;
    private TextView title1;
    private EditText etMsg;
    private TextView tvNum;
    private Button btSave;

    public YjfkFragment() {
    }

    private AppHomeActivity appHomeActivity;

    public YjfkFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    public static YjfkFragment newInstance(AppHomeActivity appHomeActivity) {
        return new YjfkFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yjfk_layout, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        title.setText("意见反馈");
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appHomeActivity.switchFragment(appHomeActivity.map.get("个人中心"));
            }
        });
        etMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    String msg = s.toString();
                    if (msg.length() >= 151) {
                        Util.showToast("只能输入150字", getContext());
                        etMsg.setText(msg.substring(0, 150));
                        etMsg.setSelection(150);
                        return;
                    }
                    tvNum.setText(msg.length() + "/150字");
                }
            }
        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("publicOpinion")
                        .setJsonObject("userid", AppClient.getUserId(AppClient.username))
                        .setJsonObject("content", etMsg.getText().toString())
                        .setJsonObject("time", Util.simpleDate("yyyy.MM.dd HH:mm:ss", new Date()))
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Util.showDialog("保存成功", getActivity());
                                    etMsg.setText("");
                                    tvNum.setText("0/150字");
                                } else {
                                    Util.showDialog("保存失败", getActivity());
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Util.showDialog("保存失败", getActivity());

                            }
                        }).start();
            }
        });
    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        title = getView().findViewById(R.id.title);
        title1 = getView().findViewById(R.id.title1);
        etMsg = getView().findViewById(R.id.et_msg);
        tvNum = getView().findViewById(R.id.tv_num);
        btSave = getView().findViewById(R.id.bt_save);
    }
}
