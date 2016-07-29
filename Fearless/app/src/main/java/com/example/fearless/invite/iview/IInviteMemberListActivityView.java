package com.example.fearless.invite.iview;

import com.example.fearless.invite.adapter.InviteMemberListAdapter;
import com.example.fearless.widget.XListView;

/**
 * Created by Fearless on 16/3/24.
 */
public interface IInviteMemberListActivityView {
    /**
     * 给列表设置adapter
     * @param adapter
     */
    public void setInviteMemberList(InviteMemberListAdapter adapter);
    /**
     * 给列表设置adapter
     */
    public void setAddedInviteMemberSize(int addedSize);

    /**
     * 返回邀约列表
     * @return
     */
    public XListView getInviteListXlv();
}
