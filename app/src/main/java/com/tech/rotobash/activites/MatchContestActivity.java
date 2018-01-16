package com.tech.rotobash.activites;

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
import com.tech.rotobash.model.MatchesData;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.AppUtils;
import com.tech.rotobash.utils.Network;

import java.util.ArrayList;

import static com.tech.rotobash.utils.AppConstant.sMatchListKey;
import static com.tech.rotobash.utils.AppConstant.sPositionKey;
import static com.tech.rotobash.utils.AppConstant.sSuccess;
import static com.tech.rotobash.utils.AppConstant.sUserResponseKey;

/**
 * @Module Name/Class		:	MatchContestActivity
 * @Author Name             :	Sachin Arora
 * @Date                    :	Jan 15th , 2018
 * @Purpose                 :	This is used to show the contest of selected match
 */
public class MatchContestActivity extends SidemenuActivity {

    private ActivityMatchContestBinding mMatchContestActivityBinding;
    private int aOffset = 0;
    private int league_id = 0;
    private int mPastVisiblesItems, mVisibleItemCount, mTotalItemCount;
    private String matchId;
    private String price = null;
    private String mWhichFilter;
    private String status;
    private boolean mLoading = true;
    private MatchContestsAdapter mAdapter;
    private ArrayList<MatchContestsData> mTempArrayListActiveContestData;
    private ArrayList<MatchContestsData> mTempArrayListInActiveContestData;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LeagueContestData> leaguesList;
    private ArrayList<String> payArrayList;
    private ArrayList<MatchesData> matchArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);

        mMatchContestActivityBinding.btnActive.setOnClickListener(v -> {
            mLoading = true;
            status = getString(R.string.active);
            ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);
            setRecyclerView(status);
        });

        mMatchContestActivityBinding.btnInActive.setOnClickListener(v -> {
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
            mWhichFilter = getString(R.string.txt_select_pay);
            mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.VISIBLE);
            mMatchContestActivityBinding.rootLayout.setVisibility(View.GONE);

            setFilterAdapter();

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

        mMatchContestActivityBinding.llMatchName.setOnClickListener(v -> {
            mWhichFilter = getString(R.string.txt_match_name);
            mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.VISIBLE);
            mMatchContestActivityBinding.rootLayout.setVisibility(View.GONE);

            setFilterAdapter();

        });

        // onclick for refreshing league and pay
        mMatchContestActivityBinding.imgRefresh.setOnClickListener(view -> {
            if (Network.isAvailable(this)) {
                Log.e("TAG", "refresh");

                refreshToDefaultValues();
            }
        });
    }

    private void refreshToDefaultValues() {
        league_id = 0;
        price = null;

        mMatchContestActivityBinding.tvSelectPay.setText(getString(R.string.txt_select_pay));
        mMatchContestActivityBinding.tvSelectLeague.setText(getString(R.string.txt_select_league));

        // method to clear list and adapter
        clearListAndAdapter();
    }

    private void setFilterAdapter() {
        ContestsFilterAdapter mFilterAdapter = new ContestsFilterAdapter(mWhichFilter, leaguesList, payArrayList, matchArrayList, pos -> {

            if (mWhichFilter.equalsIgnoreCase(getString(R.string.txt_select_league))) {
                league_id = Integer.parseInt(leaguesList.get(pos).getId());
                mMatchContestActivityBinding.tvSelectLeague.setText(leaguesList.get(pos).getName());
            } else if (mWhichFilter.equalsIgnoreCase(getString(R.string.txt_select_pay))) {

                switch (pos) {
                    case 0:
                        price = "0";
                        break;
                    case 1:
                        price = "100";
                        break;
                    case 2:
                        price = "500";
                        break;
                    case 3:
                        price = "1000";
                        break;
                    case 4:
                        price = "2000";
                        break;
                }
                mMatchContestActivityBinding.tvSelectPay.setText(price);

            } else {
                matchId = matchArrayList.get(pos).getMatchId();
                mMatchContestActivityBinding.tvMatchName.setText(new StringBuilder().append(matchArrayList.get(pos).getTeam1Name()).append(" VS ").append(matchArrayList.get(pos).getTeam2Name()).toString());

            }

            // method to clear list and adapter
            clearListAndAdapter();

        });
        mMatchContestActivityBinding.recyclerViewOther.setAdapter(mFilterAdapter);
    }

    /**
     * @Module Name/Class		:	clearListAndAdapter
     * @Author Name             :	Sombir Bisht
     * @Date                    :	Jan 16th , 2018
     * @Purpose                 :	This method is used to notify adapter and set offset to 0.
     */
    private void clearListAndAdapter() {

        aOffset = 0;
        mTempArrayListInActiveContestData.clear();
        mTempArrayListActiveContestData.clear();
        mMatchContestActivityBinding.recyclerViewOther.setVisibility(View.GONE);
        mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);
        mMatchContestActivityBinding.recyclerView.setVisibility(View.VISIBLE);


        mAdapter.notifyDataSetChanged();
        mAdapter = null;
        loadMatchContest();
    }

    private void getLeague() {

        SelectLeagueViewModel selectLeagueViewModel = ViewModelProviders.of(this).get(SelectLeagueViewModel.class);

        selectLeagueViewModel
                .getLeagues(mUserResponse)
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

    /**
     * @Module Name/Class		:	initDataVariables
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is using for initialization of base data
     */
    private void initDataVariables() {
        mMatchContestActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_contest, mBinding.contentFrame, true);
        matchArrayList = getIntent().getParcelableArrayListExtra(sMatchListKey);
        mUserResponse = getIntent().getExtras().getParcelable(sUserResponseKey);
        int matchPositionSelected = getIntent().getIntExtra(sPositionKey, -1);

        matchId = matchArrayList.get(matchPositionSelected).getMatchId();

        mMatchContestActivityBinding.btnSelectMatches.setText(AppUtils.getDateFormatYear(matchArrayList.get(matchPositionSelected).getMatchStartDate()));

        mTempArrayListActiveContestData = new ArrayList<>();
        mTempArrayListInActiveContestData = new ArrayList<>();
        leaguesList = new ArrayList<>();
        payArrayList = new ArrayList<>();

        status = getString(R.string.active);

        setContestRecyclerViewManager();
        setFilterRecyclerViewManager();

        mMatchContestActivityBinding.tvMatchName.setText(new StringBuilder().append(matchArrayList.get(matchPositionSelected).getTeam1Name()).append(" VS ").append(matchArrayList.get(matchPositionSelected).getTeam2Name()).toString());

        setSelectPay();

        if (Network.isAvailable(this)) {
            getLeague();
            loadMatchContest();
        } else {
            Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * @Module Name/Class		:	setSelectPay
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is used to create pay filter arraylist
     */
    private void setSelectPay() {
        ArrayList<String> mPayList = new ArrayList<>();
        mPayList.add("0");
        mPayList.add("<100");
        mPayList.add("<500");
        mPayList.add("<1000");
        mPayList.add("<2000");
        payArrayList.addAll(mPayList);
    }

    /**
     * @Module Name/Class		:	setFilterRecyclerViewManager
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is used to set layout manager for league filter recycler view
     */
    private void setFilterRecyclerViewManager() {
        RecyclerView.LayoutManager mLayoutFilterManager = new LinearLayoutManager(this);
        mMatchContestActivityBinding.recyclerViewOther.setLayoutManager(mLayoutFilterManager);
        mMatchContestActivityBinding.recyclerViewOther.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMatchContestActivityBinding.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mMatchContestActivityBinding.recyclerViewOther.addItemDecoration(dividerItemDecoration);
    }

    /**
     * @Module Name/Class		:	loadMatchContest
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is used to fetch the contest list and observe data
     */
    private void loadMatchContest() {

        MatchContestsViewModel mMatchContestViewModel = ViewModelProviders.of(this).get(MatchContestsViewModel.class);

        int aLimit = 10;
        mMatchContestViewModel
                .getMatchContests(mUserResponse, matchId, String.valueOf(league_id), price, String.valueOf(aOffset), String.valueOf(aLimit))
                .observe(this, matchContestsResponse -> {
                    if (matchContestsResponse.getStatus().equalsIgnoreCase(sSuccess)) {
                        if (matchContestsResponse.getContestModel().size() > 0) {
                            Log.e("size is", matchContestsResponse.getContestModel().size() + "");
                            mLoading = true;
                            aOffset = aOffset + matchContestsResponse.getContestModel().size();
                            setActiveOrInActive(matchContestsResponse.getContestModel());
                        } else {
                            Toast.makeText(MatchContestActivity.this, matchContestsResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e("failure", "***");
                        Toast.makeText(MatchContestActivity.this, matchContestsResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * @Module Name/Class		:	setActiveOrInActive
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is used to set Active and InActive contest list
     */
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

    /**
     * @Module Name/Class		:	setRecyclerView
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is used to set recycler view based on active or inactive status
     */
    private void setRecyclerView(String status) {

        if (status.equalsIgnoreCase(getString(R.string.active))) {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListActiveContestData);
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListInActiveContestData);
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        }
    }


    /**
     * @Module Name/Class		:	setContestRecyclerViewManager
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 15th , 2018
     * @Purpose                 :	This method is used to set recycler view manager for contest recycler view
     */
    private void setContestRecyclerViewManager() {
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