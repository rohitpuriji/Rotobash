package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ItemMatchesBinding;
import com.tech.rotobash.model.MatchesData;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {

    private List<MatchesData> matchesDataList;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemMatchesBinding mViewDataBinding;

        public MyViewHolder( ItemMatchesBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ItemMatchesBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public MatchesAdapter(List<MatchesData> matchesDataList) {
        this.matchesDataList = matchesDataList;

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
        viewDataBinding.tvMatchName.setText(matchesDataList.get(position).getMatchName());
        viewDataBinding.tvTeam1Name.setText(matchesDataList.get(position).getTeam1Name());
        viewDataBinding.tvTeam2Name.setText(matchesDataList.get(position).getTeam2Name());
    }

    @Override
    public int getItemCount() {
        return matchesDataList.size();
    }
}