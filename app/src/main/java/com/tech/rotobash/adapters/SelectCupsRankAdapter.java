package com.tech.rotobash.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.activites.SelectTeamActivity;
import com.tech.rotobash.databinding.CustomRanksLayoutBinding;
import com.tech.rotobash.model.ContestRankData;

import java.util.ArrayList;

/**
 * Created by sombirbisht on 18/1/18.
 */

public class SelectCupsRankAdapter extends RecyclerView.Adapter<SelectCupsRankAdapter.MyViewHolder> {
    private ArrayList<ContestRankData> mContestRankData;
    private AppCompatActivity appCompatActivity;

    public SelectCupsRankAdapter(SelectTeamActivity appCompatActivity, ArrayList<ContestRankData> mContestRanksList) {
        this.mContestRankData = mContestRanksList;
        this.appCompatActivity = appCompatActivity;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CustomRanksLayoutBinding mViewDataBinding;

        public MyViewHolder(CustomRanksLayoutBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public CustomRanksLayoutBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CustomRanksLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.custom_ranks_layout, viewGroup, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ContestRankData contestRankData = mContestRankData.get(position);

        CustomRanksLayoutBinding customRanksLayoutBinding = holder.getViewDataBinding();

        if (contestRankData.getRanks() != null)
            customRanksLayoutBinding.tvRanks.setText(contestRankData.getRanks());
        if (contestRankData.getNoOfWinners() != null)
            customRanksLayoutBinding.tvNoOfWinners.setText(contestRankData.getNoOfWinners());
        if (contestRankData.getPrize() != null)
            customRanksLayoutBinding.tvPrize.setText(contestRankData.getPrize());
    }

    @Override
    public int getItemCount() {
        return mContestRankData.size();
    }
}
