package com.example.smartcitya_2.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcitya_2.R;
import com.example.smartcitya_2.adapter.AllServiceAdapter;
import com.example.smartcitya_2.fragment.AllServiceFragment;
import com.example.smartcitya_2.fragment.Djzhfragment;
import com.example.smartcitya_2.fragment.DtcxFragment;
import com.example.smartcitya_2.fragment.HomeFragment;
import com.example.smartcitya_2.fragment.MotifPwd;
import com.example.smartcitya_2.fragment.MyCenterFragment;
import com.example.smartcitya_2.fragment.MyInfoFragment;
import com.example.smartcitya_2.fragment.NewFragment;
import com.example.smartcitya_2.fragment.OrderFragment;
import com.example.smartcitya_2.fragment.YjfkFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AppHomeActivity extends AppCompatActivity {

    private EditText etSearch;
    private BottomNavigationView bottomNav;

    public Map<String, Fragment> map;
    private LinearLayout layoutHead;
    private FrameLayout homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        map = new HashMap<>();
        map.put("1", HomeFragment.newInstance(this));
        switchFragment(map.get("1"));
        map.put("个人中心", MyCenterFragment.newInstance(this));
        map.put("个人信息", MyInfoFragment.newInstance(this));
        map.put("我的订单", OrderFragment.newInstance(this));
        map.put("修改密码", MotifPwd.newInstance(this));
        map.put("意见反馈", YjfkFragment.newInstance(this));
        map.put("地铁查询", DtcxFragment.newInstance(this));
        map.put("全部服务", AllServiceFragment.newInstance(this));

        map.put("新闻", NewFragment.newInstance(this));
        map.put("党建", Djzhfragment.newInstance(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        switchFragment(map.get("1"));
                        break;
                    case R.id.action_center:
                        switchFragment(map.get("个人中心"));
                        break;
                    case R.id.action_service:
                        switchFragment(map.get("全部服务"));
                        break;
                    case R.id.action_new:
                        switchFragment(map.get("新闻"));
                        break;
                    case R.id.action_fp:
                        switchFragment(map.get("党建"));
                        break;
                }
                return true;
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchActivity.newInstance(etSearch.getText().toString(), AppHomeActivity.this);
                    etSearch.setText("");
                }
                return false;
            }
        });
    }

    private Fragment current = new Fragment();

    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            if (current != null) {
                transaction.hide(current);
            }
            transaction.add(R.id.home_fragment, fragment, fragment.getClass().getName());
        } else {
            transaction.hide(current).show(fragment);
        }
        if (!fragment.getClass().getName().equals(map.get("1").getClass().getName())) {
            layoutHead.setVisibility(View.GONE);
        } else {
            layoutHead.setVisibility(View.VISIBLE);
        }
        current = fragment;
        transaction.commit();
    }


    public void backClick() {
        switchFragment(map.get("1"));
    }

    private void initView() {
        etSearch = findViewById(R.id.et_search);
        bottomNav = findViewById(R.id.bottom_nav);
        layoutHead = findViewById(R.id.layout_head);
        homeFragment = findViewById(R.id.home_fragment);
    }
}