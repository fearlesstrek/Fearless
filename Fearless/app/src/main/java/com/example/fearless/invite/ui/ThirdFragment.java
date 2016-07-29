package com.example.fearless.invite.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fearless.invite.adapter.InviteListAdapter;
import com.example.fearless.invite.bean.InviteBean;
import com.example.fearless.fearless.R;
import com.example.fearless.invite.ipresenter.IThirdFragmentPresenter;
import com.example.fearless.invite.iview.IThirdFragmentView;
import com.example.fearless.invite.presentImpl.ThirdFragmentImpl;
import com.example.fearless.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ThirdFragment extends Fragment implements IThirdFragmentView, XListView.IXListViewListener {

    private OnFragmentInteractionListener mListener;
    private View mView;
    private List<InviteBean> inviteList;
    private IThirdFragmentPresenter presenter;
    @InjectView(R.id.invite_list)
    XListView invite_list;

    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance() {
        ThirdFragment fragment = new ThirdFragment();
        return fragment;
    }

    public ThirdFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_third, container, false);
        ButterKnife.inject(this, mView);
        return mView;
    }
//  增加邀约活动
    @OnClick({R.id.add_invite})
     void OnClick(View v){
        switch (v.getId()){
            case R.id.add_invite:
                this.getActivity().startActivity(new Intent(this.getActivity(),AddInviteActivity.class));
                break;
        }
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ThirdFragmentImpl(this);
        presenter.getInviteList();
        invite_list.setXListViewListener(this);
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

    //列表setAapter
    @Override
    public void setInviteList(InviteListAdapter adapter) {
        invite_list.setAdapter(adapter);
    }

    //返回listview
    @Override
    public XListView getInviteListXlv() {
        return invite_list;
    }

    //刷新
    @Override
    public void onRefresh() {
        presenter.onRefalsh();
    }

    //加载更多
    @Override
    public void onLoadMore() {
        presenter.onLoadmore();
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
