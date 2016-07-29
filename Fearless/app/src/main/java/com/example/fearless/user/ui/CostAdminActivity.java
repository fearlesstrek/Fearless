package com.example.fearless.user.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.fearless.common.Constant;
import com.example.fearless.fearless.R;
import com.example.fearless.user.adapter.CostAdminDatilListAdapter;
import com.example.fearless.user.adapter.CostDatilListAdapter;
import com.example.fearless.user.ipresenter.ICostAdminPresent;
import com.example.fearless.user.iview.ICostAdminActivityView;
import com.example.fearless.user.presentimpl.CostAdminPresentImpl;
import com.example.fearless.widget.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CostAdminActivity extends Activity implements ICostAdminActivityView, XListView.IXListViewListener {


    @InjectView(R.id.cost_admin_lv)
    XListView mXlvCostAdmin;
    private ICostAdminPresent present;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cost_admin);
        ButterKnife.inject(this);
        mXlvCostAdmin.setXListViewListener(this);
        present = new CostAdminPresentImpl(this);
        present.getCostAdminDatilList();

    }

    //    退出登录
    @OnClick({R.id.btn_admin_to_exit})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_admin_to_exit:
                Constant.cachedToken(this, null);
                Constant.cachedCostTotal(this, null);
                Constant.cachedCostOrNot(this, null);
                Constant.cachedTimeForInvite(this, null);
                startActivity(new Intent(this, UserLoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void setData(CostAdminDatilListAdapter adapter) {
        mXlvCostAdmin.setAdapter(adapter);
    }

    @Override
    public XListView getCostXlv() {
        return mXlvCostAdmin;
    }


    @Override
    public void onRefresh() {
        present.refresh();
    }

    @Override
    public void onLoadMore() {
        present.load();
    }
}
