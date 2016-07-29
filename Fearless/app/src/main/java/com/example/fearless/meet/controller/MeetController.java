package com.example.fearless.meet.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.fearless.fearless.icommon.DisplayCallback;
import com.example.fearless.meet.Business.MeetBusiness;
import com.loopj.android.http.RequestParams;

/**
 * Created by Fearless on 16/3/25.
 */
public class MeetController {
    private Context mContext;
    private Handler mHandler;
    private MeetBusiness business = MeetBusiness.getInstance();

    //构造函数
    public MeetController(Handler mHandler, Context mContext) {
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

    //查找会员参与的邀约活动
    public void findMemberAddInvite(String memberName) {
        RequestParams params = new RequestParams();
        params.put("memberName", memberName);
        business.findMemberAddInvite(mContext, params, callback);
    }
    //会员退出邀约活动
    public void memberAddToInvite(String memberName) {
        RequestParams params = new RequestParams();
        params.put("memberName", memberName);
        business.memberAddToInvite(mContext, params, callback);
    }
    //会员扣费
    public void costInviteMomey(String inviteId,String costChange) {
        RequestParams params = new RequestParams();
        params.put("inviteId", inviteId);
        params.put("costChange", costChange);
        business.costInviteMomey(mContext, params, callback);
    }
    //结束邀约活动
    public void endInvite(String inviteId) {
        RequestParams params = new RequestParams();
        params.put("inviteId", inviteId);
        business.endInvite(mContext, params, callback);
    }
}
