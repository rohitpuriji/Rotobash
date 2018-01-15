package com.tech.rotobash.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ItemMatchContestsBinding;
import com.tech.rotobash.model.MatchContestsData;

import java.util.List;

/**
 * Created by sachinarora on 12/1/18.
 */

public class MatchContestsAdapter extends RecyclerView.Adapter<MatchContestsAdapter.MyViewHolder> {


    private List<MatchContestsData> matchesDataList;
    private AppCompatActivity appCompatActivity;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemMatchContestsBinding mViewDataBinding;

        public MyViewHolder(ItemMatchContestsBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ItemMatchContestsBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public MatchContestsAdapter(AppCompatActivity appCompatActivity, List<MatchContestsData> matchesDataList) {
        this.matchesDataList = matchesDataList;
        this.appCompatActivity = appCompatActivity;

    }

    @Override
    public MatchContestsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemMatchContestsBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.item_match_contests, viewGroup, false);

        return new MatchContestsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MatchContestsData matchContestsData = matchesDataList.get(position);

        ItemMatchContestsBinding viewDataBinding = holder.getViewDataBinding();

        viewDataBinding.tvTotalPrize.setText(new StringBuilder().append(appCompatActivity.getString(R.string.Rs)).append(" ").append(matchContestsData.getMax_winning_amount()).toString());
        viewDataBinding.tvMaxPrize.setText(new StringBuilder().append(appCompatActivity.getString(R.string.Rs)).append(" ").append(matchContestsData.getMax_winning_amount()).toString());
        viewDataBinding.tvPay.setText(new StringBuilder().append(appCompatActivity.getString(R.string.Rs)).append(" ").append(matchContestsData.getEntry_fee()).toString());
        viewDataBinding.tvLeagueName.setText(matchContestsData.getLeague_name());
        viewDataBinding.tvTotalWinners.setText(new StringBuilder().append(matchContestsData.getNo_of_winners()).append(" ").append("WINNERS").toString());
        viewDataBinding.tvTeamsJoined.setText(new StringBuilder().append("3").append("/").append(matchContestsData.getMax_user()).append(" ").append("TEAMS").toString());

        setProgress(viewDataBinding, matchContestsData);

    }

    private void setProgress(ItemMatchContestsBinding viewDataBinding, MatchContestsData matchContestsData) {
        int progress, teamsJoined, maxTeams;
        teamsJoined = Integer.parseInt("3");
        maxTeams = Integer.parseInt(matchContestsData.getMax_user());

        progress = (teamsJoined / maxTeams) * 100;

        Log.e("progress is", progress + "");

        viewDataBinding.seekBar.setProgress(progress);

    }

    @Override
    public int getItemCount() {
        return matchesDataList.size();
    }
}
