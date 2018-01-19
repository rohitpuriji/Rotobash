package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.ViewModel.ContestViewModel;
import com.tech.rotobash.adapters.SelectCupsRankAdapter;
import com.tech.rotobash.databinding.ActivitySelectTeamBinding;
import com.tech.rotobash.model.ContestRankData;
import com.tech.rotobash.model.MatchContestsData;
import com.tech.rotobash.model.MatchesData;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.AppUtils;
import com.tech.rotobash.utils.Network;

import java.util.ArrayList;

import static com.tech.rotobash.utils.AppConstant.sPleaseWait;
import static com.tech.rotobash.utils.AppConstant.sPositionKey;
import static com.tech.rotobash.utils.AppConstant.sUserResponseKey;


public class SelectTeamActivity extends SidemenuActivity {
    private ActivitySelectTeamBinding mSelectTeamActivityBinding;
    private ProgressDialog progressDoalog;
    private SelectCupsRankAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ContestRankData> mContestRanksData;
    private MatchContestsData matchContestsData;
    private MatchesData matchesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        mSelectTeamActivityBinding.imgBack.setOnClickListener(v -> onBackPressed());

        mSelectTeamActivityBinding.imgMenu.setOnClickListener(v -> openCloseDrawer());

        mSelectTeamActivityBinding.tvPossibleCombinations.setOnClickListener(view -> {
            View childView = getLayoutInflater().inflate(R.layout.custom_possible_combination, null);
            mSelectTeamActivityBinding.llPossibleCombinations.addView(childView);
        });

        mSelectTeamActivityBinding.tvSelectionCriteria.setOnClickListener(view -> {
            View childView = getLayoutInflater().inflate(R.layout.custom_selection_criteria, null);
            mSelectTeamActivityBinding.llPossibleCombinations.addView(childView);
        });

        mSelectTeamActivityBinding.ivCup.setOnClickListener(view -> {

            if (mSelectTeamActivityBinding.ivCup.getDrawable().getConstantState() == ContextCompat.getDrawable(this, R.drawable.cup).getConstantState()) {

                if (mContestRanksData.size() > 0) {

                    mSelectTeamActivityBinding.rlTeamSelection.setVisibility(View.GONE);
                    mSelectTeamActivityBinding.layoutRanking.setVisibility(View.VISIBLE);
                    mSelectTeamActivityBinding.rvListOfRanks.setHasFixedSize(true);

                    mSelectTeamActivityBinding.ivCup.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cup_selected));

                    setRecyclerView();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.rank_not_available), Toast.LENGTH_LONG).show();
                }

            } else if (mSelectTeamActivityBinding.ivCup.getDrawable().getConstantState() == ContextCompat.getDrawable(this, R.drawable.cup_selected).getConstantState()) {

                mSelectTeamActivityBinding.ivCup.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cup));

                mSelectTeamActivityBinding.rlTeamSelection.setVisibility(View.VISIBLE);
                mSelectTeamActivityBinding.layoutRanking.setVisibility(View.GONE);

            }
        });

        mSelectTeamActivityBinding.tvPossibleCombinations.setOnClickListener(view -> {

            // possible combinations

        });

        mSelectTeamActivityBinding.tvSelectionCriteria.setOnClickListener(view -> {

            // selection criteria

        });

        mSelectTeamActivityBinding.tabLayout.addTab(mSelectTeamActivityBinding.tabLayout.newTab().setText("BAT"));
        mSelectTeamActivityBinding.tabLayout.addTab(mSelectTeamActivityBinding.tabLayout.newTab().setText("BOWL"));
        mSelectTeamActivityBinding.tabLayout.addTab(mSelectTeamActivityBinding.tabLayout.newTab().setText("AR"));
        mSelectTeamActivityBinding.tabLayout.addTab(mSelectTeamActivityBinding.tabLayout.newTab().setText("WK"));

        mSelectTeamActivityBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    /**
     * @Module Name/Class		:	setRecyclerView
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 18th , 2018
     * @Purpose :	This method is used to set adapter on recycler view.
     */
    private void setRecyclerView() {
        mAdapter = new SelectCupsRankAdapter(this, mContestRanksData);
        mSelectTeamActivityBinding.rvListOfRanks.setAdapter(mAdapter);
    }

    /**
     * @Module Name/Class		:	initDataVariables
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 18th , 2018
     * @Purpose :	This method is used for binding the view and there initialization.
     */
    private void initDataVariables() {

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(sPleaseWait);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mSelectTeamActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_select_team, mBinding.contentFrame, true);

        mContestRanksData = new ArrayList<>();
        mUserResponse = getIntent().getParcelableExtra(sUserResponseKey);
        matchContestsData = getIntent().getParcelableExtra(AppConstant.contest);
        matchesData = getIntent().getParcelableExtra(AppConstant.matchResponse);

        Log.e("matchContestsData", matchContestsData.getId());
        Log.e("matchContestsData name1", matchesData.getTeam1Name());
        Log.e("matchContestsData name2", matchesData.getTeam2Name());


        mSelectTeamActivityBinding.tvTeamName.setText(new StringBuilder().append(matchesData.getTeam1Name()).append(" VS ").append(matchesData.getTeam1Name()).toString());
        mSelectTeamActivityBinding.txtTimeLeft.setText(AppUtils.getDateFormatYear(matchesData.getMatchStartDate()));

        setContestRecyclerViewManager();

        if (Network.isAvailable(this)) {
            getContestRanksData();
        } else {
            Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @Module Name/Class		:	setContestRecyclerViewManager
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 18th , 2018
     * @Purpose :	This method is used to set layout manager
     */
    private void setContestRecyclerViewManager() {
        mLayoutManager = new LinearLayoutManager(this);
        mSelectTeamActivityBinding.rvListOfRanks.setLayoutManager(mLayoutManager);
    }

    /**
     * @Module Name/Class		:	getContestRanksData
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 18th , 2018
     * @Purpose :	This method is used for getting contest ranking and winner
     */
    private void getContestRanksData() {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        ContestViewModel contestViewModel = ViewModelProviders.of(this).get(ContestViewModel.class);

        contestViewModel
                .getContestRankData(mUserResponse, matchContestsData.getId())
                .observe(this, contestResponse -> {

                    if (progressDoalog.isShowing()) {
                        progressDoalog.dismiss();
                    }
                    if (contestResponse.getStatus().equalsIgnoreCase("success")) {
                        if (contestResponse.getResponse().size() > 0) {
                            mContestRanksData = contestResponse.getResponse();
                        } else {
                            Toast.makeText(SelectTeamActivity.this, contestResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SelectTeamActivity.this, contestResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();

        mSelectTeamActivityBinding.ivCup.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cup));

        mSelectTeamActivityBinding.rlTeamSelection.setVisibility(View.VISIBLE);
        mSelectTeamActivityBinding.layoutRanking.setVisibility(View.GONE);
    }*/
}
