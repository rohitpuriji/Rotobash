package com.tech.rotobash.activites;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ActivityMatchListBinding;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppConstant;
import com.tech.rotobash.utils.Network;

/**
 * Created by sachinarora on 11/1/18.
 */

public class MatchListActivity extends SidemenuActivity {

    private UserResponse mUserResponse;
    private ActivityMatchListBinding mMatchListActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

        setDataToParentClass(mUserResponse);

        mMatchListActivityBinding.imgMenu.setOnClickListener(v -> {
            openCloseDrawer();
        });


        mMatchListActivityBinding.includedContent.btnCurrent.setOnClickListener(view -> {
            setBackgroundAndVisibility(true);
        });

        mMatchListActivityBinding.includedContent.btnComing.setOnClickListener(view -> {
            setBackgroundAndVisibility(false);
        });
    }

    private void setBackgroundAndVisibility(boolean isShowingCurrent) {

        if (Network.isAvailable(this)) {
            loadCurrentMatches();

        } else {
            Toast.makeText(this, AppConstant.sNoInternet, Toast.LENGTH_LONG).show();
        }

        if (isShowingCurrent) {
            mMatchListActivityBinding.includedContent.btnCurrent.setBackgroundColor(ContextCompat.getColor(this, R.color.color_current_gray));
            mMatchListActivityBinding.includedContent.btnComing.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
            mMatchListActivityBinding.includedContent.swipeContainerCurrent.setVisibility(View.VISIBLE);
            mMatchListActivityBinding.includedContent.swipeContainerUpcoming.setVisibility(View.GONE);

        } else {
            mMatchListActivityBinding.includedContent.btnCurrent.setBackgroundColor(ContextCompat.getColor(this, R.color.color_white));
            mMatchListActivityBinding.includedContent.btnComing.setBackgroundColor(ContextCompat.getColor(this, R.color.color_current_gray));
            mMatchListActivityBinding.includedContent.swipeContainerCurrent.setVisibility(View.GONE);
            mMatchListActivityBinding.includedContent.swipeContainerUpcoming.setVisibility(View.VISIBLE);
        }
    }

    private void initDataVariables() {
        mUserResponse = getIntent().getExtras().getParcelable("UserResponse");
        mMatchListActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_match_list, mBinding.contentFrame, true);

    }

    /**
     * @Module Name/Class		:	loadCurrentMatches
     * @Author Name            :	Rohit Puri
     * @Date :	Jan 11th , 2018
     * @Purpose :	This method loads the current matches from api
     */
    private void loadCurrentMatches() {

    }
}
