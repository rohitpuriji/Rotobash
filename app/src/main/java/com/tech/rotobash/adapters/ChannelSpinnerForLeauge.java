package com.tech.rotobash.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.CustomSpinnerLayoutBinding;
import com.tech.rotobash.model.LeagueContestData;

import java.util.ArrayList;

/**
 * @Module Name/Class		:	ChannelSpinnerForLeauge
 * @Author Name             :	Sombir
 * @Date :	Jan 18th , 2018
 * @Purpose :	This is used to set the items in league spinner on contest screen
 */
public class ChannelSpinnerForLeauge extends BaseAdapter {
    private ArrayList<LeagueContestData> list;

    public ChannelSpinnerForLeauge(ArrayList<LeagueContestData> list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder holder;

        if (convertView == null) {
            CustomSpinnerLayoutBinding customSpinnerLayoutBinding = DataBindingUtil.inflate(LayoutInflater
                    .from(parent.getContext()), R.layout.custom_spinner_layout, (ViewGroup) convertView, false);

            holder = new ViewHolder(customSpinnerLayoutBinding);
            holder.view = customSpinnerLayoutBinding.getRoot();
            holder.view.setTag(holder);
            customSpinnerLayoutBinding.executePendingBindings();
            customSpinnerLayoutBinding.customChannelType.setText(list.get(position).getName());

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return holder.view;
    }

    private static class ViewHolder {
        private View view;
        ViewHolder(CustomSpinnerLayoutBinding binding) {
            this.view = binding.getRoot();
        }
    }
}
