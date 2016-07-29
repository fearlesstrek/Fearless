package com.example.fearless.user.business;

import android.content.Context;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.common.utils.CommonUtils;
import com.example.fearless.common.utils.HttpUtils;
import com.example.fearless.fearless.R;
import com.example.fearless.fearless.icommon.DisplayCallback;
import com.example.fearless.user.bean.CostAdminBean;
import com.example.fearless.user.bean.CostBean;
import com.example.fearless.user.bean.UserBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
public class UserBusiness {

    /**
     * 统一获取失败
     */
    public static final int STATE_GET_failed = 0;

    /**
     * 注册状态
     */
    public static  final int STATE_SUBMIT_SUCCESS = 1;
    public static  final int STATE_SUBMIT_ERROR = 2;
    /**
     * 登录状态
     */
    public static final int STATE_LOGIN_SUCCESS = 101;
    public static final int STATE_LOGIN_ERROR = 102;
    /**
     * 修改密码状态
     */
    public static final int STATE_UPDATAPWD_SUCCESS = 201;
    public static final int STATE_UPDATAPWD_ERROR = 202;
    /**
     * 查询会费详情状态
     */
    public static final int FIND_COST_DATIL_SUCCESS = 301;
    public static final int FIND_COST_DATIL_ERROR = 302;
    /**
     * 查询会费详情状态
     */
    public static final int FIND_COST_ADMIN_DATIL_SUCCESS = 501;
    public static final int FIND_COST_ADMIN_DATIL_ERROR = 502;

    /**
     * 管理员审核通过后充值给用户的状态
     */
    public static final int FIND_ADD_COST_SUCCESS = 401;
    public static final int FIND_ADD_COST_ERROR = 402;
    /**
     * 用户充值管理员审核的状态
     */
    public static final int FIND_ADD_COST_ADMIN_SUCCESS = 601;
    public static final int FIND_ADD_COST_ADMIN_ERROR = 602;
    /**
     * 管理员删除用户充值信息的状态
     */
    public static final int FIND_DELETE_COST_ADMIN_SUCCESS = 701;
    public static final int FIND_DELETE_COST_ADMIN_ERROR = 702;

    /**
     * AsyncHttpResponseHandler的回调会在内部拥有的Handler所拥有的Looper所在线程中跑，
     * 回调中可能有耗时操作，如数据库操作，所以在线程中而不是主线程中跑
     */
    private static HandlerThread responseHandlerThread;
    private static class SingleInstaneceHolder {
        private static final UserBusiness INSTANCE = new UserBusiness();
    }
    public static UserBusiness getInstance() {
        return SingleInstaneceHolder.INSTANCE;
    }
    private UserBusiness() {
        responseHandlerThread = new HandlerThread("response_handler_thread");
        responseHandlerThread.start();
    }

    /**
     * 数据库登录
     */
    public void login(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.login_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("message");

                //判断登录是否正确
                if (flag.equals("true")) {
                    UserBean user = new UserBean(response.optJSONObject("member"));
                    //缓存token
                    Constant.cachedToken(mContext, user.getAccountNumber());
                    Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    callback.displayResult(STATE_LOGIN_SUCCESS, user);
                } else {
                    callback.displayResult(STATE_LOGIN_ERROR, tip);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }

    /**
     * 获取会费详细列表
     */
    public void findCost(final Context mContext, RequestParams params, final DisplayCallback callback) {

        String url = CommonUtils.appendRequesturl(R.string.find_cost_datil_url);
        HttpUtils.post(mContext, url, params, new TextHttpResponseHandler(responseHandlerThread.getLooper()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.displayResult(FIND_COST_DATIL_ERROR, "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("success").equals("true") || jsonObject.has("costList")) {
                        //成功
                        JSONArray jsonArray = jsonObject.optJSONArray("costList");
                        int size = jsonArray.length();
                        List<CostBean> list = new ArrayList<CostBean>();
                        for (int i = 0; i < size; i++) {
                            list.add(new CostBean(jsonArray.optJSONObject(i)));
                        }
                        if (!(list.isEmpty())) {
                            Constant.cachedCostTotal(mContext, list.get(0).getCostTotal());
                        } else {
                            Constant.cachedCostTotal(mContext, "0");
                        }
                        callback.displayResult(FIND_COST_DATIL_SUCCESS, list);
                    } else {
                        callback.displayResult(FIND_COST_DATIL_ERROR, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 获取会费详细列表
     */
    public void findCostCost(final Context mContext, RequestParams params, final DisplayCallback callback) {

        String url = CommonUtils.appendRequesturl(R.string.find_cost_datil_url);
        HttpUtils.post(mContext, url, params, new TextHttpResponseHandler(responseHandlerThread.getLooper()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.displayResult(FIND_COST_DATIL_ERROR, "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("success").equals("true") || jsonObject.has("costList")) {
                        //成功
                        JSONArray jsonArray = jsonObject.optJSONArray("costList");
                        int size = jsonArray.length();
                        List<CostBean> list = new ArrayList<CostBean>();
                        for (int i = 0; i < size; i++) {
                            list.add(new CostBean(jsonArray.optJSONObject(i)));
                        }
                        if (!(list.isEmpty())) {
                            Constant.cachedCostTotal(mContext, list.get(0).getCostTotal());
                        }else {
                            Constant.cachedCostTotal(mContext, "0");
                        }
                        callback.displayResult(FIND_COST_DATIL_SUCCESS, list);
                    } else {
                        callback.displayResult(FIND_COST_DATIL_ERROR, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取管理员会费详细列表
     */
    public void findCostAdmin(final Context mContext, RequestParams params, final DisplayCallback callback) {

        String url = CommonUtils.appendRequesturl(R.string.find_cost_admin_datil_url);
        HttpUtils.post(mContext, url, params, new TextHttpResponseHandler(responseHandlerThread.getLooper()) {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.displayResult(FIND_COST_DATIL_ERROR, "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.optString("success").equals("true") || jsonObject.has("costAdminList")) {
                        //成功
                        JSONArray jsonArray = jsonObject.optJSONArray("costAdminList");
                        int size = jsonArray.length();
                        List<CostAdminBean> list = new ArrayList<CostAdminBean>();
                        for (int i = 0; i < size; i++) {
                            list.add(new CostAdminBean(jsonArray.optJSONObject(i)));
                        }
                        callback.displayResult(FIND_COST_ADMIN_DATIL_SUCCESS, list);

                    } else {
                        callback.displayResult(FIND_COST_ADMIN_DATIL_ERROR, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 用户充值给管理员
     */
    public void findAddCostBean(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_add_cost_admin_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("failure");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //充值成功
                    callback.displayResult(FIND_ADD_COST_ADMIN_SUCCESS,1);
                } else {
                    // 充值失败
                    callback.displayResult(FIND_ADD_COST_ADMIN_ERROR,0);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
    /**
     * 管理员充值给用户
     */
    public void findAddCost(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_add_cost_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("failure");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //充值成功
                    callback.displayResult(FIND_ADD_COST_SUCCESS,1);
                } else {
                    // 充值失败
                    callback.displayResult(FIND_ADD_COST_ERROR,0);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
    /**
     * 管理员删除充值纪录
     */
    public void deleteCostAdmin(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_delete_cost_admin_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //用户名实体类解析
                String flag = response.optString("success");
                String tip = response.optString("failure");
                //判断登录是否正确
                if (flag.equals("true")) {
                    //删除成功
                    callback.displayResult(FIND_DELETE_COST_ADMIN_SUCCESS,2);
                } else {
                    // 删除失败
                    callback.displayResult(FIND_DELETE_COST_ADMIN_ERROR,0);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败
                callback.displayResult(STATE_GET_failed, null);
            }
        });
    }
    /**
     * 注册请求方法
     */
    public void submit(final Context mContext, RequestParams params, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.find_submit_user_url);
        HttpUtils.post(mContext, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                //解析注册成功的json
                String flag = response.optString("success");
                String tip = response.optString("message");
                //判断注册的信息是否正确
                if (flag.equals("true")) {
                    callback.displayResult(STATE_SUBMIT_SUCCESS, flag);
                } else {
                    callback.displayResult(STATE_SUBMIT_ERROR, tip);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //获取失败

                callback.displayResult(STATE_GET_failed,null);
            }
        });
    }

    /**
     * 修改密码
     */
    public void updataPwd(final Context mContext, RequestParams requestParams, final DisplayCallback callback) {
        String url = CommonUtils.appendRequesturl(R.string.updata_pwd_url);
        HttpUtils.post(mContext,url,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String flag = response.optString("success");
                String tip = response.optString("message");
                if (flag.equals("true")) {
                    callback.displayResult(STATE_UPDATAPWD_SUCCESS,flag);
                }else {
                    callback.displayResult(STATE_UPDATAPWD_ERROR, tip);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                callback.displayResult(STATE_GET_failed,null);

            }
        });
    }

}
