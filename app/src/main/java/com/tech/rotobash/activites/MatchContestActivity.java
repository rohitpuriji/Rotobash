package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.Validations.ViewsVisibilites;
import com.tech.rotobash.ViewModel.MatchContestsViewModel;
import com.tech.rotobash.adapters.MatchContestsAdapter;
import com.tech.rotobash.databinding.ActivityMatchContestBinding;
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
    private String matchId;
    private MatchContestsAdapter mAdapter;
    private ArrayList<MatchContestsData> mTempArrayListActiveContestData;
    private ArrayList<MatchContestsData> mTempArrayListInActiveContestData;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);

        mMatchContestActivityBinding.includedContent.btnCurrent.setOnClickListener(v -> {
            status = getString(R.string.active);
            ViewsVisibilites.showActiveMatchView(this, mMatchContestActivityBinding);
            setRecyclerView(status);
        });


        mMatchContestActivityBinding.includedContent.btnComing.setOnClickListener(v -> {
            status = getString(R.string.inActive);
            ViewsVisibilites.showInActiveMatchView(this, mMatchContestActivityBinding);
            setRecyclerView(status);
        });


        mMatchContestActivityBinding.imgBack.setOnClickListener(v -> onBackPressed());

        mMatchContestActivityBinding.imgMenu.setOnClickListener(v -> openCloseDrawer());
    }

    private void initDataVariables() {
        mMatchContestActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_contest, mBinding.contentFrame, true);
        matchId = "52";
        mUserResponse = getIntent().getExtras().getParcelable("UserResponse");
        mTempArrayListActiveContestData = new ArrayList<>();
        mTempArrayListInActiveContestData = new ArrayList<>();

        status = getString(R.string.active);

        setCurrentRecyclerViewManager();

        if (Network.isAvailable(this)) {
            loadMatchContest();

        } else {
            Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }

    }

    private void loadMatchContest() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage(sPleaseWait);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        MatchContestsViewModel mMatchContestViewModel = ViewModelProviders.of(this).get(MatchContestsViewModel.class);

        mMatchContestViewModel.getMatchContests(progressDialog, mUserResponse, matchId, String.valueOf(aOffset), String.valueOf(aLimit))
                .observe(this, matchContestsResponse -> {
                    if (matchContestsResponse.getStatus().equalsIgnoreCase("success")) {
                        if (matchContestsResponse.getMatchModel().size() > 0) {

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

//        for (int i = 0; i < matchModel.size(); i++) {
//
//            if (Integer.parseInt(matchModel.get(i).getTotal_users()) >= Integer.parseInt(matchModel.get(i).getMin_user())) {
//
//                MatchContestsData matchContestsData = matchModel.get(i);
//                activeList.add(matchContestsData);
//
//            } else {
//                MatchContestsData matchContestsData = matchModel.get(i);
//                inActiveList.add(matchContestsData);
//            }
//        }

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
            mMatchContestActivityBinding.includedContent.recyclerViewCurrent.setAdapter(mAdapter);

        } else {
            mAdapter = new MatchContestsAdapter(MatchContestActivity.this, mTempArrayListInActiveContestData);
            mMatchContestActivityBinding.includedContent.recyclerViewCurrent.setAdapter(mAdapter);
        }
    }

    private void setCurrentRecyclerViewManager() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mMatchContestActivityBinding.includedContent.recyclerViewCurrent.setLayoutManager(mLayoutManager);
        mMatchContestActivityBinding.includedContent.recyclerViewCurrent.setItemAnimator(new DefaultItemAnimator());
    }
}
