package com.example.fearless.invite.presentImpl;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.fearless.invite.adapter.InviteListAdapter;
import com.example.fearless.invite.bean.InviteBean;
import com.example.fearless.invite.business.InviteBusiness;
import com.example.fearless.invite.controller.InviteController;
import com.example.fearless.invite.ipresenter.IThirdFragmentPresenter;
import com.example.fearless.invite.iview.IThirdFragmentView;
import com.example.fearless.user.business.UserBusiness;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

/**
 * Created by Fearless on 16/3/13.
 */
public class ThirdFragmentImpl implements IThirdFragmentPresenter {
    private Context mContext;
    private IThirdFragmentView iThirdFragmentView;
    private MyHandler myHandler = new MyHandler(this);
    private InviteController controller;
    private ProgressDialog progressDialog;
    private InviteListAdapter adapter;
    private int page = 1;
    private List<InviteBean> inviteList;

    public ThirdFragmentImpl(IThirdFragmentView iThirdFragmentView) {
        this.iThirdFragmentView = iThirdFragmentView;
        mContext = ((Fragment) iThirdFragmentView).getActivity();
        controller = new InviteController(myHandler, mContext);
        adapter = new InviteListAdapter(mContext);
    }

    //获取
    @Override
    public void getInviteList() {
        controller.findInviteList();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    //刷新
    @Override
    public void onRefalsh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                iThirdFragmentView.getInviteListXlv().stopRefresh();
                iThirdFragmentView.getInviteListXlv().setRefreshTime(new Date());
                controller.findInviteList();
            }
        }, 500);
    }

    //加载
    @Override
    public void onLoadmore() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1;
                iThirdFragmentView.getInviteListXlv().stopLoadMore();
                controller.findInviteList();
            }
        }, 500);

    }
    //handler获取数据
    public static class MyHandler extends Handler {
        WeakReference<IThirdFragmentPresenter> presenter;
        public MyHandler(IThirdFragmentPresenter presenter) {
            this.presenter = new WeakReference<IThirdFragmentPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenter != null && presenter.get() != null) {
                ThirdFragmentImpl p = (ThirdFragmentImpl) presenter.get();
                p.handleMassage(msg);
            }
        }
    }
    public void handleMassage(Message msg) {
        if (msg.what == InviteBusiness.STATE_GET_INVITE_LIST_SUCCESS) {
            inviteList = (List<InviteBean>) msg.obj;
            if (inviteList.size() < 10) {
                iThirdFragmentView.getInviteListXlv().setPullLoadEnable(false);
            } else {
                iThirdFragmentView.getInviteListXlv().setPullLoadEnable(true);
            }
            if (page == 1) {
                adapter.setmList(inviteList);
                iThirdFragmentView.setInviteList(adapter);
                iThirdFragmentView.getInviteListXlv().stopRefresh();
            } else if (page > 1) {
                adapter.setmList(inviteList);
                adapter.notifyDataSetChanged();
                iThirdFragmentView.getInviteListXlv().stopLoadMore();

            }

        }
        if (msg.what == InviteBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
