package com.tech.rotobash.ViewModel;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.UserResponse;

/**
 * Created by rohitpuri on 3/1/18.
 */

public class UserViewModel extends ViewModel {

    private LiveData<UserResponse> mLiveData;

    private CommonService mCommonService = new CommonService();

    public LiveData<UserResponse> getLiveSigninData(ProgressDialog progressDoalog,String aEmail,String aPassword,String aDeviceToken,String aDeviceType) {
            mLiveData = mCommonService.doUserSignin(progressDoalog,aEmail,aPassword,aDeviceToken,aDeviceType);
        return mLiveData;
    }

    public LiveData<UserResponse> getLiveSignupData(ProgressDialog progressDoalog,String aName,String aEmail,String aPassword,String aDeviceToken,String aDeviceType) {
        mLiveData = mCommonService.doUserSignup(progressDoalog,aName,aEmail,aPassword,aDeviceToken,aDeviceType);
        return mLiveData;
    }

    public LiveData<UserResponse> getSocialConnect(ProgressDialog progressDoalog, String aName, String aEmail, String aSocialType, String aSocialId, String aDeviceToken, String aDeviceType) {
        mLiveData = mCommonService.doSocialConnect(progressDoalog,aName,aEmail,aSocialType,aSocialId,aDeviceToken,aDeviceType);
        return mLiveData;
    }

    public LiveData<UserResponse> getForgetPassword(String aEmail) {
        mLiveData = mCommonService.doForgetPassword(aEmail);
        return mLiveData;
    }


    public LiveData<UserResponse> getResetPassword(ProgressDialog progressDoalog,UserResponse aUserResponse,String aOldPass, String aNewPass) {
        mLiveData = mCommonService.doResetPassword(progressDoalog,aUserResponse,aOldPass,aNewPass);
        return mLiveData;
    }
}
