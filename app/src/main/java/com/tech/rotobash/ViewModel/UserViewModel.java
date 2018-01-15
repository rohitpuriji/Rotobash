package com.tech.rotobash.ViewModel;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tech.rotobash.RetrofitServices.CommonService;
import com.tech.rotobash.model.UserResponse;

/**
 @Module class/module		:	FieldValidations
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 8 , 2018
 @Purpose				    :	It contains methods to provide service for respective view
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


    public LiveData<UserResponse> doLogout(ProgressDialog progressDoalog,UserResponse aUserResponse) {
        mLiveData = mCommonService.doLogout(progressDoalog,aUserResponse);
        return mLiveData;
    }

}
