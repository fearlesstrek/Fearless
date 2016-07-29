package com.example.fearless.invite.iview;

import com.example.fearless.invite.adapter.InviteListAdapter;
import com.example.fearless.widget.XListView;

/**
 * Created by Fearless on 16/3/13.
 */
public interface IThirdFragmentView {
    /**
     * 给列表设置adapter
     * @param adapter
     */
    public void setInviteList(InviteListAdapter adapter);

    /**
     * 返回邀约列表
     * @return
     */
    public XListView getInviteListXlv();


}
