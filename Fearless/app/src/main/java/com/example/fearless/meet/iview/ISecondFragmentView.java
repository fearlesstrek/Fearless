package com.example.fearless.meet.iview;

import com.example.fearless.invite.bean.InviteBean;

/**
 * Created by Fearless on 16/3/25.
 */
public interface ISecondFragmentView {
    /**
     * 显示布局
     */
    public void showInviteViewSub(InviteBean invite);

    /**
     * 设置邀约活动信息
     */
    public void setInviteDate(InviteBean invite);

    /**
     * 成功退出邀约后
     */
    public void removeSuccess();
    /**
     * 成功扣费
     */
    public void costSuccess();
    /**
     * 成功结束邀约活动
     */
    public void endSuccess();

}
