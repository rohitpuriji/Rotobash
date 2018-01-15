package com.tech.rotobash.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.Validations.ViewsVisibilites;
import com.tech.rotobash.ViewModel.MatchesViewModel;
import com.tech.rotobash.adapters.MatchesAdapter;
import com.tech.rotobash.databinding.ActivityMatchListBinding;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.Network;

import static com.tech.rotobash.utils.AppConstant.sPleaseWait;

/**
 * Created by sachinarora on 11/1/18.
 */

public class MatchListActivity extends SidemenuActivity {

    private UserResponse mUserResponse;
    private ActivityMatchListBinding mMatchListActivityBinding;
    private int mMatchType = 2, mOffset = 0;
    private ProgressDialog progressDoalog;
    private MatchesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        setDataToParentClass(mUserResponse);

        mMatchListActivityBinding.imgMenu.setOnClickListener(v -> {
            openCloseDrawer();
        });


        mMatchListActivityBinding.includedContent.btnCurrent.setOnClickListener(view -> {
            ViewsVisibilites.showCurrentMatchesView(MatchListActivity.this, mMatchListActivityBinding);
            mMatchType = 2;

        });

        mMatchListActivityBinding.includedContent.btnComing.setOnClickListener(view -> {
            ViewsVisibilites.showComingMatchesView(MatchListActivity.this, mMatchListActivityBinding);
            mMatchType = 1;
        });
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
        mMatchListActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_list, mBinding.contentFrame, true);
        mUserResponse = getIntent().getExtras().getParcelable("UserResponse");
        progressDoalog = new ProgressDialog(MatchListActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(sPleaseWait);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        setSupportActionBar(mMatchListActivityBinding.toolbar);

        setCurrentRecyclerViewManager();

        if (Network.isAvailable(MatchListActivity.this)) {
            loadCurrentMatches(mMatchType + "", mOffset + "");
        } else {
            Toast.makeText(MatchListActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @Module Name/Class		:	loadCurrentMatches
     * @Author Name            :	Rohit Puri
     * @Date :	Jan 11th , 2018
     * @Purpose :	This method loads the current matches from api
     */
    private void loadCurrentMatches() {

        progressDoalog = new ProgressDialog(MatchListActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage(sPleaseWait);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        setSupportActionBar(mMatchListActivityBinding.toolbar);

        setCurrentRecyclerViewManager();

        if (Network.isAvailable(MatchListActivity.this)) {
            loadCurrentMatches(mMatchType + "", mOffset + "");
        } else {
            Toast.makeText(MatchListActivity.this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }
    }

    private void setCurrentRecyclerViewManager() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MatchListActivity.this);
        mMatchListActivityBinding.includedContent.recyclerViewCurrent.setLayoutManager(mLayoutManager);
        mMatchListActivityBinding.includedContent.recyclerViewCurrent.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * @Module Name/Class		:	loadCurrentMatches
     * @Author Name             :	Rohit Puri
     * @Date :	Jan 11th , 2018
     * @Purpose :	This method loads the current matches from api
     */
    private void loadCurrentMatches(String aMatchType, String aOffset) {

        MatchesViewModel mMatchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);

        mMatchesViewModel.getMatches(progressDoalog, mUserResponse, aMatchType, aOffset)
                .observe(this, matchesResponse -> {
                    if (matchesResponse.getStatus().equalsIgnoreCase("success")) {
                        if (matchesResponse.getMatchModel().size() > 0) {
                            mAdapter = new MatchesAdapter(matchesResponse.getMatchModel());
                            mMatchListActivityBinding.includedContent.recyclerViewCurrent.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(MatchListActivity.this, matchesResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MatchListActivity.this, matchesResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
