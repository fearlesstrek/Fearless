package com.example.fearless.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fearless.fearless.R;
import com.example.fearless.user.bean.CostAdminBean;
import com.example.fearless.user.bean.CostBean;
import com.example.fearless.user.controller.UserController;
import com.example.fearless.user.ipresenter.IAddCostPresent;
import com.example.fearless.user.iview.IAddCostActivityView;
import com.example.fearless.user.presentimpl.AddCostPersentImpl;
import com.example.fearless.user.ui.AddCostActivity;
import com.example.fearless.user.ui.CommitCostActivity;
import com.example.fearless.user.ui.CostAdminActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fearless on 16/3/7.
 */
public class CostAdminDatilListAdapter extends BaseAdapter{

    private Context mContext;
    private List<CostAdminBean> costBeanList;
    private LayoutInflater mInflater;

    public CostAdminDatilListAdapter(Context mContext) {
        this.mContext = mContext;
        this.costBeanList = new ArrayList<CostAdminBean>();
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return costBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return costBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.cost_admin_datil_list,null);
            holder.cost_memberName= (TextView) convertView.findViewById(R.id.member_add_cost);
            holder.change_date= (TextView) convertView.findViewById(R.id.admin_change_date);
            holder.change_cost= (TextView) convertView.findViewById(R.id.admin_change_cost);
            holder.commit_button= (TextView) convertView.findViewById(R.id.btn_admin_to_manager);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        final CostAdminBean cost = costBeanList.get(i);
        Log.i("wxy---costAdminList", costBeanList.get(0).getCostChange());
        holder.cost_memberName.setText("充值会员：" + cost.getCostMember());
        holder.change_date.setText(cost.getCostTime());
        holder.change_cost.setText("充值："+cost.getCostChange()+"元");
        holder.commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, CommitCostActivity.class);
                intent.putExtra("costMemberAdmin", cost.getCostMember());
                intent.putExtra("costMemberAdminId", cost.getId());
                intent.putExtra("costTimeAdmin", cost.getCostTime());
                intent.putExtra("costChangeAdmin", cost.getCostChange());
                intent.putExtra("costTotalAdmin", cost.getCostTotal());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }



    public void setmList(List<CostAdminBean> list){
        this.costBeanList.clear();
        this.costBeanList.addAll(list);

    }
    public void addList(List<CostAdminBean> list){
        this.costBeanList.addAll(list);

    }


    class ViewHolder {
        TextView cost_memberName;
        TextView change_date;
        TextView change_cost;
        TextView commit_button;

    }
}
