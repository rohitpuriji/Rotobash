package com.tech.rotobash.RetrofitServices;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tech.rotobash.activites.AuthenticationActivity;
import com.tech.rotobash.interfaces.ApiInterface;
import com.tech.rotobash.model.MatchesResponse;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tech.rotobash.utils.AppConstant.sMainUrl;

/**
 @Module class/module		:	CommonService
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 3 , 2018
 @Purpose				    :	This class defines Retrofit service to support MVVM arch
 */
public class CommonService {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        Gson gson = new GsonBuilder() .setLenient() .create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(sMainUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     @Module class/module		:	doUserSignin
     @Author Name			    :	Rohit Puri
     @Date					    :	Jan 3 , 2018
     @Purpose				    :	This method return the MutableLiveData for login
     */
    public LiveData<UserResponse> doUserSignin(ProgressDialog progressDoalog,String aEmail, String aPassword,String aDeviceToken, String aDeviceType) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .signin(aEmail,aPassword,aDeviceToken,aDeviceType)
                .enqueue(new Callback<UserResponse>(){

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.e("on response :",response.body().getMessage());
                if (progressDoalog.isShowing()) {
                    progressDoalog.dismiss();
                }
                UserResponse userResponse = response.body();
                liveUserResponse.setValue(userResponse);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("on Failure :","retrofit error");
                if (progressDoalog.isShowing()) {
                    progressDoalog.dismiss();
                }
            }
        });

        return liveUserResponse;
    }

    /**
     @Module class/module		:	doUserSignup
     @Author Name			    :	Rohit Puri
     @Date					    :	Jan 5 , 2018
     @Purpose				    :	This method return the MutableLiveData for sign up
     */
    public LiveData<UserResponse> doUserSignup(ProgressDialog progressDoalog,String aName,String aEmail, String aPassword,String aDeviceToken, String aDeviceType) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .signup(aName,aEmail,aPassword,aDeviceToken,aDeviceType)
                .enqueue(new Callback<UserResponse>(){

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :",response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :","retrofit error");
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }


    /**
     @Module class/module		:	doSocialConnect
     @Author Name			    :	Rohit Puri
     @Date					    :	Jan 9 , 2018
     @Purpose				    :	This method return the MutableLiveData for social connect api
     */
    public LiveData<UserResponse> doSocialConnect(ProgressDialog progressDoalog,String aName, String aEmail, String socialType, String socialId, String aDeviceToken, String aDeviceType) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .socialConnect(aName,aEmail,socialId,socialType,aDeviceToken,aDeviceType)
                .enqueue(new Callback<UserResponse>(){

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :",response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :","retrofit error"+t.getLocalizedMessage());
                        Log.e("on Failure :","retrofit error"+t.getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }

    /**
     @Module class/module		:	doForgetPassword
     @Author Name			    :	Rohit Puri
     @Date					    :	Jan 9 , 2018
     @Purpose				    :	This method return the MutableLiveData for forget password api
     */
    public LiveData<UserResponse> doForgetPassword(String aEmail) {


        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .forgetPassword(aEmail)
                .enqueue(new Callback<UserResponse>(){

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :",response.body().getMessage());
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :","retrofit error"+t.getLocalizedMessage());
                    }
                });

        return liveUserResponse;
    }

    /**
     @Module class/module		:	doResetPassword
     @Author Name			    :	Rohit Puri
     @Date					    :	Jan 11 , 2018
     @Purpose				    :	This method return the MutableLiveData for reset password api
     */
    public LiveData<UserResponse> doResetPassword(ProgressDialog progressDoalog,UserResponse aUserResponse,String oldass, String newPass) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }
        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .resetPassword(aUserResponse.getResponse().getAccessToken(),aUserResponse.getResponse().getUserId(),oldass,newPass )
                .enqueue(new Callback<UserResponse>(){

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :",response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :","retrofit error"+t.getLocalizedMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }

    /**
     @Module class/module		:	getMatches
     @Author Name			    :	Rohit Puri
     @Date					    :	Jan 11 , 2018
     @Purpose				    :	This method return the MutableLiveData for get Matches api
     */
    public LiveData<MatchesResponse> getMatches(ProgressDialog progressDoalog,UserResponse aUserResponse,String aType,int aOffset) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }
        final MutableLiveData<MatchesResponse> liveMatchResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .getMatches(aUserResponse.getResponse().getAccessToken(),aUserResponse.getResponse().getUserId(),aType,aOffset )
                .enqueue(new Callback<MatchesResponse>(){

                    @Override
                    public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> aMatchesResponse) {
                        Log.e("on response :",aMatchesResponse.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        MatchesResponse matchesResponse = aMatchesResponse.body();
                        liveMatchResponse.setValue(matchesResponse);
                    }

                    @Override
                    public void onFailure(Call<MatchesResponse> call, Throwable t) {
                        Log.e("on Failure :","retrofit error"+t.getLocalizedMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveMatchResponse;
    }

}
