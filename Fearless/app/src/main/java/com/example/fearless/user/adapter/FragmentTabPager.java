package com.example.fearless.user.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/24 0024.
 */
public class FragmentTabPager extends FragmentStatePagerAdapter {
    private List<Fragment>fragList;
    private List<String>titleList;
    public FragmentTabPager(FragmentManager fm,List<Fragment> fragList,List<String>titleList) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.fragList=fragList;
        this.titleList=titleList;
    }
    //获得第arg0项
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragList.get(arg0);
    }
    //返回所有视图的数量
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
    //删除页面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        super.destroyItem(container, position, object);
    }
    //实例化页面
    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        return super.instantiateItem(arg0, arg1);
    }
}
