package com.example.fearless.user.iview;

import com.example.fearless.user.bean.CostBean;

import java.util.List;

/**
 * Created by Fearless on 16/3/10.
 */
public interface IFourFragmentView {
    /**
     * CostList列表设置
     * @param costBeanList
     */
    public void setTotalCost(List<CostBean> costBeanList);
}
