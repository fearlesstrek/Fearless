package com.example.fearless.user.presentimpl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.user.ipresenter.IFourFragmentPresent;
import com.example.fearless.user.iview.IFourFragmentView;
import com.example.fearless.user.bean.CostBean;
import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Fearless on 16/3/10.
 */
public class FourFragmentImpl implements IFourFragmentPresent {

    private IFourFragmentView iFourFragmentView;
    private UserController controller;
    private Context mContext;
    private MyHandler myHandler = new MyHandler(this);
    private List<CostBean> cList;//存放会费列表

    public FourFragmentImpl(IFourFragmentView iFourFragmentView) {
        this.iFourFragmentView = iFourFragmentView;
        mContext = ((Fragment) iFourFragmentView).getActivity();
        controller = new UserController(myHandler, mContext);
    }
//  获得Cost详细列表
    @Override
    public void getCostDatilList(String accountName) {
        controller.findCostCostList(accountName, 1);
    }

    public static class MyHandler extends Handler {
        WeakReference<IFourFragmentPresent> present;

        public MyHandler(IFourFragmentPresent present) {
            this.present = new WeakReference<IFourFragmentPresent>(present);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (present != null && present.get() != null) {
                FourFragmentImpl p = (FourFragmentImpl) present.get();
                p.handleMessage(msg);
            }

        }
    }

    private void handleMessage(Message msg) {
        if (msg.what == UserBusiness.FIND_COST_DATIL_SUCCESS) {
            cList = (List<CostBean>) msg.obj;
            if (!(cList.isEmpty())) {
                if (Double.parseDouble(cList.get(0).getCostTotal()) < 10) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage("您的余额不足,请及时充值")
                            .setPositiveButton("确定", null)
                            .show();
                }
                iFourFragmentView.setTotalCost(cList);
            }else {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("您的余额不足,请及时充值")
                        .setPositiveButton("确定", null)
                        .show();

            }

        }
        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
    }
}
