package com.example.fearless.invite.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fearless.invite.bean.InviteBean;
import com.example.fearless.fearless.R;
import com.example.fearless.invite.ui.InviteDetailActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/7 0007.
 */
public class InviteListAdapter extends BaseAdapter {

    private List<InviteBean> inviteList;
    private Context mContext;
    private LayoutInflater mInflater;

    public InviteListAdapter(Context mContext) {
        this.mContext = mContext;
        this.inviteList = new ArrayList<InviteBean>();
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return inviteList.size();
    }

    @Override
    public Object getItem(int position) {
        return inviteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.invitelist, null);
            holder.invite_name = (TextView) convertView.findViewById(R.id.invite_name);
            holder.invite_id = (TextView) convertView.findViewById(R.id.invite_id);
            holder.invite_time = (TextView) convertView.findViewById(R.id.invite_time);
            holder.invite_place = (TextView) convertView.findViewById(R.id.invite_place);
            holder.limit_num = (TextView) convertView.findViewById(R.id.limit_num);
            holder.invite_avg_money = (TextView) convertView.findViewById(R.id.invite_avg_money);
            holder.invite_detail = (TextView) convertView.findViewById(R.id.invite_detail);

            convertView.setTag(holder);
        } else {
          holder = (ViewHolder) convertView.getTag();
        }
        final InviteBean inviteBean = inviteList.get(position);

        holder.invite_id.setText(inviteBean.getId()+".");
        holder.invite_name.setText("邀约名："+inviteBean.getInviteName());
        holder.invite_time.setText("时间："+inviteBean.getInviteTime());
        holder.invite_place.setText("地点："+inviteBean.getInvitePlace());
        holder.limit_num.setText("上限人数："+inviteBean.getInviteLimitNum());

        final double totalCost = Double.parseDouble(inviteBean.getInviteTotalCost());
        final double limitNum = Double.parseDouble(inviteBean.getInviteLimitNum());
        final DecimalFormat df = new DecimalFormat("0.00");
        final String avgCost = df.format(totalCost / limitNum);
        holder.invite_avg_money.setText("人均费用："+avgCost+" 元/人");
//      跳转到邀约活动详情
        holder.invite_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                i.putExtra("inviteAvgMoney",avgCost);
                i.putExtra("inviteRemarks", inviteBean.getInviteRemarks());
                i.putExtra("inviteId", inviteBean.getId());
                mContext.startActivity(i);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView invite_id;
        TextView invite_name;
        TextView invite_time;
        TextView invite_place;
        TextView limit_num;
        TextView invite_avg_money;
        TextView invite_detail;

    }
    public void setmList(List<InviteBean> list){
        this.inviteList.clear();
        this.inviteList.addAll(list);

    }
    public void addList(List<InviteBean> list){
        this.inviteList.addAll(list);

    }

}
