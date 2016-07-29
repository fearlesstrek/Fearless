package com.example.fearless.user.iview;

import com.example.fearless.user.adapter.CostAdminDatilListAdapter;
import com.example.fearless.user.adapter.CostDatilListAdapter;
import com.example.fearless.widget.XListView;

/**
 * Created by Fearless on 16/3/30.
 */
public interface ICostAdminActivityView {
    //  给list设置adapter
    public void setData(CostAdminDatilListAdapter adapter);
    //  获得CostList组件
    public XListView getCostXlv();

}
