package com.example.fearless.meet.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fearless.common.Constant;
import com.example.fearless.common.NotificationService;
import com.example.fearless.invite.bean.InviteBean;
import com.example.fearless.invite.ui.InviteDetailActivity;
import com.example.fearless.fearless.R;
import com.example.fearless.meet.ipresenter.ISecondFragmentPresenter;
import com.example.fearless.meet.iview.ISecondFragmentView;
import com.example.fearless.meet.presenter.SecondFragmentImpl;
import com.example.fearless.widget.MyScrollowView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class SecondFragment extends Fragment implements ISecondFragmentView, View.OnClickListener, MyScrollowView.OnScrollListener {


    @InjectView(R.id.third_invite_viewsub)
    ViewStub third_invite_viewsub;
    /**
     * 邀约活动名
     */
    @Optional
    @InjectView(R.id.invite_id_sub)
    TextView inviteIdSub;
    @Optional
    @InjectView(R.id.invite_name_sub)
    TextView inviteNameSub;
    @Optional
    @InjectView(R.id.invite_time_sub)
    TextView inviteTimeSub;
    @Optional
    @InjectView(R.id.invite_place_sub)
    TextView invitePlaceSub;
    @Optional
    @InjectView(R.id.invite_avg_money_sub)
    TextView inviteAvgMoneySub;
    @Optional
    @InjectView(R.id.limit_num_sub)
    TextView inviteLimitNumSub;
    @Optional
    @InjectView(R.id.invite_detail_sub)
    TextView inviteDetailSub;
    @Optional
    @InjectView(R.id.exit_invite)
    TextView inviteExit;
    @Optional
    @InjectView(R.id.end_invite)
    TextView inviteEnd;
    @Optional
    @InjectView(R.id.cost_for_invite)
    TextView inviteForCost;
    private OnFragmentInteractionListener mListener;
    private View mView;
    private InviteBean inviteBean;
    private String avgCost;

    private Context mContext;
    private ISecondFragmentPresenter presenter;
    private View viewStub;
    private int costOrNot = 1;
    private Intent intentService;


    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    public SecondFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new SecondFragmentImpl(this);
        presenter.initDate(Constant.getCachedToken(this.getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.inject(this, mView);
        return mView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (intentService != null) {
            this.getActivity().stopService(intentService);
        }

    }

    @Override
    public void showInviteViewSub(InviteBean invite) {
        if (viewStub == null) {
            viewStub = third_invite_viewsub.inflate();
        }
        viewStub.setVisibility(View.VISIBLE);

        inviteNameSub = ButterKnife.findById(viewStub, R.id.invite_name_sub);
        inviteIdSub = ButterKnife.findById(viewStub, R.id.invite_id_sub);
        inviteTimeSub = ButterKnife.findById(viewStub, R.id.invite_time_sub);
        invitePlaceSub = ButterKnife.findById(viewStub, R.id.invite_place_sub);
        inviteAvgMoneySub = ButterKnife.findById(viewStub, R.id.invite_avg_money_sub);
        inviteLimitNumSub = ButterKnife.findById(viewStub, R.id.limit_num_sub);
        inviteDetailSub = ButterKnife.findById(viewStub, R.id.invite_detail_sub);
        inviteExit = ButterKnife.findById(viewStub, R.id.exit_invite);
        inviteEnd = ButterKnife.findById(viewStub, R.id.end_invite);
        inviteForCost = ButterKnife.findById(viewStub, R.id.cost_for_invite);

        inviteIdSub.setText(invite.getId() + ".");
        inviteNameSub.setText("邀约名：" + invite.getInviteName());
        inviteTimeSub.setText("时间：" + invite.getInviteTime());
        invitePlaceSub.setText("地点：" + invite.getInvitePlace());
        final double totalCost = Double.parseDouble(invite.getInviteTotalCost());
        final double limitNum = Double.parseDouble(invite.getInviteLimitNum());
        final DecimalFormat df = new DecimalFormat("0.00");
        avgCost = df.format(totalCost / limitNum);
        inviteAvgMoneySub.setText("人均费用：" + avgCost + "元/人");
        inviteLimitNumSub.setText("上限人数：" + invite.getInviteLimitNum());
        if (Constant.getCachedToken(this.getActivity()).equals(invite.getInviteMember())) {
            inviteExit.setVisibility(View.GONE);

        }
        inviteDetailSub.setOnClickListener(this);
        inviteExit.setOnClickListener(this);
        inviteEnd.setOnClickListener(this);
        inviteForCost.setOnClickListener(this);
        if (!(Constant.getCachedToken(this.getActivity()).equals(invite.getInviteMember()))) {
            inviteForCost.setVisibility(View.GONE);
            inviteEnd.setVisibility(View.GONE);
        } else {
            String token = Constant.getCachedCostOrNot(this.getActivity());
            //如果token为空，则说明还没有登录或者已经退出登录
            if (token != null) {
                inviteForCost.setVisibility(View.GONE);
            }
        }
//        Log.i("wxy-----changeTime",invite.getInviteTime());
        this.inviteBean = invite;
        //开启服务，提醒用户一小时后有活动
        Calendar timeForInvite = Constant.getCalendarByInintData(invite.getInviteTime());
        Date timeLong = timeForInvite.getTime();
        Date curDate = new Date(System.currentTimeMillis());
        long diff = timeLong.getTime() - curDate.getTime();
        if (diff > 0) {
            intentService = new Intent();
            intentService.putExtra("inviteTime", invite.getInviteTime());
            intentService.setClass(this.getActivity(), NotificationService.class);
            this.getActivity().startService(intentService);

        }

    }

    //事件响应
    @Override
    public void onClick(View view) {
        //检查活动是否过期
        Calendar timeForInvite = Constant.getCalendarByInintData(inviteBean.getInviteTime());
        Date timeLong = timeForInvite.getTime();
        Date curDate = new Date(System.currentTimeMillis());
        long diff = timeLong.getTime() - curDate.getTime();
        switch (view.getId()) {
            case R.id.invite_detail_sub:
                Intent i = new Intent();
                i.setClass(mContext, InviteDetailActivity.class);
                i.putExtra("inviteName", inviteBean.getInviteName());
                i.putExtra("inviteMember", inviteBean.getInviteMember());
                i.putExtra("inviteTime", inviteBean.getInviteTime());
                i.putExtra("invitePlace", inviteBean.getInvitePlace());
                i.putExtra("limitNum", inviteBean.getInviteLimitNum());
                i.putExtra("invitePhone", inviteBean.getInvitePhone());
                i.putExtra("inviteTotalCost", inviteBean.getInviteTotalCost());
                i.putExtra("inviteStage", inviteBean.getInviteStage());
                i.putExtra("inviteAvgMoney", avgCost);
                i.putExtra("inviteRemarks", inviteBean.getInviteRemarks());
                i.putExtra("inviteId", inviteBean.getId());
                mContext.startActivity(i);
                break;
            //退出邀约
            case R.id.exit_invite:
                //如果是活动邀约人，并且当前活动过期
                if (Constant.getCachedToken(this.getActivity()).equals(inviteBean.getInviteMember())) {

                } else {
                    if (diff <= 0) {
                        Toast.makeText(this.getActivity(), "活动已经开启，由发起人统一扣费退出", Toast.LENGTH_SHORT).show();
                    } else {
                        String memberName = Constant.getCachedToken(this.getActivity());
                        presenter.removeToInvite(memberName);
                    }
                }
                break;
            //扣费
            case R.id.cost_for_invite:
                if (Constant.getCachedToken(this.getActivity()).equals(inviteBean.getInviteMember())) {
                    if (inviteBean.getInviteStage().equals("结束")) {
                        if (diff > 0) {
                            Toast.makeText(this.getActivity(), "活动未开始", Toast.LENGTH_SHORT).show();
                        } else {
                            presenter.costInviteMoney(inviteBean.getId(), avgCost);
                        }
                    } else {
                        if (diff < 0) {
                            presenter.costInviteMoney(inviteBean.getId(), avgCost);
                        }
                        if (diff > 0) {
                            Toast.makeText(this.getActivity(), "活动未开始", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    inviteForCost.setVisibility(View.GONE);
                }
                break;
            //结束活动
            case R.id.end_invite:
                if (Constant.getCachedToken(this.getActivity()).equals(inviteBean.getInviteMember())) {
                    //交费按钮存在
                    if (inviteForCost.getVisibility() == View.VISIBLE) {
                        //活动还没开始，可以删除
                        if (diff > 0) {
                            presenter.endInvite(inviteBean.getId());
                        }
                        //活动已经开始，先交费才能删除
                        else {
                            Toast.makeText(this.getActivity(), "请先交费", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //交费后，扣费按钮不存在
                    else {
                        presenter.endInvite(inviteBean.getId());
                    }
                } else {
                    Toast.makeText(this.getActivity(), "您不是活动发起人，无法操作", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //  成功退出
    @Override
    public void removeSuccess() {
        viewStub.setVisibility(View.GONE);
    }

    //  成功扣费
    @Override
    public void costSuccess() {
        Toast.makeText(this.getActivity(), "扣费成功", Toast.LENGTH_SHORT).show();
        inviteForCost.setVisibility(View.GONE);
        if (Constant.getCachedCostOrNot(this.getActivity()).equals("cost")) {
            inviteForCost.setVisibility(View.GONE);
        }

    }

    //邀约删除成功
    @Override
    public void endSuccess() {
        Toast.makeText(this.getActivity(), "邀约删除成功", Toast.LENGTH_SHORT).show();
        viewStub.setVisibility(View.GONE);
    }

    @Override
    public void setInviteDate(InviteBean invite) {


    }

    @Override
    public void onScroll(int scrollY) {


    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
