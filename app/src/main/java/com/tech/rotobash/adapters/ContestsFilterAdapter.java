package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ListItemMatchesFilterBinding;
import com.tech.rotobash.databinding.ListItemsFilterBinding;
import com.tech.rotobash.interfaces.MatchItemInterface;
import com.tech.rotobash.model.LeagueContestData;
import com.tech.rotobash.model.MatchesData;
import com.tech.rotobash.utils.AppUtils;

import java.util.ArrayList;

public class ContestsFilterAdapter extends RecyclerView.Adapter<ContestsFilterAdapter.MyViewHolder> {

    private MatchItemInterface mMatchInterface;
    private ArrayList<MatchesData> matchDataList;

    public ContestsFilterAdapter(ArrayList<MatchesData> matchesDataArrayList, MatchItemInterface matchItemInterface) {

        mMatchInterface = matchItemInterface;
        matchDataList = matchesDataArrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ListItemMatchesFilterBinding mViewDataBinding;

        public MyViewHolder(ListItemMatchesFilterBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ListItemMatchesFilterBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ListItemMatchesFilterBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.list_item_matches_filter, viewGroup, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ListItemMatchesFilterBinding viewDataBinding = holder.getViewDataBinding();

        viewDataBinding.tvMatchName.setText(new StringBuilder().append(matchDataList.get(position).getTeam1Name()).append(" VS ").append(matchDataList.get(position).getTeam2Name()).toString());
        viewDataBinding.tvMatchSeries.setText(matchDataList.get(position).getSeriesShortName());
        viewDataBinding.tvTimeLeft.setText(AppUtils.getDateFormatYear(matchDataList.get(position).getMatchStartDate()));
        viewDataBinding.itemConstraint.setOnClickListener(v -> mMatchInterface.onItemClick(position));

    }

    @Override
    public int getItemCount() {

        return matchDataList.size();

    }
}