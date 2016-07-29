package com.example.fearless.user.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by chc on 2015/4/30.
 * 用户资料
 */
public class UserBean implements Serializable{
    private String accountNumber;
    private String userName;
    public UserBean(){}

    public UserBean(JSONObject object) {
        this.accountNumber = object.optString("memberName");
        this.userName = object.optString("passWord");

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
