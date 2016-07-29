package com.example.fearless.invite.presentImpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.invite.business.InviteBusiness;
import com.example.fearless.invite.controller.InviteController;
import com.example.fearless.invite.ipresenter.IInviteDetailActivityPresenter;
import com.example.fearless.invite.iview.IInviteDetailActivityView;
import com.example.fearless.invite.ui.InviteDetailActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Fearless on 16/3/25.
 */
public class InviteDetailActivityImpl implements IInviteDetailActivityPresenter{

    private InviteController controller;
    private IInviteDetailActivityView iInviteDetailActivityView;
    private MyHandler myhandler = new MyHandler(this);
    private Context mContext;
    private ProgressDialog progressDialog;
    public InviteDetailActivityImpl(IInviteDetailActivityView iInviteDetailActivityView) {
        this.iInviteDetailActivityView = iInviteDetailActivityView;
        mContext = (InviteDetailActivity) iInviteDetailActivityView;
        controller = new InviteController(myhandler, mContext);
    }

    @Override
    public void memberAddToInvite(String memberName, String inviteId) {
        controller.memberAddToInvite(memberName, inviteId);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }


    private class MyHandler extends Handler{
        WeakReference<IInviteDetailActivityPresenter> presenterWeak;
        public MyHandler(IInviteDetailActivityPresenter presenter) {
            this.presenterWeak = new WeakReference<IInviteDetailActivityPresenter>(presenter);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenterWeak != null && presenterWeak.get() != null) {
                InviteDetailActivityImpl p = (InviteDetailActivityImpl) presenterWeak.get();
                p.handlerMessage(msg);
            }
        }
    }

    private void handlerMessage(Message msg) {
        String flag = (String) msg.obj;
        if (msg.what == InviteBusiness.STATE_MEMBER_ADD_TO_INVITE_SUCCESS) {
                iInviteDetailActivityView.setAddToInviteSign(flag);
        }
        if (msg.what == InviteBusiness.STATE_MEMBER_ADD_TO_INVITE_ERROR) {
            iInviteDetailActivityView.setAddToInviteSign(flag);
        }
        if (msg.what == InviteBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
