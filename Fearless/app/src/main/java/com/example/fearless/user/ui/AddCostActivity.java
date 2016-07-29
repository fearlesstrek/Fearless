package com.example.fearless.user.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.fearless.R;
import com.example.fearless.user.ipresenter.IAddCostPresent;
import com.example.fearless.user.iview.IAddCostActivityView;
import com.example.fearless.user.presentimpl.AddCostPersentImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Fearless on 16/3/9.
 */
public class AddCostActivity extends Activity implements IAddCostActivityView {


    @InjectView(R.id.change_cost_num)
    EditText change_cost_num;
    private IAddCostPresent present;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);
        ButterKnife.inject(this);
        present = new AddCostPersentImpl(this);
    }

    @OnClick({R.id.btn_back, R.id.add_cost_url})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.add_cost_url:
                //代码写到这，开始充值
                present.addCostAdmin(
                        getIntent().getStringExtra("userName")
                        , Constant.getCachedCostTotal(this)
                        , change_cost_num.getText().toString());
                break;
        }
    }

    @Override
    public void addCost() {

    }

    @Override
    public void setSign(int success) {
        if (success == 1) {
            Toast.makeText(this, "充值完成，等待管理员确认", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "充值失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setDeleteSign(int success) {

    }
}
