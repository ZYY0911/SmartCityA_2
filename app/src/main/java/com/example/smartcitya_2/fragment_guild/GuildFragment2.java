package com.example.smartcitya_2.fragment_guild;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.activity.AppHomeActivity;
import com.example.smartcitya_2.dialog.NetDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:14
 */
public class GuildFragment2 extends Fragment {
    private ImageView itemImage;
    private int image;
    private Button btSetting;
    private Button btMain;

    public GuildFragment2(int image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guild_framgnet2, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        itemImage.setImageResource(image);
        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetDialog dialog = new NetDialog();
                dialog.show(getChildFragmentManager(), "");
            }
        });

        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AppHomeActivity.class));


                getActivity().finish();
            }
        });
    }

    private void initView() {
        itemImage = getView().findViewById(R.id.item_image);
        btSetting = getView().findViewById(R.id.bt_setting);
        btMain = getView().findViewById(R.id.bt_main);
    }
}
