package com.example.fearless.user.ipresenter;

/**
 * Created by Fearless on 16/3/10.
 */
public interface IAddCostPresent {
    //    充值给管理员
    public void addCostAdmin(String accountNumber, String lastCostTotal, String costChange);

    //    充值
    public void addCost(String accountNumber, String lastCostTotal, String costChange);

    //    管理员删除
    public void deleteCost(String costId);
}
