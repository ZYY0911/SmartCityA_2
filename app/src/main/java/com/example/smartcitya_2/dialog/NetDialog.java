package com.example.smartcitya_2.dialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:22
 */
public class NetDialog extends DialogFragment {
    private EditText etIp;
    private EditText etPort;
    private Button btSure;
    private Button btExit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.net_dialog, container, false);
    }

    SharedPreferences preferences;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        preferences = AppClient.sharedPreferences;
        etIp.setText(preferences.getString(AppClient.IP, "192.168.43.149"));
        etPort.setText(preferences.getString(AppClient.PORT, "8080"));
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString(AppClient.IP, etIp.getText().toString()).apply();
                preferences.edit().putString(AppClient.PORT, etPort.getText().toString()).apply();
                Util.showToast("设置成功", getContext());
                getDialog().dismiss();
            }
        });
    }

    private void initView() {
        etIp = getView().findViewById(R.id.et_ip);
        etPort = getView().findViewById(R.id.et_port);
        btSure = getView().findViewById(R.id.bt_sure);
        btExit = getView().findViewById(R.id.bt_exit);
    }
}
