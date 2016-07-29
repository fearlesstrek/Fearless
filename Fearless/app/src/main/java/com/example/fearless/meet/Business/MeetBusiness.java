package com.example.fearless.meet.Business;

import android.content.Context;
import android.os.HandlerThread;

import com.example.fearless.common.Constant;
import com.example.fearless.common.utils.CommonUtils;
import com.example.fearless.common.utils.HttpUtils;
import com.example.fearless.fearless.R;
import com.example.fearless.fearless.icommon.DisplayCallback;
import com.example.fearless.invite.bean.InviteBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Fearless on 16/3/25.
 */
public class MeetBusiness {
    /**
     * 统一获取失败
     */
    public static final int STATE_GET_failed = 0;

    /**
     * 获取会员参与的邀约活动状态
     */
    public static  final int STATE_GET_MEMBER_INVITE_SUCCESS = 1;
    public static  final int STATE_GET_MEMBER_INVITE_ERROR = 2;

    /**
     * 获取会员退出邀约状态
     */
    public static  final int STATE_REMOVE_MEMBER_INVITE_SUCCESS = 101;
    public static  final int STATE_REMOVE_MEMBER_INVITE_ERROR = 102;

    /**
     * 获取会员交费状态
     */
    public static  final int STATE_COST_INVITE_MOMEY_SUCCESS = 201;
    public static  final int STATE_COST_INVITE_MOMEY_ERROE = 202;
    /**
     * 获取结束邀约活动状态
     */
    public static  final int STATE_END_INVITE_SUCCESS = 301;
    public static  final int STATE_END_INVITE_ERROR = 302;
    private static HandlerThread responseHandlerThread;
    private static class SingleInstaneceHolder {
        private static final MeetBusiness INSTANCE = new MeetBusiness();
    }
    public static MeetBusiness getInstance() {
        return SingleInstaneceHolder.INSTANCE;
    }
    private MeetBusiness() {
        responseHandlerThread = new HandlerThread("response11_handler_thread");
        responseHandlerThread.start();
    }

    /**
     * 查找会员参与的邀约活动
     */
    public void findMemberAddInvite(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_member_invite_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");
                InviteBean invite = null;
                //判断获取是否正确
                if (flag.equals("true")) {
                    invite = new InviteBean(response.optJSONObject("inviteQueryDto"));
                    if (invite != null) {
                        callback.displayResult(STATE_GET_MEMBER_INVITE_SUCCESS, invite);
                        Constant.cachedTimeForInvite(mContext, invite.getInviteTime());
                    }
                } else {
                    callback.displayResult(STATE_GET_MEMBER_INVITE_ERROR, tip);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
    /**
     * 用户退出邀约
     */
    public void memberAddToInvite(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.remove_member_to_invite_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //退出邀约成功
                    callback.displayResult(STATE_REMOVE_MEMBER_INVITE_SUCCESS,flag);
                } else {
                    // 退出邀约失败
                    callback.displayResult(STATE_REMOVE_MEMBER_INVITE_ERROR,tip);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
    /**
     * 用户扣费
     */
    public void costInviteMomey(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.cost_member_invite_money_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //扣费成功
                    callback.displayResult(STATE_COST_INVITE_MOMEY_SUCCESS,flag);
                    Constant.cachedCostOrNot(mContext,"cost");
                } else {
                    // 扣费失败
                    callback.displayResult(STATE_COST_INVITE_MOMEY_ERROE,tip);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
    /**
     * 发起人结束邀约活动
     */
    public void endInvite(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.end_invite_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //结束成功
                    callback.displayResult(STATE_END_INVITE_SUCCESS,flag);
                    Constant.cachedCostOrNot(mContext,null);
                } else {
                    // 结束失败
                    callback.displayResult(STATE_END_INVITE_ERROR,tip);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
}
