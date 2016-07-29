package com.example.fearless.common.utils;

import android.content.Context;

import com.example.fearless.fearless.application.ImageApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;


public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static void setcoockie(){
        PersistentCookieStore myCookieStore = new PersistentCookieStore(ImageApplication.newInstance());
        client.setCookieStore(myCookieStore);
    }
    public static void initcoockie(){
        HttpContext httpContext = client.getHttpContext();
        CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
        if(cookies==null){
            setcoockie();
        }
    }
    private HttpUtils() {


    }



    public static void get(Context context, String url, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        HttpContext httpContext = client.getHttpContext();
        CookieStore cookies = (CookieStore) httpContext.getAttribute(ClientContext.COOKIE_STORE);
        if(cookies==null){
            for(Cookie c:cookies.getCookies()){
                LogUtil.Logi("main after ~~"+c.getName(),c.getValue());
            }
        }
        client.get(context, url, params, handler);
    }

    public static void post(Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(context, url, params, handler);
    }



    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
    }



    public static void post(Context context, String url, HttpEntity entity,String contentType,
                            AsyncHttpResponseHandler handler) {
        client.post(context, url, entity, contentType, handler);
    }
    public static void cancel(Context context, boolean mayInterruptIfRunning) {
        client.cancelRequests(context, mayInterruptIfRunning);
    }

    public static AsyncHttpClient getAsyncHttpClient(){
        return client;
    }
}
