package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.ViewModel.CombinationViewModel;
import com.tech.rotobash.ViewModel.ContestViewModel;
import com.tech.rotobash.adapters.SelectCupsRankAdapter;
import com.tech.rotobash.databinding.ActivitySelectTeamBinding;
import com.tech.rotobash.model.Combination;
import com.tech.rotobash.model.ContestRankData;
import com.tech.rotobash.model.MatchContestsData;
import com.tech.rotobash.model.MatchesData;
import com.tech.rotobash.model.TeamCombinationData;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.AppUtils;
import com.tech.rotobash.utils.Network;

import java.util.ArrayList;

import static com.tech.rotobash.utils.AppConstant.sPleaseWait;
import static com.tech.rotobash.utils.AppConstant.sUserResponseKey;

public class SelectTeamActivity extends SidemenuActivity {
    private ActivitySelectTeamBinding mSelectTeamActivityBinding;
    private ProgressDialog progressDoalog;
    private SelectCupsRankAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ContestRankData> mContestRanksData;
    private MatchContestsData matchContestsData;
    private MatchesData matchesData;
    private TeamCombinationData teamCombinationData;
    private ArrayList<Combination> mteamCombinationDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initializing views using data binding
        initDataVariables();

        // top header back button and menu click
        backButtonAndMenuButtonsClick();

        // possible combination default view
        defaultCombinationAndSelection();

        // onClick of all the views used in this screen
        onClickOfViews();

        // playing list tab and bottom view
        tabLayoutAndBottomViews();
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
        mteamCombinationDataList = new ArrayList<>();
        mUserResponse = getIntent().getParcelableExtra(sUserResponseKey);
        matchContestsData = getIntent().getParcelableExtra(AppConstant.contest);
        matchesData = getIntent().getParcelableExtra(AppConstant.matchResponse);

        // top bar view entries
        topBarDetails();

        setContestRecyclerViewManager();

        if (Network.isAvailable(this)) {

            // teams combination
            getTeamsCombinations();

            // contest ranks
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
     * @Module Name/Class		:	topBarDetails
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This method is used to show top bar teams name and timer.
     */
    private void topBarDetails() {
        mSelectTeamActivityBinding.tvTeamName.setText(new StringBuilder().append(matchesData.getTeam1Name()).append(" VS ").append(matchesData.getTeam2Name()).toString());
        mSelectTeamActivityBinding.txtTimeLeft.setText(AppUtils.getDateFormatYear(matchesData.getMatchStartDate()));

        if (matchContestsData.getLeague_name().equalsIgnoreCase("max bash")) {
            mSelectTeamActivityBinding.tvLeagueName.setBackgroundColor(getResources().getColor(R.color.color_maxbash));
            mSelectTeamActivityBinding.tvLeagueName.setText(matchContestsData.getLeague_name());
        } else if (matchContestsData.getLeague_name().equalsIgnoreCase("max boundary")) {
            mSelectTeamActivityBinding.tvLeagueName.setText(matchContestsData.getLeague_name());
            mSelectTeamActivityBinding.tvLeagueName.setBackgroundColor(getResources().getColor(R.color.color_max_bound));
        } else if (matchContestsData.getLeague_name().equalsIgnoreCase("max score")) {
            mSelectTeamActivityBinding.tvLeagueName.setText(matchContestsData.getLeague_name());
            mSelectTeamActivityBinding.tvLeagueName.setBackgroundColor(getResources().getColor(R.color.color_max_score));
        } else if (matchContestsData.getLeague_name().equalsIgnoreCase("fast score")) {
            mSelectTeamActivityBinding.tvLeagueName.setText(matchContestsData.getLeague_name());
            mSelectTeamActivityBinding.tvLeagueName.setBackgroundColor(getResources().getColor(R.color.color_fast_score));
        } else {
            mSelectTeamActivityBinding.tvLeagueName.setText(matchContestsData.getLeague_name());
            mSelectTeamActivityBinding.tvLeagueName.setBackgroundColor(getResources().getColor(R.color.color_default));
        }
    }

    /**
     * @Module Name/Class		:	backButtonAndMenuButtonsClick
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This method is used to for back button and menu button on click.
     */
    private void backButtonAndMenuButtonsClick() {
        mSelectTeamActivityBinding.imgBack.setOnClickListener(v -> onBackPressed());

        mSelectTeamActivityBinding.imgMenu.setOnClickListener(v -> openCloseDrawer());
    }

    /**
     * @Module Name/Class		:	defaultCombinationAndSelection
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This method is used to store default color of combination and selection criteria view.
     */
    private void defaultCombinationAndSelection() {
        mSelectTeamActivityBinding.tvPossibleCombinations.setBackgroundColor(ContextCompat.getColor(SelectTeamActivity.this, R.color.color_black));
        mSelectTeamActivityBinding.tvSelectionCriteria.setBackgroundColor(ContextCompat.getColor(SelectTeamActivity.this, R.color.combinations_background_color));

        mSelectTeamActivityBinding.tvPossibleCombinations.setText(getResources().getString(R.string.txt_possible_combinations));
        mSelectTeamActivityBinding.tvSelectionCriteria.setText(getResources().getString(R.string.txt_selection_criteria));
    }

    /**
     * @Module Name/Class		:	onClickOfViews
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This method is used to handle views onClick.
     */
    private void onClickOfViews() {

        mSelectTeamActivityBinding.tvPossibleCombinations.setOnClickListener(view -> {

            mSelectTeamActivityBinding.tvPossibleCombinations.setBackgroundColor(ContextCompat.getColor(SelectTeamActivity.this, R.color.color_black));
            mSelectTeamActivityBinding.tvSelectionCriteria.setBackgroundColor(ContextCompat.getColor(SelectTeamActivity.this, R.color.combinations_background_color));

            mSelectTeamActivityBinding.tvPossibleCombinations.setText(getResources().getString(R.string.txt_possible_combinations));
            mSelectTeamActivityBinding.tvSelectionCriteria.setText(getResources().getString(R.string.txt_selection_criteria));

            // possible combination inflator
            setLayoutInflatorForCombination();

        });

        mSelectTeamActivityBinding.tvSelectionCriteria.setOnClickListener(view -> {

            mSelectTeamActivityBinding.tvPossibleCombinations.setBackgroundColor(ContextCompat.getColor(SelectTeamActivity.this, R.color.combinations_background_color));
            mSelectTeamActivityBinding.tvSelectionCriteria.setBackgroundColor(ContextCompat.getColor(SelectTeamActivity.this, R.color.color_black));

            mSelectTeamActivityBinding.tvPossibleCombinations.setText(getResources().getString(R.string.txt_possible_combinations));
            mSelectTeamActivityBinding.tvSelectionCriteria.setText(getResources().getString(R.string.txt_selection_criteria));

            mSelectTeamActivityBinding.layoutSelection.layoutSelectionView.setVisibility(View.VISIBLE);
            mSelectTeamActivityBinding.llPossibleCombinations.setVisibility(View.GONE);
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
    }

    /**
     * @Module Name/Class		:	tabLayoutAndBottomViews
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This method is used to show selection tab and the selection playlist.
     */
    private void tabLayoutAndBottomViews() {

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
                .observe(this, contestRankResponse -> {

                    if (progressDoalog.isShowing()) {
                        progressDoalog.dismiss();
                    }

                    //  Log.e("status", )
                    try {
                        if (contestRankResponse.getStatus().equalsIgnoreCase("success")) {
                            if (contestRankResponse.getResponse().size() > 0) {
                                mContestRanksData = contestRankResponse.getResponse();
                            } else {
                                Toast.makeText(SelectTeamActivity.this, contestRankResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SelectTeamActivity.this, contestRankResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exp) {
                        Toast.makeText(SelectTeamActivity.this, AppConstant.responseNotAvailable, Toast.LENGTH_LONG).show();
                        exp.printStackTrace();
                    }
                });
    }

    /**
     * @Module Name/Class		:	getContestRanksData
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 18th , 2018
     * @Purpose :	This method is used for getting contest ranking and winner
     */
    private void getTeamsCombinations() {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        CombinationViewModel combinationViewModel = ViewModelProviders.of(this).get(CombinationViewModel.class);

        combinationViewModel
                .getTeamsCombinations(mUserResponse, matchesData.getMatchType(), matchContestsData.getLeague_code())
                .observe(this, teamCombinationResponse -> {

                    if (progressDoalog.isShowing()) {
                        progressDoalog.dismiss();
                    }
                    try {
                        if (teamCombinationResponse.getStatus().equalsIgnoreCase("success")) {

                            teamCombinationData = teamCombinationResponse.getResponse();

                            setSelectionCriteriaData();
                            if (teamCombinationResponse.getResponse().getCombination().size() > 0) {
                                Log.e("combina", teamCombinationResponse.getResponse().getCombination().get(0).getAR());
                                mteamCombinationDataList = teamCombinationResponse.getResponse().getCombination();
                                setLayoutInflatorForCombination();
                            } else {
                                Toast.makeText(SelectTeamActivity.this, teamCombinationResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SelectTeamActivity.this, teamCombinationResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exp) {
                        Toast.makeText(SelectTeamActivity.this, AppConstant.responseNotAvailable, Toast.LENGTH_LONG).show();
                        exp.printStackTrace();
                    }
                });
    }


    /**
     * @Module Name/Class		:	setSelectionCriteriaData
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This method is used for displaying selection criteria.
     */
    private void setSelectionCriteriaData() {

        if (teamCombinationData.getCriteria() != null) {
            mSelectTeamActivityBinding.layoutSelection.tvMaxTeam.setText(teamCombinationData.getCriteria().getOneTeamMax());
            mSelectTeamActivityBinding.layoutSelection.tvMaxBat.setText(teamCombinationData.getCriteria().getBatsmanMax());
            mSelectTeamActivityBinding.layoutSelection.tvMaxBowl.setText(teamCombinationData.getCriteria().getBallersMax());
            mSelectTeamActivityBinding.layoutSelection.tvMaxAR.setText(teamCombinationData.getCriteria().getARMax());
            mSelectTeamActivityBinding.layoutSelection.tvMaxWK.setText(teamCombinationData.getCriteria().getWKMax());
            mSelectTeamActivityBinding.layoutSelection.tvMaxSub.setText(teamCombinationData.getCriteria().getSubMax());

            mSelectTeamActivityBinding.layoutSelection.tvMinTeam.setText(teamCombinationData.getCriteria().getOneTeamMin());
            mSelectTeamActivityBinding.layoutSelection.tvMinBat.setText(teamCombinationData.getCriteria().getBatsmanMin());
            mSelectTeamActivityBinding.layoutSelection.tvMinBowl.setText(teamCombinationData.getCriteria().getBallersMin());
            mSelectTeamActivityBinding.layoutSelection.tvMinAR.setText(teamCombinationData.getCriteria().getARMin());
            mSelectTeamActivityBinding.layoutSelection.tvMinWK.setText(teamCombinationData.getCriteria().getWKMin());
            mSelectTeamActivityBinding.layoutSelection.tvMinSub.setText(teamCombinationData.getCriteria().getSubMin());
        }
    }

    /**
     * @Module Name/Class		:	setLayoutInflatorForCombination
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This is used to add inflate possible combination view.
     */
    private void setLayoutInflatorForCombination() {

        mSelectTeamActivityBinding.layoutSelection.layoutSelectionView.setVisibility(View.GONE);
        mSelectTeamActivityBinding.llPossibleCombinations.setVisibility(View.VISIBLE);

        mSelectTeamActivityBinding.llPossibleCombinations.removeAllViews();

        for (int i = 0; i < mteamCombinationDataList.size(); i++) {

            addViews(mteamCombinationDataList.get(i));
        }
    }


    /**
     * @Module Name/Class		:	addViews
     * @Author Name             :	Sombir Bisht
     * @Date :	Jan 19th , 2018
     * @Purpose :	This is used to inflate and show possible combination view.
     */
    private void addViews(Combination combination) {

      /*  CustomPossibleCombinationBinding customPossibleCombinationBinding = DataBindingUtil.inflate(LayoutInflater
                .from(this), R.layout.custom_possible_combination, null, false);*/

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myView = inflater.inflate(R.layout.custom_possible_combination, null);

        RelativeLayout rlCompleteView = myView.findViewById(R.id.rlCompleteView);
        TextView tvCustomPossibleCombination = myView.findViewById(R.id.tvCustomPossibleCombination);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rlCompleteView.setLayoutParams(layoutParams);

        mSelectTeamActivityBinding.llPossibleCombinations.addView(rlCompleteView);
        tvCustomPossibleCombination.setText(new StringBuilder().append("BATSMAN " + combination.getBT()).append(" | ").append("  BOWLER ").append(combination.getBW()).append(" | ").append("  ALL-ROUNDER ").append(combination.getAR()).append(" | ").append("  WICKET-KEEPER ").append(combination.getWK()));


    }

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();

        mSelectTeamActivityBinding.ivCup.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cup));

        mSelectTeamActivityBinding.rlTeamSelection.setVisibility(View.VISIBLE);
        mSelectTeamActivityBinding.layoutRanking.setVisibility(View.GONE);
    }*/
}
