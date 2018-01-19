package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.Validations.ViewsVisibilites;
import com.tech.rotobash.ViewModel.MatchContestsViewModel;
import com.tech.rotobash.ViewModel.SelectLeagueViewModel;
import com.tech.rotobash.adapters.ContestsFilterAdapter;
import com.tech.rotobash.adapters.MatchContestsAdapter;
import com.tech.rotobash.adapters.NothingSelectedSpinnerAdapter;
import com.tech.rotobash.databinding.ActivityMatchContestBinding;
import com.tech.rotobash.model.LeagueContestData;
import com.tech.rotobash.model.MatchContestsData;
import com.tech.rotobash.model.MatchesData;
import com.tech.rotobash.adapters.ChannelSpinnerForLeauge;
import com.tech.rotobash.adapters.ChannelSpinnerForPay;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.AppUtils;
import com.tech.rotobash.utils.Network;

import java.util.ArrayList;

import static com.tech.rotobash.utils.AppConstant.sMatchListKey;
import static com.tech.rotobash.utils.AppConstant.sPleaseWait;
import static com.tech.rotobash.utils.AppConstant.sPositionKey;
import static com.tech.rotobash.utils.AppConstant.sSuccess;
import static com.tech.rotobash.utils.AppConstant.sUserResponseKey;

/**
 * @Module Name/Class		:	MatchContestActivity
 * @Author Name             :	Sachin Arora
 * @Date :	Jan 15th , 2018
 * @Purpose :	This is used to show the contest of selected match
 */
public class MatchContestActivity extends SidemenuActivity {

    private ActivityMatchContestBinding mMatchContestActivityBinding;
    private int aOffset = 0;
    private int league_id = 0;
    private int leagueCheck = 0;
    private int payCheck = 0;
    private ProgressDialog progressDoalog;
    private int mPastVisiblesItems, mVisibleItemCount, mTotalItemCount;
    private String matchId;
    private String price = null;
    private String status;
    private boolean mLoading = true;
    private MatchContestsAdapter mAdapter;
    private ArrayList<MatchContestsData> mTempArrayListActiveContestData;
    private ArrayList<MatchContestsData> mTempArrayListInActiveContestData;
    private ArrayList<MatchContestsData> mTempArrayListAllContestData;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LeagueContestData> leaguesList;
    private ArrayList<String> payArrayList;
    private ArrayList<MatchesData> matchArrayList;
    private ChannelSpinnerForPay contestSelectSpinnerforPay;
    private ChannelSpinnerForLeauge contestSelectSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        mMatchContestActivityBinding.btnActive.setOnClickListener(v -> {
            mLoading = true;
            status = getString(R.string.active);
            ViewsVisibilites.showActiveContestView(this, mMatchContestActivityBinding);
            setRecyclerView(status);
        });

        mMatchContestActivityBinding.btnInActive.setOnClickListener(v -> {
            mLoading = true;
            status = getString(R.string.inActive);
            ViewsVisibilites.showInActiveContestView(this, mMatchContestActivityBinding);
            setRecyclerView(status);

        });

        mMatchContestActivityBinding.btnAll.setOnClickListener(v -> {
            mLoading = true;
            status = getString(R.string.txt_alls);
            ViewsVisibilites.showAllContestView(this, mMatchContestActivityBinding);
            setRecyclerView(status);

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
                                loadMatchContest(false);
                            }
                        }
                    } else {
                        mLoading = true;
                    }
                }
            }
        });

        mMatchContestActivityBinding.swipeContainerCurrent.setOnRefreshListener(() -> {
            clearListAndAdapter(false,true);
        });

        mMatchContestActivityBinding.llMatchName.setOnClickListener(v -> {
            if (mMatchContestActivityBinding.llRecyclerOther.getVisibility() == View.VISIBLE) {
                mMatchContestActivityBinding.llRecyclerOther.setVisibility(View.GONE);
                mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);
                mMatchContestActivityBinding.constraintLayout.setVisibility(View.VISIBLE);
            } else {
                mMatchContestActivityBinding.llRecyclerOther.setVisibility(View.VISIBLE);
                mMatchContestActivityBinding.rootLayout.setVisibility(View.GONE);
                mMatchContestActivityBinding.constraintLayout.setVisibility(View.GONE);
                setFilterAdapter();
            }

        });

        mMatchContestActivityBinding.imgRefresh.setOnClickListener(view -> {
            if (Network.isAvailable(this)) {
                refreshToDefaultValues();
            }else {
                Toast.makeText(MatchContestActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
            }
        });

        mMatchContestActivityBinding.spinnerLeauge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(leagueCheck > 0) {
                    if (Network.isAvailable(MatchContestActivity.this)) {
                        league_id = Integer.parseInt(leaguesList.get(i-1).getId());
                        clearListAndAdapter(false,false);
                    } else {
                        Toast.makeText(MatchContestActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
                    }
                }

                leagueCheck++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mMatchContestActivityBinding.spinnerPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (payCheck > 0) {
                    switch (i - 1) {
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
                    if (Network.isAvailable(MatchContestActivity.this)) {
                        clearListAndAdapter(false,false);
                    } else {
                        Toast.makeText(MatchContestActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
                    }
                }

                payCheck++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * @Module Name/Class		:	refreshToDefaultValues
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 17th , 2018
     * @Purpose :	This is used to clear the init variable on refresh button click
     */
    private void refreshToDefaultValues() {
        league_id = 0;
        price = null;
        leagueCheck = 0;
        payCheck = 0;

        mMatchContestActivityBinding.spinnerPay.setAdapter(new NothingSelectedSpinnerAdapter(contestSelectSpinnerforPay, R.layout.spinner_select_pay, this));
        mMatchContestActivityBinding.spinnerLeauge.setAdapter(new NothingSelectedSpinnerAdapter(contestSelectSpinner, R.layout.spinner_select_leauge, this));

        clearListAndAdapter(false,false);
    }

    /**
     * @Module Name/Class		:	setFilterAdapter
     * @Author Name             :	Sachin Arora
     * @Date                    :	Jan 17th , 2018
     * @Purpose                 :	This is used to set adapter for match filter and league
     */
    private void setFilterAdapter() {
        ContestsFilterAdapter mFilterAdapter = new ContestsFilterAdapter(matchArrayList, pos -> {

            if (Network.isAvailable(MatchContestActivity.this)) {
                matchId = matchArrayList.get(pos).getMatchId();
                mMatchContestActivityBinding.tvMatchName.setText(new StringBuilder().append(matchArrayList.get(pos).getTeam1Name()).append(" VS ").append(matchArrayList.get(pos).getTeam2Name()).toString());
                clearListAndAdapter(false,false);
            } else {
                Toast.makeText(MatchContestActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
            }

        });

        mMatchContestActivityBinding.recyclerViewOther.setAdapter(mFilterAdapter);

    }

    /**
     * @Module Name/Class		:	clearListAndAdapter
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 16th , 2018
     * @Purpose :	This method is used to notify adapter and set offset to 0.
     */
    private void clearListAndAdapter(boolean isFailure,boolean isSwipeRefresh) {

        if (progressDoalog != null && progressDoalog.isShowing()) {
            progressDoalog.dismiss();
        }

        aOffset = 0;
        mTempArrayListInActiveContestData.clear();
        mTempArrayListActiveContestData.clear();
        mTempArrayListAllContestData.clear();
        mMatchContestActivityBinding.llRecyclerOther.setVisibility(View.GONE);
        mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);
        mMatchContestActivityBinding.recyclerView.setVisibility(View.VISIBLE);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            mAdapter = null;
        }
        mMatchContestActivityBinding.constraintLayout.setVisibility(View.VISIBLE);


        if (!isFailure)
            loadMatchContest(isSwipeRefresh);
    }

    /**
     * @Module Name/Class		:	getLeague
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 17th , 2018
     * @Purpose :	This is used to populate the league spinner when data observe from api
     */
    private void getLeague() {

        SelectLeagueViewModel selectLeagueViewModel = ViewModelProviders.of(this).get(SelectLeagueViewModel.class);

        selectLeagueViewModel
                .getLeagues(mUserResponse)
                .observe(this, leaguesResponse -> {
                    if (leaguesResponse.getStatus().equalsIgnoreCase("success")) {
                        if (leaguesResponse.getMatchModel().size() > 0) {
                            if (leaguesList.size() > 0) {
                                leaguesList.clear();
                            }
                            leaguesList = leaguesResponse.getMatchModel();
                            Log.e("leauge size", String.valueOf(leaguesList.size()));
                            setSpinnerForLeauge();
                        } else {
                            Toast.makeText(MatchContestActivity.this, leaguesResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MatchContestActivity.this, leaguesResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    /**
     * @Module Name/Class		:	setSpinnerForPay
     * @Author Name             :	Sombir
     * @Date :	Jan 17th , 2018
     * @Purpose :	This is used to set pay spinner
     */
    private void setSpinnerForPay() {
        contestSelectSpinnerforPay = new ChannelSpinnerForPay(payArrayList);
        mMatchContestActivityBinding.spinnerPay.setAdapter(contestSelectSpinnerforPay);
        mMatchContestActivityBinding.spinnerPay.setAdapter(new NothingSelectedSpinnerAdapter(contestSelectSpinnerforPay, R.layout.spinner_select_pay, this));
    }

    /**
     * @Module Name/Class		:	setSpinnerForLeauge
     * @Author Name             :	Sombir
     * @Date :	Jan 17th , 2018
     * @Purpose :	This is used to set league spinner
     */
    private void setSpinnerForLeauge() {
        contestSelectSpinner = new ChannelSpinnerForLeauge(leaguesList);
        mMatchContestActivityBinding.spinnerLeauge.setAdapter(contestSelectSpinner);
        mMatchContestActivityBinding.spinnerLeauge.setAdapter(new NothingSelectedSpinnerAdapter(contestSelectSpinner, R.layout.spinner_select_leauge, this));
    }


    /**
     * @Module Name/Class		:	initDataVariables
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is using for initialization of base data
     */
    private void initDataVariables() {

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(sPleaseWait);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mMatchContestActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_contest, mBinding.contentFrame, true);
        matchArrayList = getIntent().getParcelableArrayListExtra(sMatchListKey);
        mUserResponse = getIntent().getExtras().getParcelable(sUserResponseKey);

        matchId = matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)).getMatchId();

        mMatchContestActivityBinding.btnSelectMatches.setText(AppUtils.getDateFormatYear(matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)).getMatchStartDate()));

        mTempArrayListActiveContestData = new ArrayList<>();
        mTempArrayListInActiveContestData = new ArrayList<>();
        leaguesList = new ArrayList<>();
        payArrayList = new ArrayList<>();

        mMatchContestActivityBinding.spinnerLeauge.setPrompt(getResources().getString(R.string.txt_select_league));
        mMatchContestActivityBinding.spinnerPay.setPrompt(getResources().getString(R.string.txt_select_pay));

        status = getString(R.string.txt_alls);
        ViewsVisibilites.showAllContestView(this, mMatchContestActivityBinding);

        setContestRecyclerViewManager();
        setFilterRecyclerViewManager();

        mMatchContestActivityBinding.tvMatchName.setText(new StringBuilder().append(matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)).getTeam1Name()).append(" VS ").append(matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)).getTeam2Name()).toString());

        setSelectPay();

        setSpinnerForPay();

        if (Network.isAvailable(this)) {
            getLeague();
            loadMatchContest(false);
        } else {
            Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * @Module Name/Class		:	setSelectPay
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is used to create pay filter arraylist
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
     * @Author Name             :	Rohit puri
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is used to set layout manager for league filter recycler view
     */
    private void setFilterRecyclerViewManager() {
        RecyclerView.LayoutManager mLayoutOtherManager = new LinearLayoutManager(this);

        mMatchContestActivityBinding.recyclerViewOther.setLayoutManager(mLayoutOtherManager);
        mMatchContestActivityBinding.recyclerViewOther.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mMatchContestActivityBinding.recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mMatchContestActivityBinding.recyclerViewOther.addItemDecoration(dividerItemDecoration);
    }

    /**
     * @Module Name/Class		:	loadMatchContest
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is used to fetch the contest list and observe data
     */
    private void loadMatchContest(boolean isSwipeRefresh) {


        if (!progressDoalog.isShowing() && !isSwipeRefresh){
            progressDoalog.show();
        }
        MatchContestsViewModel mMatchContestViewModel = ViewModelProviders.of(this).get(MatchContestsViewModel.class);

        int aLimit = 10;
        mMatchContestViewModel
                .getMatchContests(mUserResponse, matchId, String.valueOf(league_id), price, String.valueOf(aOffset), String.valueOf(aLimit))
                .observe(this, matchContestsResponse -> {
                    if (progressDoalog.isShowing()) {
                        progressDoalog.dismiss();
                    }

                    if (matchContestsResponse.getStatus().equalsIgnoreCase(sSuccess)) {
                        if (matchContestsResponse.getContestModel().size() > 0) {
                            Log.e("size is", matchContestsResponse.getContestModel().size() + "");
                            Log.e("contest id", matchContestsResponse.getContestModel().get(0).getId());
                            mLoading = true;
                            aOffset = aOffset + matchContestsResponse.getContestModel().size();
                            setActiveOrInActive(matchContestsResponse.getContestModel());
                        } else {
                            Toast.makeText(MatchContestActivity.this, matchContestsResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e("failure", "***");
                        Toast.makeText(MatchContestActivity.this, matchContestsResponse.getMessage(), Toast.LENGTH_LONG).show();

                        if (aOffset==0)
                            clearListAndAdapter(true,false);
                    }
                    if (mMatchContestActivityBinding.swipeContainerCurrent.isRefreshing()) {
                        mMatchContestActivityBinding.swipeContainerCurrent.setRefreshing(false);
                    }
                });
    }

    /**
     * @Module Name/Class		:	setActiveOrInActive
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is used to set Active and InActive contest list
     */
    private void setActiveOrInActive(ArrayList<MatchContestsData> matchModel) {

        ArrayList<MatchContestsData> activeList = new ArrayList<>();
        ArrayList<MatchContestsData> inActiveList = new ArrayList<>();
        mTempArrayListAllContestData = new ArrayList<>();

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
        mTempArrayListAllContestData.addAll(matchModel);

        if (mAdapter == null)
            setRecyclerView(status);
        else
            mAdapter.notifyDataSetChanged();

    }

    /**
     * @Module Name/Class		:	setRecyclerView
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is used to set recycler view based on active or inactive status
     */
    private void setRecyclerView(String status) {
        if (status.equalsIgnoreCase(getString(R.string.txt_alls))) {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListAllContestData, pos -> {
                Intent selectTeamIntent = new Intent(MatchContestActivity.this, SelectTeamActivity.class);
                selectTeamIntent.putExtra(AppConstant.contest, mTempArrayListAllContestData.get(pos));
                selectTeamIntent.putExtra(AppConstant.sUserResponseKey, mUserResponse);
                selectTeamIntent.putExtra(AppConstant.matchResponse, matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)));
                startActivity(selectTeamIntent);

                Log.e("all", mTempArrayListAllContestData.size() + "");

            });
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        } else if (status.equalsIgnoreCase(getString(R.string.active))) {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListActiveContestData, pos -> {
                Intent selectTeamIntent = new Intent(MatchContestActivity.this, SelectTeamActivity.class);
                selectTeamIntent.putExtra(AppConstant.contest, mTempArrayListAllContestData.get(pos));
                selectTeamIntent.putExtra(AppConstant.matchResponse, matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)));
                selectTeamIntent.putExtra(AppConstant.sUserResponseKey, mUserResponse);
                startActivity(selectTeamIntent);

                Log.e("active", mTempArrayListActiveContestData.size() + "");

            });
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListInActiveContestData, pos -> {
                Intent selectTeamIntent = new Intent(MatchContestActivity.this, SelectTeamActivity.class);
                selectTeamIntent.putExtra(AppConstant.contest, mTempArrayListAllContestData.get(pos));
                selectTeamIntent.putExtra(AppConstant.sUserResponseKey, mUserResponse);
                selectTeamIntent.putExtra(AppConstant.matchResponse, matchArrayList.get(getIntent().getIntExtra(sPositionKey, -1)));
                startActivity(selectTeamIntent);

                Log.e("inactive", mTempArrayListInActiveContestData.size() + "");

            });
            mMatchContestActivityBinding.recyclerView.setAdapter(mAdapter);
        }
    }


    /**
     * @Module Name/Class		:	setContestRecyclerViewManager
     * @Author Name             :	Sachin Arora
     * @Date :	Jan 15th , 2018
     * @Purpose :	This method is used to set recycler view manager for contest recycler view
     */
    private void setContestRecyclerViewManager() {
        mLayoutManager = new LinearLayoutManager(this);
        mMatchContestActivityBinding.recyclerView.setLayoutManager(mLayoutManager);
        mMatchContestActivityBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onBackPressed() {
        if (mMatchContestActivityBinding.llRecyclerOther.getVisibility() == View.VISIBLE) {
            mMatchContestActivityBinding.llRecyclerOther.setVisibility(View.GONE);
            mMatchContestActivityBinding.rootLayout.setVisibility(View.VISIBLE);
            mMatchContestActivityBinding.constraintLayout.setVisibility(View.VISIBLE);
        }else
            super.onBackPressed();
    }

}
