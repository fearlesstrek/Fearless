package com.example.fearless.user.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fearless.fearless.R;
import com.example.fearless.user.adapter.CostDatilListAdapter;
import com.example.fearless.user.ipresenter.ICostDatilPresent;
import com.example.fearless.user.iview.ICostActivityView;
import com.example.fearless.user.presentimpl.CostDatilPresentImpl;
import com.example.fearless.widget.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CostActivity extends Activity implements ICostActivityView,XListView.IXListViewListener{


    @InjectView(R.id.cost_lv)XListView mXlvCost;
    private ICostDatilPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        present = new CostDatilPresentImpl(this);
        present.getCostDatilList(getIntent().getStringExtra("userName"));
        ButterKnife.inject(this);
        mXlvCost.setXListViewListener(this);
    }
//    返回按钮
    @OnClick({R.id.btn_back})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }

    //  给list设置adapter
    @Override
    public void setData(CostDatilListAdapter adapter) {
        mXlvCost.setAdapter(adapter);
    }
    //  返回CostList组件
    @Override
    public XListView getCostXlv() {
        return mXlvCost;
    }
//    刷新
    @Override
    public void onRefresh() {
        present.refresh();
    }
//    加载更多
    @Override
    public void onLoadMore() {
        present.load();
    }
}
