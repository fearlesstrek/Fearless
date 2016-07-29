package com.example.fearless.user.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduDialog;
import com.baidu.api.BaiduDialogError;
import com.baidu.api.BaiduException;
import com.example.fearless.common.utils.CommonUtils;
import com.example.fearless.common.utils.HttpUtils;
import com.example.fearless.fearless.R;
import com.example.fearless.user.bean.UserBean;
import com.example.fearless.user.bean.UserEvent;
import com.example.fearless.user.iview.ILoginFragmentView;
import com.example.fearless.user.presentimpl.LoginPresentImpl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


public class LoginFragment extends Fragment implements ILoginFragmentView{

    private OnFragmentInteractionListener mListener;
    private View mView;
    //数据包
    private RequestParams params;
    //初始化控件
    @InjectView(R.id.user_login_num)
    EditText user_login_num;
     @InjectView(R.id.user_login_pwd)
    EditText user_login_pwd;
    //flag标记
    private String flag;
    //sharepreference保存数据
    private SharedPreferences sp;
    //操作SharedPreferences的功能函数
    private SharedPreferences.Editor editor;

    //逻辑对象
    private LoginPresentImpl present;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }
    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, mView);
        //初始化数据包
        params = new RequestParams();
        //初始化SharedPreferences
        sp = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //初始化Editor
        editor = sp.edit();
        present = new LoginPresentImpl(this);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.login})
    void onClick(View view) {
        switch (view.getId()) {
//            case R.id.baidu_login://百度登录
//                baiduLogin();
//                break;
            case R.id.login://数据库登录
                if (user_login_num.getText().toString().equals("")
                        || user_login_pwd.getText().toString().equals("")) {
                    Toast.makeText(this.getActivity(), "请输入账号或者密码", Toast.LENGTH_SHORT).show();
                } else {
                    present.getLogin(user_login_num.getText().toString()
                            ,user_login_pwd.getText().toString());

                }
                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(UserBean uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //接口的方法
    @Override
    public void setUserName(UserBean user) {
        //这里的userName可以是服务器传过来的用户名，现在只是传送过来success
        editor.putString("userName", user.getUserName());
        editor.putString("accountNumber", user.getAccountNumber());
        //测试
        Log.i("wxy_use", "setUserName --->"+ user.getUserName()+user.getAccountNumber());
        //fragment向Activity发送信息
        mListener.onFragmentInteraction(user);
        editor.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(UserBean user);
    }
    //百度登录
    public void baiduLogin() {
        final Baidu baidu = new Baidu("qGl3kzFKbFDGKkg28zAhK2xR", this.getActivity());
        baidu.authorize(this.getActivity(), true, true, new BaiduDialog.BaiduDialogListener() {
            @Override
            public void onComplete(Bundle bundle) {
                EventBus.getDefault().post(new UserEvent(baidu.getAccessToken(), "1"));
            }

            @Override
            public void onBaiduException(BaiduException e) {

            }

            @Override
            public void onError(BaiduDialogError baiduDialogError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
