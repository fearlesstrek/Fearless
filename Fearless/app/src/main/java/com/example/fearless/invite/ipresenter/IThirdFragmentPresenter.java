package com.example.fearless.invite.ipresenter;

import android.os.Message;

/**
 * Created by Fearless on 16/3/13.
 */
public interface IThirdFragmentPresenter {
    /**
     * 获得邀约列表
     */
    public void getInviteList();
    /**
     * 刷新
     */
    public void onRefalsh();
    /**
     * 加载
     */
    public void onLoadmore();



}
