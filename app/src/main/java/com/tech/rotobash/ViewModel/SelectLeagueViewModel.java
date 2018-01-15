package com.tech.rotobash.ViewModel;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.LeaguesResponse;
import com.tech.rotobash.model.MatchesResponse;
import com.tech.rotobash.model.UserResponse;

/**
 * @Module class/module		:	MatchesViewModel
 * @Author Name             :	Rohit Puri
 * @Date                    :	Jan 8 , 2018
 * @Purpose                 :	It contains methods to provide service for respective view like Matches
 */
public class SelectLeagueViewModel extends ViewModel {

    private LiveData<LeaguesResponse> mMatchData;

    private CommonService mCommonService = new CommonService();

    public LiveData<LeaguesResponse> getLeagues(UserResponse aUserResponse) {
        mMatchData = mCommonService.getLeague(aUserResponse);
        return mMatchData;
    }

}
