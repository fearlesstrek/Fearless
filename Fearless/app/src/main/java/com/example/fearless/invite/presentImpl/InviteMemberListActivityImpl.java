package com.example.fearless.invite.presentImpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.invite.adapter.InviteMemberListAdapter;
import com.example.fearless.invite.business.InviteBusiness;
import com.example.fearless.invite.controller.InviteController;
import com.example.fearless.invite.ipresenter.IInviteMemberListActivityPresenter;
import com.example.fearless.invite.iview.IInviteMemberListActivityView;
import com.example.fearless.invite.ui.InviteMemberListActivity;
import com.example.fearless.user.bean.UserBean;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

/**
 * Created by Fearless on 16/3/24.
 */
public class InviteMemberListActivityImpl implements IInviteMemberListActivityPresenter {
    private InviteController controller;
    private IInviteMemberListActivityView iInviteMemberListActivityView;
    private Context mContext;
    private MyHandler myHandler = new MyHandler(this);
    private List<UserBean> listInviteMember;
    private InviteMemberListAdapter adapter;
    private ProgressDialog progressDialog;
    public InviteMemberListActivityImpl(IInviteMemberListActivityView iInviteMemberListActivityView) {
        this.iInviteMemberListActivityView = iInviteMemberListActivityView;
        this.mContext = (InviteMemberListActivity) iInviteMemberListActivityView;
        controller = new InviteController(myHandler, mContext);
        adapter = new InviteMemberListAdapter(mContext);
    }

    @Override
    public void getInviteMemberList(String inviteId) {
        controller.getInviteMemberList(inviteId);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    @Override
    public void onRefalsh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iInviteMemberListActivityView.getInviteListXlv().stopRefresh();
                iInviteMemberListActivityView.getInviteListXlv().setRefreshTime(new Date());
                controller.findInviteList();
            }
        }, 500);
    }

    @Override
    public void onLoadmore() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iInviteMemberListActivityView.getInviteListXlv().stopLoadMore();
                controller.findInviteList();
            }
        }, 500);
    }

    private class MyHandler extends Handler {
        WeakReference<IInviteMemberListActivityPresenter> presentWeak;
        public MyHandler(IInviteMemberListActivityPresenter presenter) {
            this.presentWeak = new WeakReference<IInviteMemberListActivityPresenter>(presenter);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presentWeak != null && presentWeak.get() != null) {
                InviteMemberListActivityImpl p = (InviteMemberListActivityImpl) presentWeak.get();
                p.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        if (msg.what == InviteBusiness.STATE_GET_INVITE_MEMBER_SUCCESS) {
            listInviteMember = (List<UserBean>) msg.obj;
            if (!(listInviteMember.isEmpty())) {
                iInviteMemberListActivityView.getInviteListXlv().setPullLoadEnable(false);
            } else {
                iInviteMemberListActivityView.getInviteListXlv().setPullLoadEnable(true);
            }

            adapter.setmList(listInviteMember);
            iInviteMemberListActivityView.setInviteMemberList(adapter);
            iInviteMemberListActivityView.setAddedInviteMemberSize(listInviteMember.size());
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
        if (msg.what == InviteBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
    }
}
