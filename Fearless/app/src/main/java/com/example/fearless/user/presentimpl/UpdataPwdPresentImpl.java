package com.example.fearless.user.presentimpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.IUpdataPwdPresent;
import com.example.fearless.user.iview.IUpdataPwdActivityView;
import com.example.fearless.user.ui.UpdataPwdActivity;
import com.loopj.android.http.PersistentCookieStore;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public class UpdataPwdPresentImpl implements IUpdataPwdPresent {

    private UserController controller;
    private Context mContext;
    private IUpdataPwdActivityView updataPwdActivityView;
    private MyHandler myHandler = new MyHandler(this);

    //dialog
    private ProgressDialog dialog;

    public UpdataPwdPresentImpl(IUpdataPwdActivityView updataPwdActivityView) {
        this.updataPwdActivityView = updataPwdActivityView;
        mContext = ((UpdataPwdActivity)updataPwdActivityView);
        controller = new UserController(myHandler, mContext);

    }
//  密码更新
    @Override
    public void getUpdataPwd(String userAccount, String old_pwd,
                             String new_pwd_1) {
        controller.updataPwd(userAccount, old_pwd, new_pwd_1);
        if (dialog == null) {
            dialog = new ProgressDialog(mContext);
            dialog.show();
        }

    }

    private class MyHandler extends Handler {
        WeakReference<IUpdataPwdPresent> present;

        public  MyHandler(IUpdataPwdPresent present) {
            this.present = new WeakReference<IUpdataPwdPresent>(present);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (present != null && present.get() != null) {
                UpdataPwdPresentImpl pwdPresent = (UpdataPwdPresentImpl) present.get();
                pwdPresent.handlerMessage(msg);
            }
        }
    }

    private void handlerMessage(Message msg) {
        if (msg.what == UserBusiness.STATE_UPDATAPWD_SUCCESS) {
            String flag = (String) msg.obj;
            updataPwdActivityView.setUpdataPwd(flag);
        }
        if (msg.what == UserBusiness.STATE_UPDATAPWD_ERROR){
            String tip = (String) msg.obj;
            updataPwdActivityView.setUpdataPwd(tip);
        }

        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
