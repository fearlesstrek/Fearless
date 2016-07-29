package com.example.fearless.invite.presentImpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.invite.business.InviteBusiness;
import com.example.fearless.invite.controller.InviteController;
import com.example.fearless.invite.ipresenter.IAddInviteActivityPresenter;
import com.example.fearless.invite.iview.IAddInviteActivityView;
import com.example.fearless.invite.ui.AddInviteActivity;
import com.example.fearless.user.iview.IAddCostActivityView;
import com.example.fearless.user.ui.AddCostActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Fearless on 16/3/24.
 */
public class AddInviteActivityImpl implements IAddInviteActivityPresenter {

    private InviteController controller;
    private IAddInviteActivityView iAddInviteActivityView;
    private Myhandler myhandler = new Myhandler(this);
    private Context mContext;
    //加载进度条
    private ProgressDialog dialog;
    public AddInviteActivityImpl(IAddInviteActivityView iAddInviteActivityView) {
        this.iAddInviteActivityView = iAddInviteActivityView;
        this.mContext = (AddInviteActivity) iAddInviteActivityView;
        controller = new InviteController(myhandler, mContext);
    }

    @Override
    public void creatInvite(String inviteMember, String inviteName, String inviteTime, String invitePlace
            , String inviteLimitNum, String inviteTotalCost, String invitePhone, String inviteRemarks) {
        controller.creatInvite(inviteMember, inviteName, inviteTime, invitePlace, inviteLimitNum
                , inviteTotalCost, invitePhone, inviteRemarks);
        if (dialog == null) {
            dialog = new ProgressDialog(mContext);
            dialog.show();
        }
    }

    private class Myhandler extends Handler {
        //创建弱相关
        WeakReference<IAddInviteActivityPresenter> presentWeak;

        //构造函数
        public Myhandler(IAddInviteActivityPresenter presenter) {
            this.presentWeak = new WeakReference<IAddInviteActivityPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presentWeak != null && presentWeak.get() != null) {
                AddInviteActivityImpl p = (AddInviteActivityImpl) presentWeak.get();
                p.handlerMessage(msg);
            }
        }
    }

    private void handlerMessage(Message msg) {
        if (msg.what == InviteBusiness.STATE_CREAT_INVITE_SUCCESS) {
            String flag = (String) msg.obj;
            iAddInviteActivityView.creatInviteSuccessOrNot(flag);
        }
        if (msg.what == InviteBusiness.STATE_CREAT_INVITE_ERROR) {
            String flag = (String) msg.obj;
            iAddInviteActivityView.creatInviteSuccessOrNot(flag);
        }
        if (msg.what == InviteBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        //dialog消失
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
