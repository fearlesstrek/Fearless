package com.example.fearless.user.presentimpl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.IAddCostPresent;
import com.example.fearless.user.iview.IAddCostActivityView;
import com.example.fearless.user.iview.ICostAdminActivityView;
import com.example.fearless.user.ui.AddCostActivity;
import com.example.fearless.user.ui.CostAdminActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Fearless on 16/3/10.
 */
public class AddCostPersentImpl implements IAddCostPresent{
    private UserController controller;
    private Context mContext;
    private MyHandler myHandler = new MyHandler(this);
    private IAddCostActivityView iAddCostActivityView;

    public AddCostPersentImpl(IAddCostActivityView iAddCostActivityView) {
        this.iAddCostActivityView = iAddCostActivityView;
        mContext = ((Context) iAddCostActivityView);
        controller = new UserController(myHandler, mContext);
    }

//  充值给管理员
    @Override
    public void addCostAdmin(String accountNumber, String lastCostTotal, String costChange) {
        controller.findAddCostAdmin(accountNumber,lastCostTotal,costChange);
    }
    //  管理员给充值
    @Override
    public void addCost(String accountNumber, String lastCostTotal, String costChange) {
        controller.findAddCost(accountNumber, lastCostTotal, costChange);
    }

    @Override
    public void deleteCost(String costId) {
        controller.findDeleteCostAdmin(costId);
    }

    private class MyHandler extends Handler{
        WeakReference<IAddCostPresent> present;
        public MyHandler(IAddCostPresent present) {
            this.present = new WeakReference<IAddCostPresent>(present);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (present != null && present.get() != null) {
                AddCostPersentImpl addPresent = (AddCostPersentImpl) present.get();
                addPresent.handlerMessage(msg);
            }
        }
    }
    //写一个方法获取信息
    private void handlerMessage(Message msg) {
        if (msg.what == UserBusiness.FIND_ADD_COST_ADMIN_SUCCESS) {
            //充值给管理员成功
            iAddCostActivityView.setSign((Integer) msg.obj);
        }
        if (msg.what == UserBusiness.FIND_ADD_COST_SUCCESS) {
            //充值给用户成功
            iAddCostActivityView.setSign((Integer) msg.obj);
        }
        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (msg.what == UserBusiness.FIND_DELETE_COST_ADMIN_SUCCESS) {
            //删除成功
            iAddCostActivityView.setDeleteSign((Integer) msg.obj);
        }
    }
}
