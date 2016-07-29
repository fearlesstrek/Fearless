package com.example.fearless.user.presentimpl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.fearless.user.business.UserBusiness;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.ISubmitPresent;
import com.example.fearless.user.iview.ISubmitFragmentView;
import com.example.fearless.user.ui.SubmitFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public class SubmitPresentImpl implements ISubmitPresent {

    private UserController controller;
    private ISubmitFragmentView fragmentView;
    private MyHandler myHandler = new MyHandler(this);
    private Context mContext;

    //加载进度条
    private ProgressDialog dialog;

    //构造函数
    public SubmitPresentImpl(ISubmitFragmentView fragmentView) {
        this.fragmentView = fragmentView;
        controller = new UserController(myHandler, ((SubmitFragment) fragmentView).getActivity());
        this.mContext = ((SubmitFragment) fragmentView).getActivity();
    }

    //重写继承ISubmitPresent接口的方法
    @Override
    public void getSubmit(String userName,
                          String pwd) {
        controller.submit(userName, pwd);
        if (dialog == null) {
            dialog = new ProgressDialog(mContext);
            dialog.show();
        }
    }

    //用于handle---controller的信息
    private class MyHandler extends Handler {
        //弱相关
        WeakReference<ISubmitPresent> presentWeak;

        //构造函数
        public MyHandler(ISubmitPresent presentWeak) {
            this.presentWeak = new WeakReference<ISubmitPresent>(presentWeak);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (presentWeak != null && presentWeak.get() != null) {
                SubmitPresentImpl present = (SubmitPresentImpl) presentWeak.get();
                present.handlerMessage(msg);
            }
        }
    }

    //写一个方法获取信息
    private void handlerMessage(Message msg) {
        if (msg.what == UserBusiness.STATE_SUBMIT_SUCCESS) {
            String flag = (String) msg.obj;
            fragmentView.setSubmit(flag);
        }
        if (msg.what == UserBusiness.STATE_SUBMIT_ERROR) {
            Toast.makeText(mContext, "用户名已经存在", Toast.LENGTH_SHORT).show();
        }
        if (msg.what == UserBusiness.STATE_GET_failed) {
            Toast.makeText(mContext, "联网失败或者服务器关闭", Toast.LENGTH_SHORT).show();
        }
        //dialog消失
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
