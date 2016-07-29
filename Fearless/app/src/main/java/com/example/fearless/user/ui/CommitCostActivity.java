package com.example.fearless.user.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
 * Created by Fearless on 16/4/1.
 */
public class CommitCostActivity extends Activity implements IAddCostActivityView {
    @InjectView(R.id.member_add_cost_commit)
    TextView memberAddCostCommit;
    @InjectView(R.id.admin_total_cost_commit)
    TextView adminTotalCostCommit;
    @InjectView(R.id.admin_change_cost_commit)
    TextView adminChangeCostCommit;
    @InjectView(R.id.admin_change_date_commit)
    TextView adminChangeDateCommit;
    @InjectView(R.id.btn_admin_to_commit)
    TextView btnAdminToCommit;
    @InjectView(R.id.btn_admin_to_delete)
    TextView btnAdminToDelete;
    private IAddCostPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commit_cost);
        ButterKnife.inject(this);
        memberAddCostCommit.setText("会员：" + getIntent().getStringExtra("costMemberAdmin"));
        adminTotalCostCommit.setText("充值后："+getIntent().getStringExtra("costTotalAdmin")+"元");
        adminChangeCostCommit.setText("充值：" +getIntent().getStringExtra("costChangeAdmin")+"元");
        adminChangeDateCommit.setText(getIntent().getStringExtra("costTimeAdmin"));
        present = new AddCostPersentImpl(this);


    }

    //   删除和确认
    @OnClick({R.id.btn_admin_to_commit,R.id.btn_admin_to_delete})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_admin_to_commit:
                present.addCost(
                        getIntent().getStringExtra("costMemberAdmin")
                        , getIntent().getStringExtra("costTotalAdmin")
                        , getIntent().getStringExtra("costChangeAdmin"));
                break;
            case R.id.btn_admin_to_delete:
                present.deleteCost(getIntent().getStringExtra("costMemberAdminId"));
                break;
        }
    }

    @Override
    public void addCost() {

    }

    @Override
    public void setSign(int success) {
        if (success == 1) {
            Toast.makeText(this, "确认完成", Toast.LENGTH_LONG).show();
            btnAdminToCommit.setVisibility(View.GONE);
            finish();
        }
    }

    @Override
    public void setDeleteSign(int success) {
        if (success == 2) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
