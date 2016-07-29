package com.example.fearless.user.iview;

import com.example.fearless.user.adapter.CostDatilListAdapter;
import com.example.fearless.widget.XListView;

/**
 * Created by Fearless on 16/3/7.
 */
public interface ICostActivityView {

//  给list设置adapter
    public void setData(CostDatilListAdapter adapter);
//  获得CostList组件
    public XListView getCostXlv();
}
