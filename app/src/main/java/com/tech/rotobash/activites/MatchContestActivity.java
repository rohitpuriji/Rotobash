package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.Validations.ViewsVisibilites;
import com.tech.rotobash.ViewModel.MatchContestsViewModel;
import com.tech.rotobash.ViewModel.SelectLeagueViewModel;
import com.tech.rotobash.adapters.ContestsFilterAdapter;
import com.tech.rotobash.adapters.MatchContestsAdapter;
import com.tech.rotobash.databinding.ActivityMatchContestBinding;
import com.tech.rotobash.model.LeagueContestData;
import com.tech.rotobash.model.MatchContestsData;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.Network;

import java.util.ArrayList;

import static com.tech.rotobash.utils.AppConstant.sPleaseWait;

/**
 * Created by sachinarora on 12/1/18.
 */

public class MatchContestActivity extends SidemenuActivity {

    private ActivityMatchContestBinding mMatchContestActivityBinding;
    private int aOffset = 0;
    private int aLimit = 10;
    private int league_id = 0;
    private String matchId;
    private MatchContestsAdapter mAdapter;
    private ArrayList<MatchContestsData> mTempArrayListActiveContestData;
    private ArrayList<MatchContestsData> mTempArrayListInActiveContestData;
    private String status;
    private int mPastVisiblesItems, mVisibleItemCount, mTotalItemCount;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutFilterManager;
    private boolean mLoading = true;
    private ProgressDialog progressDialog;
    private ArrayList<LeagueContestData> leaguesList;
    private ArrayList<String> payArrayList;
    private ContestsFilterAdapter mFilterAdapter;
    private String mWhichFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);

        mMatchContestActivityBinding.btnCurrent.setOnClickListener(v -> {
            mLoading = true;
            status = getString(R.string.active);
            ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);
            setRecyclerView(status);
        });

        mMatchContestActivityBinding.btnComing.setOnClickListener(v -> {
            mLoading = true;
            status = getString(R.string.inActive);
            ViewsVisibilites.showInActiveMatchView(this, mMatchContestActivityBinding);
            setRecyclerView(status);

        });

        mMatchContestActivityBinding.tvSelectLeague.setOnClickListener(v -> {

            if (mMatchContestActivityBinding.recyclerViewOther.getVisibility() == View.GONE) {

                if (Network.isAvailable(this)) {
                    if (leaguesList.size() > 0) {
                        mWhichFilter = getString(R.string.txt_select_league);
                        mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.VISIBLE);
                        mMatchContestActivityBinding.rootLayout.setVisibility(View.GONE);
                        setFilterAdapter();
                    }

                } else
                    Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();

            } else {
                mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.GONE);
                mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);
            }
        });

        mMatchContestActivityBinding.tvSelectPay.setOnClickListener(v -> {


        });

        mMatchContestActivityBinding.imgBack.setOnClickListener(v -> onBackPressed());

        mMatchContestActivityBinding.imgMenu.setOnClickListener(v -> openCloseDrawer());

        mMatchContestActivityBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    mVisibleItemCount = mLayoutManager.getChildCount();
                    mTotalItemCount = mLayoutManager.getItemCount();
                    mPastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (Network.isAvailable(MatchContestActivity.this)) {
                        if (mLoading) {
                            if ((mVisibleItemCount + mPastVisiblesItems) >= mTotalItemCount) {
                                mLoading = false;
                                loadMatchContest();
                            }
                        }
                    } else {
                        mLoading = true;
                    }
                }
            }
        });

        mMatchContestActivityBinding.swipeContainerCurrent.setOnRefreshListener(() -> {
            // refreshList();
            if (mMatchContestActivityBinding.swipeContainerCurrent.isRefreshing()) {
                mMatchContestActivityBinding.swipeContainerCurrent.setRefreshing(false);
            }
        });
    }

    private void setFilterAdapter() {
        mFilterAdapter = new ContestsFilterAdapter(mWhichFilter, leaguesList, pos -> {
            league_id = Integer.parseInt(leaguesList.get(pos).getId());
            aOffset = 0;
            mTempArrayListInActiveContestData.clear();
            mTempArrayListActiveContestData.clear();
            mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.GONE);
            mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);
            mMatchContestActivityBinding.recyclerView.setVisibility(View.VISIBLE);
            mAdapter = null;
            loadMatchContest();

        });
        mMatchContestActivityBinding.recyclerViewOther.setAdapter(mFilterAdapter);
    }

    private void getLeague() {
        progressDialog.show();

        SelectLeagueViewModel selectLeagueViewModel = ViewModelProviders.of(this).get(SelectLeagueViewModel.class);

        selectLeagueViewModel.getLeagues(progressDialog, mUserResponse)
                .observe(this, leaguesResponse -> {
                    if (leaguesResponse.getStatus().equalsIgnoreCase("success")) {
                        if (leaguesResponse.getMatchModel().size() > 0) {
                            leaguesList = leaguesResponse.getMatchModel();

                        } else {
                            Toast.makeText(MatchContestActivity.this, leaguesResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(MatchContestActivity.this, leaguesResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void refreshList() {
        ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);
        status = getString(R.string.active);
    }

    private void initDataVariables() {
        mMatchContestActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_contest, mBinding.contentFrame, true);
        matchId = "52";
        mUserResponse = getIntent().getExtras().getParcelable("UserResponse");
        mTempArrayListActiveContestData = new ArrayList<>();
        mTempArrayListInActiveContestData = new ArrayList<>();
        leaguesList = new ArrayList<>();
        payArrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage(sPleaseWait);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        status = getString(R.string.active);

        setCurrentRecyclerViewManager();
        setFilterRecyclerViewManager();

        setSelectPay();

        if (Network.isAvailable(this)) {
            getLeague();
            loadMatchContest();
        } else {
            Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }

    }

    private void setSelectPay() {
        ArrayList<String> mPayList = new ArrayList<>();
        mPayList.add("0");
        mPayList.add("<100");
        mPayList.add("<500");
        mPayList.add("<1000");
        mPayList.add("<2000");
        payArrayList.addAll(mPayList);
    }

    private void setFilterRecyclerViewManager() {
        mLayoutFilterManager = new LinearLayoutManager(this);
        mMatchContestActivityBinding.recyclerViewOther.setLayoutManager(mLayoutFilterManager);
        mMatchContestActivityBinding.recyclerViewOther.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMatchContestActivityBinding.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mMatchContestActivityBinding.recyclerViewOther.addItemDecoration(dividerItemDecoration);
    }

    private void loadMatchContest() {

        MatchContestsViewModel mMatchContestViewModel = ViewModelProviders.of(this).get(MatchContestsViewModel.class);

        mMatchContestViewModel.getMatchContests(progressDialog, mUserResponse, matchId, String.valueOf(league_id), String.valueOf(aOffset), String.valueOf(aLimit))
                .observe(this, matchContestsResponse -> {
                    if (matchContestsResponse.getStatus().equalsIgnoreCase("success")) {
                        if (matchContestsResponse.getMatchModel().size() > 0) {
                            Log.e("size is", matchContestsResponse.getMatchModel().size() + "");

                            mLoading = true;
                            aOffset = aOffset + matchContestsResponse.getMatchModel().size();
                            setActiveOrInActive(matchContestsResponse.getMatchModel());

                        } else {
                            Toast.makeText(MatchContestActivity.this, matchContestsResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(MatchContestActivity.this, matchContestsResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setActiveOrInActive(ArrayList<MatchContestsData> matchModel) {

        ArrayList<MatchContestsData> activeList = new ArrayList<>();
        ArrayList<MatchContestsData> inActiveList = new ArrayList<>();

        for (int i = 0; i < matchModel.size(); i++) {

            if (Integer.parseInt(matchModel.get(i).getTotal_users()) >= Integer.parseInt(matchModel.get(i).getMin_user())) {

                MatchContestsData matchContestsData = matchModel.get(i);
                activeList.add(matchContestsData);

            } else {
                MatchContestsData matchContestsData = matchModel.get(i);
                inActiveList.add(matchContestsData);
            }
        }

        mTempArrayListActiveContestData.addAll(activeList);
        mTempArrayListInActiveContestData.addAll(inActiveList);

        if (mAdapter == null)
            setRecyclerView(status);
        else
            mAdapter.notifyDataSetChanged();

    }

    private void setRecyclerView(String status) {

        if (status.equalsIgnoreCase(getString(R.string.active))) {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListActiveContestData);
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListInActiveContestData);
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        }
    }

    private void setCurrentRecyclerViewManager() {
        mLayoutManager = new LinearLayoutManager(this);
        mMatchContestActivityBinding.recyclerView.setLayoutManager(mLayoutManager);
        mMatchContestActivityBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onBackPressed() {
        if (mMatchContestActivityBinding.recyclerViewOther.getVisibility() == View.VISIBLE) {
            mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.GONE);
            mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);

        } else
            super.onBackPressed();
    }
}
