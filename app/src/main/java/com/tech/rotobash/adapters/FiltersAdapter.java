package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ListItemsFilterBinding;
import com.tech.rotobash.interfaces.FilterClickListener;
import com.tech.rotobash.model.SeriesData;

import java.util.List;

/**
 * @Module Name/Class		:	FiltersAdapter
 * @Author Name             :	Rohit Puri
 * @Date :	Jan 16th , 2018
 * @Purpose :	This is used to adapt matches filter item in recyclerview using binding related to Match screen
 */
public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.MyViewHolder> {

    private List<SeriesData> seriesDataList;
    private FilterClickListener mFilterClickListener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ListItemsFilterBinding mViewDataBinding;

        public MyViewHolder( ListItemsFilterBinding viewDataBinding) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ListItemsFilterBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public FiltersAdapter(List<SeriesData> seriesDataList, FilterClickListener filterClickListener) {
        this.seriesDataList = seriesDataList;
        mFilterClickListener = filterClickListener;

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
        viewDataBinding.tvItemName.setText(seriesDataList.get(position).getmSeriesModel().getSeriesShortName());
        viewDataBinding.tvItemName.setOnClickListener(view -> {
            mFilterClickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return seriesDataList.size();
    }
}