package com.example.fearless.invite.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/7 0007.
 */
public class InviteBean {
    private String id;
    private String inviteMember;//发起人
    private String inviteName;//邀约活动名
    private String invitePlace;//活动地点
    private String inviteTime;
    private String inviteLimitNum;//活动限制人数
    private String inviteTotalCost;//活动总费用
    private String invitePhone;//活动联系方式
    private String inviteStage;//活动状态
    private String inviteRemarks;//活动备注

    public InviteBean() {
    }
    public InviteBean(JSONObject object) {
        this.id = object.optString("id");
        this.inviteMember = object.optString("inviteMember");
        this.inviteName = object.optString("inviteName");
        this.inviteTime = object.optString("inviteTime");
        this.invitePlace = object.optString("invitePlace");
        this.inviteLimitNum = object.optString("inviteLimitNum");
        this.inviteTotalCost = object.optString("inviteTotalCost");
        this.invitePhone = object.optString("invitePhone");
        this.inviteStage = object.optString("inviteStage");
        this.inviteRemarks = object.optString("inviteRemarks");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInviteMember() {
        return inviteMember;
    }

    public void setInviteMember(String inviteMember) {
        this.inviteMember = inviteMember;
    }

    public String getInviteName() {
        return inviteName;
    }

    public void setInviteName(String inviteName) {
        this.inviteName = inviteName;
    }

    public String getInvitePlace() {
        return invitePlace;
    }

    public void setInvitePlace(String invitePlace) {
        this.invitePlace = invitePlace;
    }

    public String getInviteLimitNum() {
        return inviteLimitNum;
    }

    public void setInviteLimitNum(String inviteLimitNum) {
        this.inviteLimitNum = inviteLimitNum;
    }

    public String getInviteTotalCost() {
        return inviteTotalCost;
    }

    public void setInviteTotalCost(String inviteTotalCost) {
        this.inviteTotalCost = inviteTotalCost;
    }

    public String getInviteTime() {
        return inviteTime;
    }

    public void setInviteTime(String inviteTime) {
        this.inviteTime = inviteTime;
    }

    public String getInvitePhone() {
        return invitePhone;
    }

    public void setInvitePhone(String invitePhone) {
        this.invitePhone = invitePhone;
    }

    public String getInviteStage() {
        return inviteStage;
    }

    public void setInviteStage(String inviteStage) {
        this.inviteStage = inviteStage;
    }

    public String getInviteRemarks() {
        return inviteRemarks;
    }

    public void setInviteRemarks(String inviteRemarks) {
        this.inviteRemarks = inviteRemarks;
    }
}
