package com.example.fearless.user.iview;

/**
 * Created by Fearless on 16/3/10.
 */
public interface IAddCostActivityView {
    /**
     * 充值
     */
    public void addCost();
    /**
     * 设置充值成功标签
     */
    public void setSign(int success);
    /**
     * 设置删除成功标签
     */
    public void setDeleteSign(int success);
}
