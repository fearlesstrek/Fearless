package com.example.fearless.user.presentimpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.user.adapter.CostDatilListAdapter;
import com.example.fearless.user.bean.CostBean;
import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.ICostDatilPresent;
import com.example.fearless.user.iview.ICostActivityView;
import com.example.fearless.user.ui.CostActivity;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

/**
 * Created by Fearless on 16/3/7.
 */
public class CostDatilPresentImpl implements ICostDatilPresent {

    //代码写到这，
    private ICostActivityView iCostActivityView;
    private UserController controller;
    private CostDatilListAdapter costAdapter;//adapter
    private Context mContext;

    private MyHandler myHandler = new MyHandler(this);
    private List<CostBean> cList;//存放会费列表
    private ProgressDialog progressDialog;
    private int page = 1;
    private String accountName;
//构造函数


    public CostDatilPresentImpl(ICostActivityView iCostActivityView) {
        this.iCostActivityView = iCostActivityView;
        this.mContext = (CostActivity) iCostActivityView;
        this.controller = new UserController(myHandler, (CostActivity) iCostActivityView);
        this.costAdapter = new CostDatilListAdapter(mContext);
    }

    //获取costList
    @Override
    public void getCostDatilList(String accountName) {
        this.accountName = accountName;
        controller.findCostList(accountName, page);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    //刷新
    @Override
    public void refresh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                iCostActivityView.getCostXlv().stopRefresh();
                iCostActivityView.getCostXlv().setRefreshTime(new Date());
                controller.findCostList(accountName, page);
            }
        }, 500);
    }

    //加载更多
    @Override
    public void load() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1;
                iCostActivityView.getCostXlv().stopLoadMore();
                controller.findCostList(accountName, page);
            }
        }, 500);
    }
//创建handler类
    public static class MyHandler extends Handler {
        WeakReference<ICostDatilPresent> present;

        public MyHandler(ICostDatilPresent present) {
            this.present = new WeakReference<ICostDatilPresent>(present);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (present != null && present.get() != null) {
                CostDatilPresentImpl p = (CostDatilPresentImpl) present.get();
                p.handleMessage(msg);
            }

        }
    }
    //创建handler用的handler函数
    private void handleMessage(Message msg) {
        if (msg.what == UserBusiness.FIND_COST_DATIL_SUCCESS) {
            cList = (List<CostBean>) msg.obj;
            //如果List长度小于10，不允许加载
            if (cList.size() <= 10) {
                iCostActivityView.getCostXlv().setPullLoadEnable(false);
            } else {
                iCostActivityView.getCostXlv().setPullLoadEnable(true);
            }
            if (page == 1) {
                costAdapter.setmList(cList);
                iCostActivityView.setData(costAdapter);
                iCostActivityView.getCostXlv().stopRefresh();
            } else if (page > 1) {
                costAdapter.addList(cList);
                costAdapter.notifyDataSetChanged();
                iCostActivityView.getCostXlv().stopLoadMore();

            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (msg.what == UserBusiness.FIND_COST_DATIL_ERROR) {

        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
