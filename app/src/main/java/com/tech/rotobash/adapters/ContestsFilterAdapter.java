package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ListItemsFilterBinding;
import com.tech.rotobash.interfaces.MatchItemInterface;
import com.tech.rotobash.model.LeagueContestData;
import com.tech.rotobash.model.MatchesData;

import java.util.ArrayList;

public class ContestsFilterAdapter extends RecyclerView.Adapter<ContestsFilterAdapter.MyViewHolder> {

    private ArrayList<LeagueContestData> leaguesArrayList;
    private ArrayList<String> payArrayList;
    private String mContestsFilter;
    private MatchItemInterface mMatchInterface;
    private ArrayList<MatchesData> matchDataList;

    public ContestsFilterAdapter(String mWhichFilter, ArrayList<LeagueContestData> leaguesList, ArrayList<String> payList, ArrayList<MatchesData> matchesDataArrayList, MatchItemInterface matchItemInterface) {

        mContestsFilter = mWhichFilter;
        leaguesArrayList = leaguesList;
        mMatchInterface = matchItemInterface;
        payArrayList = payList;
        matchDataList = matchesDataArrayList;
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

        if (mContestsFilter.equalsIgnoreCase("Select League")) {
            viewDataBinding.tvItemName.setText(leaguesArrayList.get(position).getName());
            viewDataBinding.tvItemName.setOnClickListener(v -> mMatchInterface.onItemClick(position));

        } else if (mContestsFilter.equalsIgnoreCase("Select Pay")) {
            viewDataBinding.tvItemName.setText(payArrayList.get(position));
            viewDataBinding.tvItemName.setOnClickListener(v -> mMatchInterface.onItemClick(position));

        } else {
            viewDataBinding.tvItemName.setText(new StringBuilder().append(matchDataList.get(position).getTeam1Name()).append(" VS ").append(matchDataList.get(position).getTeam2Name()).toString());
            viewDataBinding.tvItemName.setOnClickListener(v -> mMatchInterface.onItemClick(position));
        }
    }

    @Override
    public int getItemCount() {

        if (mContestsFilter.equalsIgnoreCase("Select League"))
            return leaguesArrayList.size();
        else if (mContestsFilter.equalsIgnoreCase("Select Pay"))
            return payArrayList.size();

        return matchDataList.size();

    }
}