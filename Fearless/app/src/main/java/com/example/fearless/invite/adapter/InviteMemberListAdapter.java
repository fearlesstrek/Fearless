package com.example.fearless.invite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fearless.fearless.R;
import com.example.fearless.user.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fearless on 16/3/21.
 */
public class InviteMemberListAdapter extends BaseAdapter {

    private List<UserBean> inviteMemberList;
    private Context mContext;
    private LayoutInflater mInflater;

    public InviteMemberListAdapter(Context mContext) {
        this.mContext = mContext;
        this.inviteMemberList = new ArrayList<UserBean>();
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return inviteMemberList.size();
    }

    @Override
    public Object getItem(int i) {
        return inviteMemberList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.invite_member, null);
            holder.add_invite_member_name = (TextView) convertView.findViewById(R.id.add_invite_member_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserBean userBean = inviteMemberList.get(i);
        holder.add_invite_member_name.setText(userBean.getAccountNumber());
        return convertView;
    }

    class ViewHolder {
        TextView add_invite_member_name;


    }
    public void setmList(List<UserBean> list){
        this.inviteMemberList.clear();
        this.inviteMemberList.addAll(list);

    }
    public void addList(List<UserBean> list){
        this.inviteMemberList.addAll(list);

    }
}