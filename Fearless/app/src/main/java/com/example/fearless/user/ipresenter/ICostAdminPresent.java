package com.example.fearless.user.ipresenter;

/**
 * Created by Fearless on 16/3/30.
 */
public interface ICostAdminPresent {
    /**
     * 获取会员充值列表
     */
    public void getCostAdminDatilList();

    /**
     * 下拉刷新
     */
    public void refresh();

    /**
     * 上拉加载
     */
    public void load();

}
