package com.example.fearless.meet.ipresenter;

/**
 * Created by Fearless on 16/3/25.
 */
public interface ISecondFragmentPresenter {
    /**
     * 请求数据
     */
    public void initDate(String memberName);

    /**
     * 请求退出邀约
     */
    public void removeToInvite(String memberName);
    /**
     * 请求扣费
     */
    public void costInviteMoney(String inviteId,String costChange);
    /**
     * 请求退出邀约
     */
    public void endInvite(String inviteId);

}
