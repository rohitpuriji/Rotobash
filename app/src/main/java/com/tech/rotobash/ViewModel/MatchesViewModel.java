package com.tech.rotobash.ViewModel;

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

    private LiveData<MatchesResponse> mMatchData;

    private CommonService mCommonService = new CommonService();

    public LiveData<MatchesResponse> getMatches(String aSeriesId,UserResponse aUserResponse, String aMatchType, String aOffset) {
        mMatchData = mCommonService.getMatches(aSeriesId,aUserResponse,aMatchType,aOffset);
        return mMatchData;
    }

}
