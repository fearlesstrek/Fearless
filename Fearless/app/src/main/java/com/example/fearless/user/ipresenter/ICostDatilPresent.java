package com.example.fearless.user.ipresenter;

/**
 * Created by Fearless on 16/3/7.
 */
public interface ICostDatilPresent {
    /**
     * 获取会费列表
     */
    public void getCostDatilList(String accountName);

    /**
     * 下拉刷新
     */
    public void refresh();

    /**
     * 上拉加载
     */
    public void load();

}
