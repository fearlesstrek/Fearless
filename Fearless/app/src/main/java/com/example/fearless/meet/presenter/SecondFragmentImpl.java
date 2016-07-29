package com.example.fearless.meet.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.fearless.invite.bean.InviteBean;
import com.example.fearless.invite.business.InviteBusiness;
import com.example.fearless.meet.Business.MeetBusiness;
import com.example.fearless.meet.controller.MeetController;
import com.example.fearless.meet.ipresenter.ISecondFragmentPresenter;
import com.example.fearless.meet.iview.ISecondFragmentView;

import java.lang.ref.WeakReference;

/**
 * Created by Fearless on 16/3/25.
 */
public class SecondFragmentImpl implements ISecondFragmentPresenter {

    private MeetController controller;
    private Context mContext;
    private MyHandler myhandler = new MyHandler(this);
    private ISecondFragmentView iSecondFragmentView;
    private ProgressDialog progressDialog;

    public SecondFragmentImpl(ISecondFragmentView iSecondFragmentView) {
        this.iSecondFragmentView = iSecondFragmentView;
        mContext = ((Fragment) iSecondFragmentView).getActivity();
        controller = new MeetController(myhandler, mContext);
    }

    /**
     * 请求数据
     */
    @Override
    public void initDate(String memberName) {
        controller.findMemberAddInvite(memberName);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    /**
     * 请求退出邀约
     */
    @Override
    public void removeToInvite(final String memberName) {
        new android.support.v7.app.AlertDialog.Builder(mContext).setTitle("提示").setMessage("确定要退出邀约活动吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 发起退出
                        controller.memberAddToInvite(memberName);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    /**
     * 请求扣费
     */
    @Override
    public void costInviteMoney(final String inviteId, final String costChange) {
        new android.support.v7.app.AlertDialog.Builder(mContext).setTitle("提示").setMessage("确定要现在交费吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 发起退出
                        controller.costInviteMomey(inviteId, costChange);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    @Override
    public void endInvite(final String inviteId) {
        new android.support.v7.app.AlertDialog.Builder(mContext).setTitle("提示").setMessage("活动未开始，确定要删除邀约活动吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 发起退出
                        controller.endInvite(inviteId);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, 2);
            progressDialog.show();
        }
    }

    private class MyHandler extends Handler {
        WeakReference<ISecondFragmentPresenter> presenterWeak;

        public MyHandler(ISecondFragmentPresenter presenter) {
            this.presenterWeak = new WeakReference<ISecondFragmentPresenter>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presenterWeak != null && presenterWeak.get() != null) {
                SecondFragmentImpl p = (SecondFragmentImpl) presenterWeak.get();
                p.handlerMessage(msg);
            }
        }
    }

    private void handlerMessage(Message msg) {
        //查询用户已经参与的布局
        if (msg.what == MeetBusiness.STATE_GET_MEMBER_INVITE_SUCCESS) {
            InviteBean inviteBean = (InviteBean) msg.obj;
            if (inviteBean != null) {
                //显示布局
                iSecondFragmentView.showInviteViewSub(inviteBean);
//                iSecondFragmentView.setInviteTime(inviteBean);
            }
        }
        //用户退出邀约活动
        if (msg.what == MeetBusiness.STATE_REMOVE_MEMBER_INVITE_SUCCESS) {
            String flag = (String) msg.obj;
            if (flag.equals("true")) {
                iSecondFragmentView.removeSuccess();
            }
        }
        //用户扣费
        if (msg.what == MeetBusiness.STATE_COST_INVITE_MOMEY_SUCCESS) {
            String flag = (String) msg.obj;
            if (flag.equals("true")) {
                iSecondFragmentView.costSuccess();
            }
        }
        //用户结束邀约活动
        if (msg.what == MeetBusiness.STATE_END_INVITE_SUCCESS) {
            String flag = (String) msg.obj;
            if (flag.equals("true")) {
                iSecondFragmentView.endSuccess();
            }
        }
        if (msg.what == MeetBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
