package com.example.fearless.user.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fearless.common.utils.CommonUtils;
import com.example.fearless.common.utils.HttpUtils;
import com.example.fearless.fearless.R;
import com.example.fearless.user.iview.IUpdataPwdActivityView;
import com.example.fearless.user.presentimpl.UpdataPwdPresentImpl;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UpdataPwdActivity extends Activity implements IUpdataPwdActivityView{
    //数据包
    private RequestParams params;
    //初始化
    @InjectView(R.id.updata_username)
    EditText updata_username;
    @InjectView(R.id.updata_old_pwd)
    EditText updata_old_pwd;
    @InjectView(R.id.updata_new_pwd)
    EditText updata_new_pwd;

    //逻辑处理
    private UpdataPwdPresentImpl present;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_pwd);
        ButterKnife.inject(this);
        params = new RequestParams();
        present = new UpdataPwdPresentImpl(this);

    }

    @OnClick({R.id.btn_updata,R.id.btn_back_pwd})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_updata://注册按钮
                if (updata_username.getText().toString().equals("") || updata_old_pwd.getText().toString().equals("")
                        || updata_new_pwd.getText().toString().equals("") ) {
                    Toast.makeText(UpdataPwdActivity.this, "请输入完整修改信息", Toast.LENGTH_SHORT).show();
                } else {
                    present.getUpdataPwd(updata_username.getText().toString(),updata_old_pwd.getText().toString(),
                            updata_new_pwd.getText().toString());
                }
//                updataPwd(this,params);
                break;
            case R.id.btn_back_pwd:
                finish();
                break;
        }
    }

//    public void updataPwd(final Context mContext,RequestParams params) {
//        String url = CommonUtils.appendRequesturl(R.string.updata_pwd_url);
//        params.put("accountNumber", updata_username.getText().toString());
//        params.put("password", updata_old_pwd.getText().toString());
//        params.put("modifyPassword", updata_new_pwd.getText().toString());
//        params.put("secondPassword", updata_new_pwd_2.getText().toString());
//        HttpUtils.post(mContext,url,params,new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                String flag = response.optString("flag");
//                String tip = response.optString("tip");
//                if (flag.equals("success")) {
//                    Toast.makeText(mContext, "修改成功 "+ flag , Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(mContext, "修改失败" + tip, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }

    @Override
    public void setUpdataPwd(String flag) {
        if (flag.equals("true")) {
            Toast.makeText(UpdataPwdActivity.this, "修改成功 ", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(UpdataPwdActivity.this, flag, Toast.LENGTH_SHORT).show();
        }
    }
}
