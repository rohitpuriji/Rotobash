package com.tech.rotobash.ViewModel;

/**
 * Created by sombirbisht on 19/1/18.
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.TeamCombinationResponse;
import com.tech.rotobash.model.UserResponse;

/**
 * @Module class/module		:	CombinationViewModel
 * @Author Name             :	Sombir Bisht
 * @Date :	Jan 19 , 2018
 * @Purpose :	It contains methods to provide team possible combinations
 */
public class CombinationViewModel extends ViewModel {

    private LiveData<TeamCombinationResponse> mMatchData;

    private CommonService mCommonService = new CommonService();

    public LiveData<TeamCombinationResponse> getTeamsCombinations(UserResponse aUserResponse, String type, String league_code) {
        mMatchData = mCommonService.getTeamCombinationResponseData(aUserResponse, type, league_code);
        return mMatchData;
    }

}
