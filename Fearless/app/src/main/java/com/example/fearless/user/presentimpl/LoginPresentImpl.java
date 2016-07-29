package com.example.fearless.user.presentimpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.user.bean.UserBean;
import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.ILoginPresent;
import com.example.fearless.user.iview.ILoginFragmentView;
import com.example.fearless.user.ui.LoginFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public class LoginPresentImpl implements ILoginPresent {
    private UserController controller;
    private Context mContext;
    private MyHandler myHandler = new MyHandler(this);
    private ILoginFragmentView iLoginFragmentView;
    //加载进度条
    private ProgressDialog dialog;

    public LoginPresentImpl(ILoginFragmentView iLoginFragmentView) {
        this.iLoginFragmentView = iLoginFragmentView;
        mContext = ((LoginFragment) iLoginFragmentView).getActivity();
        controller = new UserController(myHandler, mContext);

    }
//  登录
    @Override
    public void getLogin(String accountNumber, String password) {
        controller.login(accountNumber, password);
        if (dialog == null) {
            dialog = new ProgressDialog(mContext);
            dialog.show();
        }
    }

    private class MyHandler extends Handler{
        WeakReference<ILoginPresent> present;
        public MyHandler(ILoginPresent present) {
            this.present = new WeakReference<ILoginPresent>(present);
        }
        //重写方法
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (present != null && present.get() != null) {
                LoginPresentImpl loginPresent = (LoginPresentImpl) present.get();
                loginPresent.handlerMessage(msg);
            }
        }
    }
    //写一个方法获取信息
    private void handlerMessage(Message msg) {
        if (msg.what == UserBusiness.STATE_LOGIN_SUCCESS) {
            //
            UserBean user = (UserBean) msg.obj;
            iLoginFragmentView.setUserName(user);

        }
        if (msg.what == UserBusiness.STATE_LOGIN_ERROR) {
            //错误提示
            String tip = (String) msg.obj;
            Toast.makeText(mContext, "用户或者密码错误提示！", Toast.LENGTH_SHORT).show();
        }
        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (dialog != null) {
            dialog.dismiss();
        }

    }
}
