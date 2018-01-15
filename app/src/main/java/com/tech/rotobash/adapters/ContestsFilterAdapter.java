package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ListItemsFilterBinding;
import com.tech.rotobash.interfaces.MatchItemInterface;
import com.tech.rotobash.model.LeagueContestData;

import java.util.ArrayList;

public class ContestsFilterAdapter extends RecyclerView.Adapter<ContestsFilterAdapter.MyViewHolder> {

    private ArrayList<LeagueContestData> leaguesArrayList;
    private String mContests;
    private MatchItemInterface mMatchInterface;

    public ContestsFilterAdapter(String mWhichFilter, ArrayList<LeagueContestData> leaguesList, MatchItemInterface matchItemInterface) {

        mContests = mWhichFilter;
        leaguesArrayList = leaguesList;
        mMatchInterface = matchItemInterface;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ListItemsFilterBinding mViewDataBinding;

        public MyViewHolder(ListItemsFilterBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ListItemsFilterBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ListItemsFilterBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.list_items_filter, viewGroup, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ListItemsFilterBinding viewDataBinding = holder.getViewDataBinding();

        if (mContests.equalsIgnoreCase("Select League")) {
            viewDataBinding.tvItemName.setText(leaguesArrayList.get(position).getName());
            viewDataBinding.tvItemName.setOnClickListener(v -> {
                mMatchInterface.onItemClick(position);

            });
        }
    }

    @Override
    public int getItemCount() {

        if (mContests.equalsIgnoreCase("Select League"))
            return leaguesArrayList.size();

        return leaguesArrayList.size();

    }
}