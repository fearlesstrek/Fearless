package com.example.fearless.user.presentimpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.fearless.user.adapter.CostAdminDatilListAdapter;
import com.example.fearless.user.bean.CostAdminBean;
import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.ICostAdminPresent;
import com.example.fearless.user.iview.ICostAdminActivityView;
import com.example.fearless.user.ui.CostAdminActivity;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

/**
 * Created by Fearless on 16/3/30.
 */
public class CostAdminPresentImpl implements ICostAdminPresent{
    //代码写到这，
    private ICostAdminActivityView iCostAdminActivityView;
    private UserController controller;
    private CostAdminDatilListAdapter costAdminAdapter;//adapter
    private Context mContext;

    private MyHandler myHandler = new MyHandler(this);
    private List<CostAdminBean> cList;//存放会费列表
    private ProgressDialog progressDialog;

    public CostAdminPresentImpl(ICostAdminActivityView iCostAdminActivityView) {
        this.iCostAdminActivityView = iCostAdminActivityView;
        mContext = (CostAdminActivity)iCostAdminActivityView;
        controller = new UserController(myHandler, mContext);
        costAdminAdapter = new CostAdminDatilListAdapter(mContext);
    }

    @Override
    public void getCostAdminDatilList() {
        controller.findCostAdminList();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    @Override
    public void refresh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iCostAdminActivityView.getCostXlv().stopRefresh();
                iCostAdminActivityView.getCostXlv().setRefreshTime(new Date());
                controller.findCostAdminList();
            }
        }, 500);
    }

    @Override
    public void load() {

    }
    //创建handler类
    public static class MyHandler extends Handler {
        WeakReference<ICostAdminPresent> present;

        public MyHandler(ICostAdminPresent present) {
            this.present = new WeakReference<ICostAdminPresent>(present);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (present != null && present.get() != null) {
                CostAdminPresentImpl p = (CostAdminPresentImpl) present.get();
                p.handleMessage(msg);
            }

        }
    }
    //创建handler用的handler函数
    private void handleMessage(Message msg) {
        //找到列表
        if (msg.what == UserBusiness.FIND_COST_ADMIN_DATIL_SUCCESS) {
            cList = (List<CostAdminBean>) msg.obj;

            if (cList.size() <= 10) {
                iCostAdminActivityView.getCostXlv().setPullLoadEnable(false);
            } else {
                iCostAdminActivityView.getCostXlv().setPullLoadEnable(true);
            }
            if (cList != null) {
                costAdminAdapter.setmList(cList);
                iCostAdminActivityView.setData(costAdminAdapter);
            }
        }
        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (msg.what == UserBusiness.FIND_COST_ADMIN_DATIL_ERROR) {

        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
