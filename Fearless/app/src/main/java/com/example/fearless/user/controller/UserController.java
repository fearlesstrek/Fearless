package com.example.fearless.user.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.fearless.fearless.icommon.DisplayCallback;
import com.example.fearless.user.business.UserBusiness;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public class UserController {
    private Context mContext;
    private Handler mHandler;
    private UserBusiness business = UserBusiness.getInstance();
    //构造函数
    public UserController(Handler mHandler, Context mContext) {
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

    //登录
    public void login(String userAccount, String pwd) {
        RequestParams params = new RequestParams();
        params.put("accountNumber",userAccount);
        params.put("password", pwd);
        business.login(mContext, params, callback);
    }

    //获取用户会费详细列表
    public void findCostList(String accountNumber,int page) {
        RequestParams params = new RequestParams();
        params.put("accountNumber",accountNumber);
        params.put("page",page);
        business.findCost(mContext, params, callback);
    }

    //获取用户会费详细列表
    public void findCostCostList(String accountNumber,int page) {
        RequestParams params = new RequestParams();
        params.put("accountNumber",accountNumber);
        params.put("page",page);
        business.findCostCost(mContext, params, callback);
    }
    //获取用户充值详细列表
    public void findCostAdminList() {
        RequestParams params = new RequestParams();
        business.findCostAdmin(mContext, params, callback);
    }
    //注册数据
    public void submit(String accountNumber, String pwd) {
        RequestParams params = new RequestParams();
        params.put("accountNumber", accountNumber);
        params.put("password", pwd);
        business.submit(mContext, params, callback);
    }

    //充值给管理员
    public  void findAddCostAdmin(String accountNumber, String lastCostTotal, String costChange) {
        RequestParams params = new RequestParams();
        params.put("accountNumber", accountNumber);
        params.put("lastCostTotal", lastCostTotal);
        params.put("costChange", costChange);
        business.findAddCostBean(mContext, params, callback);
    }
    //管理员充值给用户
    public  void findAddCost(String accountNumber, String lastCostTotal, String costChange) {
        RequestParams params = new RequestParams();
        params.put("accountNumber", accountNumber);
        params.put("lastCostTotal", lastCostTotal);
        params.put("costChange", costChange);
        business.findAddCost(mContext, params, callback);
    }
    //管理员充值给用户
    public  void findDeleteCostAdmin(String costId) {
        RequestParams params = new RequestParams();
        params.put("costId", costId);
        business.deleteCostAdmin(mContext, params, callback);
    }
    //修改密码
    public void updataPwd(String userAccount, String old_pwd, String new_pwd_1) {
        RequestParams params = new RequestParams();
        params.put("accountNumber",userAccount);
        params.put("password",old_pwd);
        params.put("newPassword",new_pwd_1);
        business.updataPwd(mContext, params, callback);
    }

}
