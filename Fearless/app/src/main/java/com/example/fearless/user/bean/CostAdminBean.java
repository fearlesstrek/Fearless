package com.example.fearless.user.bean;

import org.json.JSONObject;

/**
 * Created by Fearless on 16/3/7.
 */
public class CostAdminBean {

    private String id;
    private String costMember;
    private String costTotal;
    private String costChange;
    private String costTime;
    private String costRemarks;

    public CostAdminBean(){}

    public CostAdminBean(JSONObject object) {
        this.id = object.optString("id");
        this.costMember = object.optString("costMemberAdmin");
        this.costTotal = object.optString("costTotalAdmin");
        this.costChange = object.optString("costChangeAdmin");
        this.costTime = object.optString("costTimeAdmin");
        this.costRemarks = object.optString("costRemarksAdmin");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCostMember() {
        return costMember;
    }

    public void setCostMember(String costMember) {
        this.costMember = costMember;
    }

    public String getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(String costTotal) {
        this.costTotal = costTotal;
    }

    public String getCostChange() {
        return costChange;
    }

    public void setCostChange(String costChange) {
        this.costChange = costChange;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getCostRemarks() {
        return costRemarks;
    }

    public void setCostRemarks(String costRemarks) {
        this.costRemarks = costRemarks;
    }
}
