package com.tech.rotobash.ViewModel;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.MatchesResponse;
import com.tech.rotobash.model.UserResponse;

/**
 @Module class/module		:	MatchesViewModel
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 8 , 2018
 @Purpose				    :	It contains methods to provide service for respective view like Matches
 */
public class MatchesViewModel extends ViewModel {

    private LiveData<MatchesResponse> mLiveMatchData;

    private CommonService mCommonService = new CommonService();

    public LiveData<MatchesResponse> getMatches(ProgressDialog progressDialog, UserResponse aUserResponse, String aMatchType, String aOffset) {
        mLiveMatchData = mCommonService.getMatches(progressDialog,aUserResponse,aMatchType,aOffset);
        return mLiveMatchData;
    }

}
