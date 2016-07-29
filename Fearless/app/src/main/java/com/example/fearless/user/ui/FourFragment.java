package com.example.fearless.user.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.fearless.R;
import com.example.fearless.user.ipresenter.IFourFragmentPresent;
import com.example.fearless.user.iview.IFourFragmentView;
import com.example.fearless.user.presentimpl.FourFragmentImpl;
import com.example.fearless.user.bean.CostBean;
import com.example.fearless.user.bean.UserEvent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FourFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourFragment extends Fragment implements IFourFragmentView {

    private View mView;
    private OnFragmentInteractionListener mListener;
    private IFourFragmentPresent present;
    @InjectView(R.id.username)
    TextView userName;

    @InjectView(R.id.account)
    TextView account;
    // sharePreference
    private SharedPreferences sp;

    public static FourFragment newInstance() {
        FourFragment fragment = new FourFragment();
        return fragment;
    }

    public FourFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
//        present = new FourFragmentImpl(this);
//        present.getCostDatilList(Constant.getCachedToken(this.getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_four, container, false);
        ButterKnife.inject(this, mView);
        sp = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if (!(sp.getString("userName", "").equals(""))) {
            userName.setText(sp.getString("accountNumber", ""));

        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String costTotal = Constant.getCachedCostTotal(this.getActivity());
//        Log.i("wxyyy---costTotal", costTotal);
        if (costTotal!=null) {
            account.setText("余额：" + Constant.getCachedCostTotal(this.getActivity()));
        }
        present = new FourFragmentImpl(this);
        present.getCostDatilList(Constant.getCachedToken(this.getActivity()));
    }

    @OnClick({R.id.rel_cost, R.id.rel_add_cost, R.id.rel_about, R.id.rel_updatePwd
            , R.id.rel_head, R.id.logout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_head:     //跳转到user登录界面，采用startActivityForResult跳转
//                startActivityForResult(new Intent(getActivity(), UserLoginActivity.class),1);
                break;
            case R.id.rel_cost:    //跳转到会费查询信息界面
                Intent i = new Intent();
                i.setClass(getActivity(), CostActivity.class);
                i.putExtra("userName", userName.getText().toString());
                startActivity(i);
                break;
            case R.id.rel_add_cost:    //跳转到会费充值信息界面
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddCostActivity.class);
                intent.putExtra("userName", userName.getText().toString());
                startActivity(intent);
                break;
            case R.id.rel_updatePwd:  //跳转到修改密码界面
                startActivity(new Intent(getActivity(), UpdataPwdActivity.class));
                break;
            case R.id.rel_about:      //跳转到关于我们界面
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.logout:      //退出
                new AlertDialog.Builder(this.getActivity()).setTitle("提示").setMessage("确定要退出吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Constant.cachedToken(getActivity(), null);
                                Constant.cachedCostTotal(getActivity(), null);
                                Constant.cachedCostOrNot(getActivity(), null);
                                Constant.cachedTimeForInvite(getActivity(), null);
                                startActivity(new Intent(getActivity(), UserLoginActivity.class));
                                getActivity().finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


                break;

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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


    @Override
    public void setTotalCost(List<CostBean> costBeanList) {
        String costTotal = costBeanList.get(0).getCostTotal();

        if (costTotal!=null) {
            double total = Double.parseDouble(costTotal);
            account.setText("余额：" + total);
        } else{
            account.setText("余额：" + 0);
        }


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void onEventMainThread(UserEvent event) {
        userName.setText(event.getAccessToken());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                userName.setText(data.getStringExtra("userName"));
                account.setText(data.getStringExtra("accountNumber"));
        }
    }
}
