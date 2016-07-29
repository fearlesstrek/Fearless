package com.example.fearless.user.bean;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public class UserEvent {

    private String accessToken;
    private String msg;
    public UserEvent(String accessToken,String msg) {
        this.accessToken = accessToken;
        this.msg = msg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getMsg() {
        return msg;
    }
}
