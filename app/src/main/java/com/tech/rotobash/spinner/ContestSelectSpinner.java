package com.tech.rotobash.spinner;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.CustomSpinnerLayoutBinding;
import com.tech.rotobash.model.LeagueContestData;

import java.util.ArrayList;


public class ContestSelectSpinner extends BaseAdapter  {
    LayoutInflater inflater = null;
    ArrayList<LeagueContestData> list;
    Context context;

    public ContestSelectSpinner(Context context, ArrayList<LeagueContestData> list) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("name:", list.get(position).getName());
        PlanetViewHolder holder;

        if (convertView == null) {
            CustomSpinnerLayoutBinding customSpinnerLayoutBinding = DataBindingUtil.inflate(LayoutInflater
                    .from(parent.getContext()), R.layout.custom_spinner_layout, (ViewGroup) convertView, false);

            holder = new PlanetViewHolder(customSpinnerLayoutBinding);
            holder.view = customSpinnerLayoutBinding.getRoot();
            holder.view.setTag(holder);
            customSpinnerLayoutBinding.executePendingBindings();
            customSpinnerLayoutBinding.customChannelType.setText(list.get(position).getName());

        } else {

            holder = (PlanetViewHolder) convertView.getTag();
        }
        return holder.binding.view;
    }

    private static class PlanetViewHolder {
        private View view;
        private PlanetViewHolder binding;

        PlanetViewHolder(CustomSpinnerLayoutBinding binding) {
            this.view = binding.getRoot();
        }
    }
}
