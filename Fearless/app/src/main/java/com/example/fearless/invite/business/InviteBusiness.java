package com.example.fearless.invite.business;

import android.content.Context;
import android.os.HandlerThread;
import android.widget.Toast;

import com.example.fearless.common.utils.CommonUtils;
import com.example.fearless.common.utils.HttpUtils;
import com.example.fearless.fearless.R;
import com.example.fearless.fearless.icommon.DisplayCallback;
import com.example.fearless.invite.bean.InviteBean;
import com.example.fearless.user.bean.UserBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fearless on 16/3/13.
 */
public class InviteBusiness {
    /**
     * 统一获取失败
     */
    public static final int STATE_GET_failed = 0;

    /**
     * 获取邀约列表状态
     */
    public static  final int STATE_GET_INVITE_LIST_SUCCESS = 1;
    public static  final int STATE_GET_INVITE_LIST_ERROR = 2;
    /**
     * 创建邀约活动状态
     */
    public static  final int STATE_CREAT_INVITE_SUCCESS = 101;
    public static  final int STATE_CREAT_INVITE_ERROR = 102;
    /**
     * 获取参与邀约活动的会员列表状态
     */
    public static  final int STATE_GET_INVITE_MEMBER_SUCCESS = 201;
    public static  final int STATE_GET_INVITE_MEMBER_ERROR = 202;
    /**
     * 用户加入邀约活动状态
     */
    public static  final int STATE_MEMBER_ADD_TO_INVITE_SUCCESS = 301;
    public static  final int STATE_MEMBER_ADD_TO_INVITE_ERROR = 302;
    private static HandlerThread responseHandlerThread;

    private static class SingleInstaneceHolder {
        private static final InviteBusiness INSTANCE = new InviteBusiness();
    }
    public static InviteBusiness getInstance() {
        return SingleInstaneceHolder.INSTANCE;
    }
    private InviteBusiness() {
        responseHandlerThread = new HandlerThread("response1_handler_thread");
        responseHandlerThread.start();
    }

    /**
     * 获取邀约列表
     */
    public void getInviteList(Context mcontext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_invite_list_url);
        HttpUtils.post(mcontext, url, params, new TextHttpResponseHandler(responseHandlerThread.getLooper()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.displayResult(STATE_GET_failed, "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("success").equals("true") || jsonObject.has("inviteList")) {
                        //成功
                        JSONArray jsonArray = jsonObject.optJSONArray("inviteList");
                        int size = jsonArray.length();
                        List<InviteBean> list = new ArrayList<InviteBean>();
                        for (int i = 0; i < size; i++) {
                            list.add(new InviteBean(jsonArray.optJSONObject(i)));
                        }
                        callback.displayResult(STATE_GET_INVITE_LIST_SUCCESS, list);
                    } else {
                        callback.displayResult(STATE_GET_INVITE_LIST_ERROR, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 用户创建邀约
     */
    public void creatInvite(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.create_invite_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //创建邀约成功
                    callback.displayResult(STATE_CREAT_INVITE_SUCCESS,flag);
                } else {
                    // 创建邀约失败
                    callback.displayResult(STATE_CREAT_INVITE_ERROR,tip);
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
     * 获取参与邀约活动的会员列表
     */
    public void getInviteMemberList(Context mcontext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_invite_member_url);
        HttpUtils.post(mcontext, url, params, new TextHttpResponseHandler(responseHandlerThread.getLooper()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.displayResult(STATE_GET_failed, "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("success").equals("true") || jsonObject.has("listMember")) {
                        //成功
                        JSONArray jsonArray = jsonObject.optJSONArray("listMember");
                        int size = jsonArray.length();
                        List<UserBean> list = new ArrayList<UserBean>();
                        for (int i = 0; i < size; i++) {
                            list.add(new UserBean(jsonArray.optJSONObject(i)));
                        }
                        callback.displayResult(STATE_GET_INVITE_MEMBER_SUCCESS, list);
                    } else {
                        callback.displayResult(STATE_GET_INVITE_MEMBER_ERROR, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 用户加入邀约
     */
    public void memberAddToInvite(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.member_add_to_invite_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //加入邀约成功
                    callback.displayResult(STATE_MEMBER_ADD_TO_INVITE_SUCCESS,flag);
                } else {
                    // 加入邀约失败
                    callback.displayResult(STATE_MEMBER_ADD_TO_INVITE_ERROR,tip);
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
