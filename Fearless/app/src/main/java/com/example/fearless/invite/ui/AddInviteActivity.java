package com.example.fearless.invite.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.common.NotificationService;
import com.example.fearless.common.utils.TimeActivity;
import com.example.fearless.fearless.R;
import com.example.fearless.invite.ipresenter.IAddInviteActivityPresenter;
import com.example.fearless.invite.iview.IAddInviteActivityView;
import com.example.fearless.invite.presentImpl.AddInviteActivityImpl;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Fearless on 16/3/21.
 */
public class AddInviteActivity extends Activity implements IAddInviteActivityView{

    @InjectView(R.id.add_invite_name)
    EditText add_invite_name;
    @InjectView(R.id.add_invite_time)
    EditText addInviteTime;
    @InjectView(R.id.add_invite_place)
    EditText add_invite_place;
    @InjectView(R.id.add_invite_limit_num)
    EditText add_invite_limit_num;
    @InjectView(R.id.add_invite_total_cost)
    EditText add_invite_total_cost;
    @InjectView(R.id.add_invite_phone)
    EditText add_invite_phone;
    @InjectView(R.id.add_invite_Remarks)
    EditText add_invite_Remarks;
    private IAddInviteActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_invite);
        ButterKnife.inject(this);
        presenter = new AddInviteActivityImpl(this);

    }
    @OnClick({R.id.btn_back_add_invite,R.id.btn_creat_invite,R.id.add_invite_time})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back_add_invite:
                finish();
                break;
            //时间选择
            case R.id.add_invite_time:
                startActivityForResult(new Intent(this, TimeActivity.class), 1);
                break;
            //创建invite
            case R.id.btn_creat_invite:

                if (add_invite_name.getText().toString().equals("")|| addInviteTime.getText().toString().equals("")
                        || add_invite_place.getText().toString().equals("")|| add_invite_limit_num.getText().toString().equals("")
                        || add_invite_total_cost.getText().toString().equals("")|| add_invite_phone.getText().toString().equals("")
                        || add_invite_Remarks.getText().toString().equals("")) {
                    Toast.makeText(this, "请填好表格", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar timeForInvite = Constant.getCalendarByInintData(addInviteTime.getText().toString());
                    Date timeLong = timeForInvite.getTime();
                    Date curDate = new Date(System.currentTimeMillis());
                    long diff = timeLong.getTime() - curDate.getTime();
                    if (diff < 0) {
                        Toast.makeText(this, "不能创建过期活动", Toast.LENGTH_SHORT).show();
                    }else {
                        presenter.creatInvite(Constant.getCachedToken(this),add_invite_name.getText().toString()
                                ,addInviteTime.getText().toString(),add_invite_place.getText().toString()
                                ,add_invite_limit_num.getText().toString(),add_invite_total_cost.getText().toString()
                                ,add_invite_phone.getText().toString(),add_invite_Remarks.getText().toString()
                        );
                    }
                }
                break;
        }
    }

    @Override
    public void creatInviteSuccessOrNot(String flag) {
        if (flag.equals("true")) {
            Toast.makeText(this, "创建邀约活动成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "您已参加活动", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==2) {
                    addInviteTime.setText(data.getStringExtra("time"));
                }
                break;
        }
    }
}
