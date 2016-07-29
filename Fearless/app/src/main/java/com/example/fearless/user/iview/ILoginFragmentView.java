package com.example.fearless.user.iview;

import com.example.fearless.user.bean.UserBean;
import com.example.fearless.user.bean.UserEvent;
import com.example.fearless.user.business.UserBusiness;

/**
 * Created by Administrator on 2015/8/26 0026.
 */
public interface ILoginFragmentView {
    /**
     * 设置用户名
     * @param user
     */
    public void setUserName(UserBean user);
}
