package com.tech.rotobash.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.SeriesResponse;
import com.tech.rotobash.model.UserResponse;

/**
 * Created by rohitpuri on 15/1/18.
 */

public class SeriesViewModel extends ViewModel {

    private LiveData<SeriesResponse> mSeriesData;

    private CommonService mCommonService = new CommonService();

    public LiveData<SeriesResponse> getSeries(UserResponse aUserResponse) {
        mSeriesData = mCommonService.getFilters(aUserResponse);
        return mSeriesData;
    }

}
