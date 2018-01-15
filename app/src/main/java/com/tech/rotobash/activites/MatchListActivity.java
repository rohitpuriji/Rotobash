package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.Validations.ViewsVisibilites;
import com.tech.rotobash.ViewModel.MatchesViewModel;
import com.tech.rotobash.ViewModel.SeriesViewModel;
import com.tech.rotobash.adapters.FiltersAdapter;
import com.tech.rotobash.adapters.MatchesAdapter;
import com.tech.rotobash.databinding.ActivityMatchListBinding;
import com.tech.rotobash.interfaces.FilterClickListener;
import com.tech.rotobash.interfaces.MatchItemInterface;
import com.tech.rotobash.model.MatchesData;
import com.tech.rotobash.model.SeriesData;
import com.tech.rotobash.model.SeriesModel;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.Network;

import java.util.ArrayList;

import io.reactivex.Observable;
import rx.functions.Action1;

import static com.tech.rotobash.utils.AppConstant.sPleaseWait;

/**
 * Created by sachinarora on 11/1/18.
 */

public class MatchListActivity extends SidemenuActivity {

    private UserResponse mUserResponse;
    private ActivityMatchListBinding mMatchListActivityBinding;
    private int mMatchType = 2,mOffset=0;
    private MatchesAdapter mAdapter;
    private FiltersAdapter mFilterAdapter;
    private boolean mLoading = true;
    private int mPastVisiblesItems, mVisibleItemCount, mTotalItemCount;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutFilterManager;
    private ArrayList<MatchesData> mTempList = new ArrayList<>();
    private int lastSizeOfList = 0;
    private ArrayList<MatchesData> mMatchesList = new ArrayList<>();
    private ArrayList<SeriesData> mSeriesList = new ArrayList<>();
    private int mSeriesId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        setDataToParentClass(mUserResponse);

        mMatchListActivityBinding.includedContent.swipeContainerCurrent.setOnRefreshListener(() -> {
            refreshList();
        });

        mMatchListActivityBinding.imgMenu.setOnClickListener(v -> openCloseDrawer());

        mMatchListActivityBinding.includedContent.btnCurrent.setOnClickListener(view -> {
            ViewsVisibilites.showCurrentMatchesView(MatchListActivity.this,mMatchListActivityBinding);
            mMatchType = 2;
            refreshList();
        });

        mMatchListActivityBinding.includedContent.btnComing.setOnClickListener(view -> {
            ViewsVisibilites.showComingMatchesView(MatchListActivity.this,mMatchListActivityBinding);
            mMatchType = 1;
            refreshList();
        });

        mMatchListActivityBinding.includedContent.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    mVisibleItemCount = mLayoutManager.getChildCount();
                    mTotalItemCount = mLayoutManager.getItemCount();
                    mPastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (Network.isAvailable(MatchListActivity.this)) {
                        if (mLoading) {
                            if ((mVisibleItemCount + mPastVisiblesItems) >= mTotalItemCount) {
                                mLoading = false;
                                mOffset = mTempList.size();
                                loadMatches(mSeriesId+"",mMatchType+"",mOffset+"");
                            }
                        }
                    } else {
                        mLoading = true;
                    }
                }
            }
        });


        mMatchListActivityBinding.btnSelectMatches.setOnClickListener(view -> {
            handleFilterVisibility();
        });
    }

    private void handleFilterVisibility(){
        if (mSeriesList.size()>0){
            if (mMatchListActivityBinding.listViewFilter.getVisibility() != View.VISIBLE) {
                mMatchListActivityBinding.listViewFilter.setVisibility(View.VISIBLE);
                mMatchListActivityBinding.includedContent.rootLayout.setVisibility(View.GONE);
            }else{
                mMatchListActivityBinding.listViewFilter.setVisibility(View.GONE);
                mMatchListActivityBinding.includedContent.rootLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void refreshList(){
        mLoading = true;
        mPastVisiblesItems = 0;
        mVisibleItemCount = 0;
        mTotalItemCount = 0;
        lastSizeOfList = 0;
        mOffset = 0;
        if (mTempList.size()>0) {
            mTempList.clear();
        }

        if (mMatchesList.size()>0) {
            mMatchesList.clear();
        }

        loadMatches(mSeriesId + "", mMatchType + "", mOffset + "");

    }

    /**
     * @Module Name/Class		:	openCloseDrawer
     * @Author Name            :	Sachin Arora
     * @Date :	Jan 8rd , 2018
     * @Purpose :	This method will open close drawer
     */
    public void openCloseDrawer() {

        if (mBinding.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mBinding.drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            mBinding.drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    private void initDataVariables() {
        mUserResponse = getIntent().getExtras().getParcelable("UserResponse");
        mMatchListActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_list, mBinding.contentFrame, true);

        mMatchListActivityBinding.listViewFilter.setVisibility(View.GONE);

        ProgressDialog progressDoalog = new ProgressDialog(MatchListActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(sPleaseWait);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        setSupportActionBar(mMatchListActivityBinding.toolbar);

        setFilterRecyclerViewManager();
        setRecyclerViewManager();

        if (Network.isAvailable(MatchListActivity.this)) {
            getFilterData();
            if (mTempList.size()==0){
                loadMatches(mSeriesId+"",mMatchType+"",mOffset+"");
            }
        } else {
            Toast.makeText(MatchListActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }
    }

    private void setRecyclerViewManager(){
        mLayoutManager = new LinearLayoutManager(MatchListActivity.this);
        mMatchListActivityBinding.includedContent.recyclerView.setLayoutManager(mLayoutManager);
        mMatchListActivityBinding.includedContent.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setFilterRecyclerViewManager(){
        mLayoutFilterManager = new LinearLayoutManager(MatchListActivity.this);
        mMatchListActivityBinding.listViewFilter.setLayoutManager(mLayoutFilterManager);
        mMatchListActivityBinding.listViewFilter.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMatchListActivityBinding.listViewFilter.getContext(),
                DividerItemDecoration.VERTICAL);
        mMatchListActivityBinding.listViewFilter.addItemDecoration(dividerItemDecoration);
    }

    /**
     * @Module Name/Class		:	loadMatches
     * @Author Name             :	Rohit Puri
     * @Date                    :	Jan 11th , 2018
     * @Purpose                 :	This method loads the current or upcoming matches based on type from api
     */
    private void loadMatches(String aSeriesId,String aMatchType,String aOffset) {

        MatchesViewModel mMatchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);

        mMatchesViewModel.getMatches(aSeriesId,mUserResponse, aMatchType, aOffset)
                .observe(this, matchesResponse -> {
                    if (matchesResponse.getStatus().equalsIgnoreCase("success")) {
                        if (matchesResponse.getMatchModel().size() > 0) {
                            mMatchesList = matchesResponse.getMatchModel();

                            mTempList.addAll(mMatchesList);

                            if (mMatchesList.size() > 0) {
                                mLoading = true;
                            }

                            mAdapter = new MatchesAdapter(mTempList, pos -> {
                                if (mMatchType == 2){
                                    moveScreen(pos);
                                }
                            });
                            mMatchListActivityBinding.includedContent.recyclerView.setAdapter(mAdapter);

                            lastSizeOfList = mTempList.size() - lastSizeOfList;

                            if (mOffset > 0 && lastSizeOfList > 0) {
                                mMatchListActivityBinding.includedContent.recyclerView.scrollToPosition(lastSizeOfList);
                            }

                            Log.e("size",mTempList.size()+"");

                            if (mMatchListActivityBinding.includedContent.swipeContainerCurrent.isRefreshing()){
                                mMatchListActivityBinding.includedContent.swipeContainerCurrent.setRefreshing(false);
                            }
                        }
                        else {
                            Toast.makeText(MatchListActivity.this, matchesResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (mSeriesId!=0){
                            if (mLoading){
                                mTempList.clear();
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        Toast.makeText(MatchListActivity.this, matchesResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void moveScreen(int pos){

    }

    /**
     * @Module Name/Class		:	getFilterData
     * @Author Name             :	Rohit Puri
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method loads the filters from api
     */
    private void getFilterData() {

        SeriesViewModel mSeriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

        mSeriesViewModel.getSeries(mUserResponse)
                .observe(this, seriesResponse -> {
                    if (seriesResponse.getStatus().equalsIgnoreCase("success")) {
                        if (seriesResponse.getSeriesModel().size() > 0) {
                            mSeriesList = seriesResponse.getSeriesModel();

                            mFilterAdapter = new FiltersAdapter(mSeriesList, pos -> {
                                mSeriesId = Integer.parseInt(mSeriesList.get(pos).getmSeriesModel().getSeriesId());
                                mMatchListActivityBinding.btnSelectMatches.setText(mSeriesList.get(pos).getmSeriesModel().getSeriesShortName());
                                refreshList();
                                mMatchListActivityBinding.listViewFilter.setVisibility(View.GONE);
                                mMatchListActivityBinding.includedContent.rootLayout.setVisibility(View.VISIBLE);
                            });

                            mMatchListActivityBinding.listViewFilter.setAdapter(mFilterAdapter);
                        }
                        else {
                            Toast.makeText(MatchListActivity.this, seriesResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MatchListActivity.this, seriesResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mMatchListActivityBinding.listViewFilter.getVisibility() == View.VISIBLE) {
            mMatchListActivityBinding.listViewFilter.setVisibility(View.GONE);
            mMatchListActivityBinding.includedContent.rootLayout.setVisibility(View.VISIBLE);
        }else{
            finish();
        }
    }
}
