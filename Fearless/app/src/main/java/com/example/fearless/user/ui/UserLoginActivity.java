package com.example.fearless.user.ui;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.fearless.FearlessActivity;
import com.example.fearless.fearless.R;
import com.example.fearless.user.adapter.FragmentTabPager;
import com.example.fearless.user.bean.UserBean;
import com.example.fearless.user.bean.UserEvent;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.graphics.Color.*;

public class UserLoginActivity extends FragmentActivity implements ViewPager.OnPageChangeListener
        , LoginFragment.OnFragmentInteractionListener, SubmitFragment.OnFragmentInteractionListener {

    private FragmentTabPager pageAdapter;
    private List<Fragment> fragments;
    private List<String> titleList;
    private Handler handler;

//    @InjectView(R.id.tab)
//    PagerTabStrip tab;
    @InjectView(R.id.ac_mycourse_page)
    ViewPager pages;
    @InjectView(R.id.user_login)
    TextView user_login;
    @InjectView(R.id.user_submit)
    TextView user_sunmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.inject(this);
//        tab.setBackgroundColor(getResources().getColor(R.color.gray_border));
//        tab.setTabIndicatorColor(BLUE);
////        tab.setDrawFullUnderline(false);
//        tab.setTextColor(R.color.classify_checked);

        //初始化fragments
        fragments = new ArrayList<Fragment>();
        titleList = new ArrayList<String>();

        //添加Fragment
        fragments.add(new LoginFragment());
        fragments.add(new SubmitFragment());
        //添加titleList
        titleList.add(user_login.getText().toString());
        titleList.add(user_sunmit.getText().toString());

        //实例化pageAdapter
        pageAdapter = new FragmentTabPager(getSupportFragmentManager(), fragments, titleList);

        //setadapter
        pages.setAdapter(pageAdapter);
        pages.setOnPageChangeListener(this);
        String token = Constant.getCachedToken(this);
        //如果token为空，则说明还没有登录或者已经退出登录
        if (token != null) {
            if (token.equals("admin")) {
                startActivity(new Intent(this, CostAdminActivity.class));
            } else {
                startActivity(new Intent(this, FearlessActivity.class));
            }

            finish();
        }

    }

    //监听事件
    @OnClick({R.id.user_login,R.id.user_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_login:
                //设置点击后改变viewPager页面
                pages.setCurrentItem(0);
                break;
            case R.id.user_submit:
                //设置点击后改变viewPager页面
                pages.setCurrentItem(1);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //改变导航菜单颜色
        Log.i("wxy", "onPageSelected " + position);
        if (position == 0) {
            user_login.setTextColor(this.getResources().getColor(R.color.classify_checked));
            user_sunmit.setTextColor(this.getResources().getColor(R.color.black));
        }
        if (position == 1) {
            user_sunmit.setTextColor(this.getResources().getColor(R.color.classify_checked));
            user_login.setTextColor(this.getResources().getColor(R.color.black));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onFragmentInteraction(UserBean user) {
//        Toast.makeText(this, uri,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("userName", user.getUserName());
        intent.putExtra("accountNumber", user.getAccountNumber());
        intent.putExtras(intent);
        setResult(1, intent);
        if (user.getAccountNumber().equals("admin")) {
            startActivity(new Intent(this, CostAdminActivity.class));
        } else {
            startActivity(new Intent(this, FearlessActivity.class));
        }
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
