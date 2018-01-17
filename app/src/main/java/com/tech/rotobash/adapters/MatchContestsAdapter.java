package com.tech.rotobash.adapters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.activites.SelectTeamActivity;
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
        if (matchContestsData.getLeague_name().equalsIgnoreCase("max bash")){
            viewDataBinding.tvLeagueName.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_maxbash));
        }else if (matchContestsData.getLeague_name().equalsIgnoreCase("max boundary")){
            viewDataBinding.tvLeagueName.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_max_bound));
        }else if(matchContestsData.getLeague_name().equalsIgnoreCase("max score")){
            viewDataBinding.tvLeagueName.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_max_score));
        }else if(matchContestsData.getLeague_name().equalsIgnoreCase("fast score")){
            viewDataBinding.tvLeagueName.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_fast_score));
        }else{
            viewDataBinding.tvLeagueName.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_default));
        }
        viewDataBinding.tvTotalWinners.setText(new StringBuilder().append(matchContestsData.getNo_of_winners()).append(" ").append("WINNERS").toString());
        viewDataBinding.tvTeamsJoined.setText(new StringBuilder().append(matchContestsData.getTotal_users()).append("/").append(matchContestsData.getMax_user()).append(" ").append("TEAMS").toString());
        viewDataBinding.seekBar.setProgress(getProgress(matchContestsData));

        // to join contest
        viewDataBinding.tvJoin.setOnClickListener(v -> {
            Intent selectTeamIntent = new Intent(appCompatActivity, SelectTeamActivity.class);
            appCompatActivity.startActivity(selectTeamIntent);
        });

        if (Integer.parseInt(matchContestsData.getTotal_users()) >= Integer.parseInt(matchContestsData.getMin_user())) {
            viewDataBinding.cardView.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_light_blue));
        }else{
            viewDataBinding.cardView.setBackgroundColor(appCompatActivity.getResources().getColor(R.color.color_white));
        }
    }

    private int getProgress(MatchContestsData matchContestsData) {

        int progress;
        int total_users;
        int max_users;

        total_users = Integer.parseInt(matchContestsData.getTotal_users());
        max_users = Integer.parseInt(matchContestsData.getMax_user());

        progress = (total_users * 100) / max_users;

        Log.e("progress", progress + "");

        return progress;

    }

    @Override
    public int getItemCount() {
        return matchesDataList.size();
    }
}
