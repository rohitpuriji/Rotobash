package com.tech.rotobash.ViewModel;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.MatchContestsResponse;
import com.tech.rotobash.model.UserResponse;

/**
 * Created by sachinarora on 12/1/18.
 */

public class MatchContestsViewModel extends ViewModel {

    private LiveData<MatchContestsResponse> mLiveMatchContestsData;

    private CommonService mCommonService = new CommonService();

    public LiveData<MatchContestsResponse> getMatchContests(UserResponse mUserResponse, String aMatchId, String league_id, String price, String aOffset, String aLimit) {
        mLiveMatchContestsData = mCommonService.getMatchContests(mUserResponse, aMatchId, league_id, price, aOffset, aLimit);
        return mLiveMatchContestsData;
    }
}
