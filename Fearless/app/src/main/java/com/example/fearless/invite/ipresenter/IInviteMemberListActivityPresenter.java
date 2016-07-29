package com.example.fearless.invite.ipresenter;

/**
 * Created by Fearless on 16/3/24.
 */
public interface IInviteMemberListActivityPresenter {
    /**
     * 获得已经参与邀约活动的会员列表
     */
    public void getInviteMemberList(String inviteId);
    /**
     * 刷新
     */
    public void onRefalsh();
    /**
     * 加载
     */
    public void onLoadmore();
}
