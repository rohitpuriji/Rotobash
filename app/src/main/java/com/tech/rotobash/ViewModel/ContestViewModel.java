package com.tech.rotobash.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.ContestRankResponse;
import com.tech.rotobash.model.UserResponse;

/**
 * Created by sombirbisht on 18/1/18.
 */

/**
 * @Module class/module		:	ContestViewModel
 * @Author Name             :	Sombir Bisht
 * @Date                    :	Jan 18 , 2018
 * @Purpose                 :	It contains methods to provide ranks related to contest
 */
public class ContestViewModel extends ViewModel {

    private LiveData<ContestRankResponse> mMatchData;

    private CommonService mCommonService = new CommonService();

    public LiveData<ContestRankResponse> getContestRankData(UserResponse aUserResponse, String contest_id) {
        mMatchData = mCommonService.getContestRankData(aUserResponse, contest_id);
        return mMatchData;
    }

}
