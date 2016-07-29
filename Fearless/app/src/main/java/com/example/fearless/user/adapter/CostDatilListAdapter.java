package com.example.fearless.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fearless.fearless.R;
import com.example.fearless.user.bean.CostBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fearless on 16/3/7.
 */
public class CostDatilListAdapter extends BaseAdapter{

    private Context mContext;
    private List<CostBean> costBeanList;
    private LayoutInflater mInflater;

    public CostDatilListAdapter(Context mContext) {
        this.mContext = mContext;
        this.costBeanList = new ArrayList<CostBean>();
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.cost_datil_list,null);
            holder.total_cost= (TextView) convertView.findViewById(R.id.total_cost);
            holder.change_date= (TextView) convertView.findViewById(R.id.change_date);
            holder.change_cost= (TextView) convertView.findViewById(R.id.change_cost);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        final CostBean cost = costBeanList.get(i);

        holder.total_cost.setText("总额："+cost.getCostTotal()+"元");
        holder.change_date.setText(cost.getCostTime());
        holder.change_cost.setText(cost.getCostChange()+"元");
        return convertView;
    }



    public void setmList(List<CostBean> list){
        this.costBeanList.clear();
        this.costBeanList.addAll(list);

    }
    public void addList(List<CostBean> list){
        this.costBeanList.addAll(list);

    }

    class ViewHolder {
        TextView total_cost;
        TextView change_date;
        TextView change_cost;
    }
}
