package com.example.fearless.user.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fearless.fearless.R;
import com.example.fearless.user.ipresenter.ISubmitPresent;
import com.example.fearless.user.iview.ISubmitFragmentView;
import com.example.fearless.user.presentimpl.SubmitPresentImpl;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubmitFragment extends Fragment implements ISubmitFragmentView {


    private OnFragmentInteractionListener mListener;
    private View mView;
    //EditText的数据
    @InjectView(R.id.user_login_name)
    EditText user_login_name;
    @InjectView(R.id.user_login_pwd)
    EditText user_login_pwd;


    //逻辑presenter
    private ISubmitPresent present;
    public static SubmitFragment newInstance() {
        SubmitFragment fragment = new SubmitFragment();
        return fragment;
    }
    public SubmitFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_submit, container, false);
        ButterKnife.inject(this, mView);
        present = new SubmitPresentImpl(this);
        return mView;
    }

    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    //注册按钮响应事件
    @OnClick(R.id.submit_login)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_login:
                present.getSubmit( user_login_name.getText().toString()
                        , user_login_pwd.getText().toString()
                       );
                break;
        }
    }
    public void onButtonPressed(Uri uri) {
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
    //接口ISubmitFragmentView
    @Override
    public void setSubmit(String flag) {
        if (flag.equals("true")) {
            Toast.makeText(this.getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
        }

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
