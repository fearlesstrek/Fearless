package com.example.fearless.invite.ipresenter;

/**
 * Created by Fearless on 16/3/24.
 */
public interface IAddInviteActivityPresenter {
    /**
     * 创建邀约活动
     */
    public void creatInvite(String inviteMember, String inviteName, String inviteTime, String invitePlace
            , String inviteLimitNum, String inviteTotalCost, String invitePhone, String inviteRemarks);
}
