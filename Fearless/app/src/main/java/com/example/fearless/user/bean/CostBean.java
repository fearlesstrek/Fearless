package com.example.fearless.user.bean;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Fearless on 16/3/7.
 */
public class CostBean {

    private String id;
    private String costMember;
    private String costTotal;
    private String costChange;
    private String costTime;
    private String costRemarks;

    public CostBean(){}

    public CostBean(JSONObject object) {
        this.id = object.optString("id");
        this.costMember = object.optString("costMember");
        this.costTotal = object.optString("costTotal");
        this.costChange = object.optString("costChange");
        this.costTime = object.optString("costTime");
        this.costRemarks = object.optString("costRemarks");
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
