package com.tech.rotobash.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ListItemsFilterBinding;
import com.tech.rotobash.model.SeriesData;

import java.util.List;

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.MyViewHolder> {

    private List<SeriesData> seriesDataList;

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

    public FiltersAdapter(List<SeriesData> seriesDataList) {
        this.seriesDataList = seriesDataList;

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
    }

    @Override
    public int getItemCount() {
        return seriesDataList.size();
    }
}