package com.example.fearless.invite.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.fearless.fearless.icommon.DisplayCallback;
import com.example.fearless.invite.business.InviteBusiness;
import com.loopj.android.http.RequestParams;

/**
 * Created by Fearless on 16/3/13.
 */
public class InviteController {
    private Context mContext;
    private Handler mHandler;
    private InviteBusiness business = InviteBusiness.getInstance();

    //构造函数
    public InviteController(Handler mHandler, Context mContext) {
        this.mHandler = mHandler;
        this.mContext = mContext;
    }

    //实现callback
    private DisplayCallback callback = new DisplayCallback() {
        @Override
        public void displayResult(int key, Object object) {
            Message msg = mHandler.obtainMessage();
            msg.what = key;
            msg.obj = object;
            mHandler.sendMessage(msg);
        }
    };

    //  加载邀约列表
    public void findInviteList() {
        RequestParams params = new RequestParams();
        business.getInviteList(mContext, params, callback);

    }

    //  创建邀约活动
    public void creatInvite(String inviteMember, String inviteName, String inviteTime, String invitePlace
            , String inviteLimitNum, String inviteTotalCost, String invitePhone, String inviteRemarks) {
        RequestParams params = new RequestParams();
        params.put("inviteMember", inviteMember);
        params.put("inviteName", inviteName);
        params.put("inviteTime", inviteTime);
        params.put("invitePlace", invitePlace);
        params.put("inviteLimitNum", inviteLimitNum);
        params.put("inviteTotalCost", inviteTotalCost);
        params.put("invitePhone", invitePhone);
        params.put("inviteRemarks", inviteRemarks);
        business.creatInvite(mContext, params, callback);
    }

    //  获取已经参与邀约活动的会员列表
    public void getInviteMemberList(String inviteId) {
        RequestParams params = new RequestParams();
        params.put("inviteId", inviteId);
        business.getInviteMemberList(mContext, params, callback);
    }

    //  用户加入邀约活动
    public void memberAddToInvite(String memberName, String inviteId) {
        RequestParams params = new RequestParams();
        params.put("inviteId", inviteId);
        params.put("memberName", memberName);
        business.memberAddToInvite(mContext, params, callback);
    }
}
