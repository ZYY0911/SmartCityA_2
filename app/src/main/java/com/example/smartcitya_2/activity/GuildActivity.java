package com.example.smartcitya_2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.smartcitya_2.AppClient;
import com.example.smartcitya_2.R;
import com.example.smartcitya_2.fragment_guild.GuildFragment;
import com.example.smartcitya_2.fragment_guild.GuildFragment2;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/18 at 9:13
 */
public class GuildActivity extends AppCompatActivity {
    List<Fragment> fragments;
    int image[] = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private ViewPager viewPager;
    private SharedPreferences preferences;
    private LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = AppClient.sharedPreferences;
        if (preferences.getBoolean(AppClient.IsFirst, true)) {
            preferences.edit().putBoolean(AppClient.IsFirst, false).apply();
        } else {
            startActivity(new Intent(this, AppHomeActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.guld_layout);
        initView();
        fragments = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            if (i == image.length - 1) {
                fragments.add(new GuildFragment2(image[i]));
            } else {
                fragments.add(new GuildFragment(image[i]));
            }
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.drawable.selset_image);
            } else {
                imageView.setImageResource(R.drawable.no_selset_image);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.setMargins(20, 0, 20, 0);
            imageView.setLayoutParams(layoutParams);
            layout.addView(imageView);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    ImageView imageView = (ImageView) layout.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(R.drawable.selset_image);
                    } else {
                        imageView.setImageResource(R.drawable.no_selset_image);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        layout = findViewById(R.id.layout);
    }
}
