package com.example.fearless.invite.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.fearless.R;
import com.example.fearless.invite.ipresenter.IInviteDetailActivityPresenter;
import com.example.fearless.invite.iview.IInviteDetailActivityView;
import com.example.fearless.invite.presentImpl.InviteDetailActivityImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Fearless on 16/3/21.
 */
public class InviteDetailActivity extends Activity implements IInviteDetailActivityView{
    @InjectView(R.id.invite_id)
    TextView invite_id;
    @InjectView(R.id.invite_name)
    TextView invite_name;
    @InjectView(R.id.invite_time)
    TextView invite_time;
    @InjectView(R.id.invite_place)
    TextView invite_place;
    @InjectView(R.id.invite_stage)
    TextView invite_stage;
    @InjectView(R.id.invite_avg_money)
    TextView invite_avg_money;
    @InjectView(R.id.invite_phone)
    TextView invite_phone;
    @InjectView(R.id.invite_total_cost)
    TextView invite_total_cost;
    @InjectView(R.id.limit_num)
    TextView limit_num;

    @InjectView(R.id.invite_remarks)
    TextView invite_remarks;
    @InjectView(R.id.invite_member_name)
    TextView invite_member_name;
    private IInviteDetailActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_detail);
        ButterKnife.inject(this);
        invite_id.setText(getIntent().getStringExtra("inviteId") + ".");
        invite_name.setText("邀约名：" + getIntent().getStringExtra("inviteName"));
        invite_member_name.setText("发起人：" + getIntent().getStringExtra("inviteMember"));
        invite_time.setText("时间：" + getIntent().getStringExtra("inviteTime"));
        invite_place.setText("地点：" + getIntent().getStringExtra("invitePlace"));
        invite_stage.setText("活动状态：" + getIntent().getStringExtra("inviteStage"));
        invite_phone.setText("电话：" + getIntent().getStringExtra("invitePhone"));
        invite_total_cost.setText("总费用：" + getIntent().getStringExtra("inviteTotalCost"));
        invite_avg_money.setText("人均费用：" + getIntent().getStringExtra("inviteAvgMoney")+" 元/人");
        limit_num.setText("上限人数：" + getIntent().getStringExtra("limitNum"));
        invite_remarks.setText(getIntent().getStringExtra("inviteRemarks"));
        presenter = new InviteDetailActivityImpl(this);

    }

    @OnClick({R.id.btn_back_invite,R.id.btn_add_to_invite,R.id.to_find_invite_member})
    void OnClick(View v){
        switch (v.getId()){
            case R.id.btn_back_invite:
                finish();
                break;
            //加入邀约
            case R.id.btn_add_to_invite:
                //检查活动是否过期

                Calendar timeForInvite =Constant.getCalendarByInintData(getIntent().getStringExtra("inviteTime"));
                String inviteTime = getIntent().getStringExtra("inviteTime");
                Date timeLong = timeForInvite.getTime();
                Date curDate = new Date(System.currentTimeMillis());
                long diff = timeLong.getTime()-curDate.getTime();
//                Log.i("wxy---现在时间:",""+System.currentTimeMillis());
                Log.i("wxy---邀约时间:",""+diff);
                if (getIntent().getStringExtra("inviteStage").equals("结束")) {
                    Toast.makeText(this, "该活动人数满", Toast.LENGTH_SHORT).show();
                }else if (diff <= 0) {
                    Toast.makeText(this, "该活动过期", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.memberAddToInvite(Constant.getCachedToken(this), getIntent().getStringExtra("inviteId"));
                }
                break;
            //查找当前参与得会员
            case R.id.to_find_invite_member:
                Intent i = new Intent();
                i.setClass(this, InviteMemberListActivity.class);
                i.putExtra("limitNumToAdded", getIntent().getStringExtra("limitNum"));
                i.putExtra("invite_Id", getIntent().getStringExtra("inviteId"));
                startActivity(i);
                break;
        }
    }

    @Override
    public void setAddToInviteSign(String flag) {
        if (flag.equals("true")) {
            Toast.makeText(this, "加入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, flag, Toast.LENGTH_SHORT).show();
        }
    }
}
