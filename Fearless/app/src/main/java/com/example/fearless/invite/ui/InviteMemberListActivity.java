package com.example.fearless.invite.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fearless.fearless.R;
import com.example.fearless.invite.adapter.InviteMemberListAdapter;
import com.example.fearless.invite.ipresenter.IInviteMemberListActivityPresenter;
import com.example.fearless.invite.iview.IInviteMemberListActivityView;
import com.example.fearless.invite.presentImpl.InviteMemberListActivityImpl;
import com.example.fearless.widget.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Fearless on 16/3/24.
 */
public class InviteMemberListActivity extends Activity implements IInviteMemberListActivityView,XListView.IXListViewListener {
    @InjectView(R.id.find_invite_member_list)
    XListView find_invite_member_list;

    @InjectView(R.id.added_invite)
    TextView addedInvite;
    private IInviteMemberListActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_member_list);
        ButterKnife.inject(this);
        find_invite_member_list.setXListViewListener(this);
        presenter = new InviteMemberListActivityImpl(this);
        //获取已经参与活动的用户列表
        presenter.getInviteMemberList(getIntent().getStringExtra("invite_Id"));
    }

    //刷新
    @Override
    public void onRefresh() {
        presenter.onRefalsh();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadmore();
    }

    @Override
    public void setInviteMemberList(InviteMemberListAdapter adapter) {
        find_invite_member_list.setAdapter(adapter);

    }

    @Override
    public void setAddedInviteMemberSize(int addedSize) {
        addedInvite.setText("已经参加："+addedSize +"人"+ "；" +"上限人数："+ getIntent().getStringExtra("limitNumToAdded")+"人");
    }

    @Override
    public XListView getInviteListXlv() {
        return find_invite_member_list;
    }
}
