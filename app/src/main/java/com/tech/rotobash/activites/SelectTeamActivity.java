package com.tech.rotobash.activites;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.tech.rotobash.R;
import com.tech.rotobash.databinding.ActivitySelectTeamBinding;


public class SelectTeamActivity extends SidemenuActivity {
    private ActivitySelectTeamBinding mSelectTeamActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataVariables();

    }

    private void initDataVariables() {
        mSelectTeamActivityBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_select_team, mBinding.contentFrame, true);

    }
}
