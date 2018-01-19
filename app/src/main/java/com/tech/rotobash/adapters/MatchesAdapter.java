package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ItemMatchesBinding;
import com.tech.rotobash.interfaces.MatchItemInterface;
import com.tech.rotobash.model.MatchesData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.tech.rotobash.utils.AppUtils.getCountdownText;
import static com.tech.rotobash.utils.AppUtils.getDateFormat;

/**
 * @Module Name/Class		:	FiltersAdapter
 * @Author Name             :	Rohit Puri
 * @Date :	Jan 16th , 2018
 * @Purpose :	This is used to show the matches in recyclerview using binding related to match screen
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {

    private List<MatchesData> matchesDataList;
    private MatchItemInterface mMatchItemInterface;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemMatchesBinding mViewDataBinding;
        private CountDownTimer timer;

        MyViewHolder(ItemMatchesBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        ItemMatchesBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public MatchesAdapter(List<MatchesData> matchesDataList, MatchItemInterface matchItemInterface) {
        this.matchesDataList = matchesDataList;
        mMatchItemInterface = matchItemInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemMatchesBinding binding = DataBindingUtil.inflate(LayoutInflater
                .from(viewGroup.getContext()), R.layout.item_matches, viewGroup, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ItemMatchesBinding viewDataBinding = holder.getViewDataBinding();
        viewDataBinding.tvMatchName.setText(matchesDataList.get(position).getSeriesShortName());
        viewDataBinding.tvTeam1Name.setText(matchesDataList.get(position).getTeam1Name());
        viewDataBinding.tvTeam2Name.setText(matchesDataList.get(position).getTeam2Name());
        viewDataBinding.cardView.setOnClickListener(view -> mMatchItemInterface.onItemClick(position));

        if (holder.timer != null) {
            holder.timer.cancel();
        }

        holder.timer = new CountDownTimer(getDateFormat(matchesDataList.get(position).getMatchStartDate()).getTime(), 1000) {
            public void onTick(long millisUntilFinished) {
                viewDataBinding.tvTimeLeft.setText(getCountdownText(getDateFormat(matchesDataList.get(position).getMatchStartDate())));
            }
            public void onFinish() {
                viewDataBinding.tvTimeLeft.setText("00:00:00");
            }
        }.start();

    }

    @Override
    public int getItemCount() {
        return matchesDataList.size();
    }


}