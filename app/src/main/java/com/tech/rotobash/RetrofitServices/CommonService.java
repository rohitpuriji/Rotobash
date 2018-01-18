package com.tech.rotobash.RetrofitServices;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tech.rotobash.interfaces.ApiInterface;
import com.tech.rotobash.model.LeaguesResponse;
import com.tech.rotobash.model.MatchContestsResponse;
import com.tech.rotobash.model.MatchesResponse;
import com.tech.rotobash.model.SeriesResponse;
import com.tech.rotobash.model.UserResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tech.rotobash.utils.AppConstant.sMainUrl;

/**
 * @Module class/module		:	CommonService
 * @Author Name                :	Rohit Puri
 * @Date :	Jan 3 , 2018
 * @Purpose :	This class defines Retrofit service to support MVVM arch
 */
public class CommonService {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(sMainUrl)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     * @Module class/module		:	doUserSignin
     * @Author Name                :	Rohit Puri
     * @Date :	Jan 3 , 2018
     * @Purpose :	This method return the MutableLiveData for login
     */
    public LiveData<UserResponse> doUserSignin(ProgressDialog progressDoalog, String aEmail, String aPassword, String aDeviceToken, String aDeviceType) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .signin(aEmail, aPassword, aDeviceToken, aDeviceType)
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :", response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error");
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }

    /**
     * @Module class/module		:	doUserSignup
     * @Author Name                :	Rohit Puri
     * @Date :	Jan 5 , 2018
     * @Purpose :	This method return the MutableLiveData for sign up
     */
    public LiveData<UserResponse> doUserSignup(ProgressDialog progressDoalog, String aName, String aEmail, String aPassword, String aDeviceToken, String aDeviceType) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .signup(aName, aEmail, aPassword, aDeviceToken, aDeviceType)
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :", response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error");
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }


    /**
     * @Module class/module		:	doSocialConnect
     * @Author Name                :	Rohit Puri
     * @Date :	Jan 9 , 2018
     * @Purpose :	This method return the MutableLiveData for social connect api
     */
    public LiveData<UserResponse> doSocialConnect(ProgressDialog progressDoalog, String aName, String aEmail, String socialType, String socialId, String aDeviceToken, String aDeviceType) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }

        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .socialConnect(aName, aEmail, socialId, socialType, aDeviceToken, aDeviceType)
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :", response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                        Log.e("on Failure :", "retrofit error" + t.getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }

    /**
     * @Module class/module		    :	doForgetPassword
     * @Author Name                 :	Rohit Puri
     * @Date                        :	Jan 9 , 2018
     * @Purpose                     :	This method return the MutableLiveData for forget password api
     */
    public LiveData<UserResponse> doForgetPassword(String aEmail) {


        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .forgetPassword(aEmail)
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :", response.body().getMessage());
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                    }
                });

        return liveUserResponse;
    }

    /**
     * @Module class/module		    :	doResetPassword
     * @Author Name                 :	Rohit Puri
     * @Date                        :	Jan 11 , 2018
     * @Purpose                     :	This method return the MutableLiveData for reset password api
     */
    public LiveData<UserResponse> doResetPassword(ProgressDialog progressDoalog, UserResponse aUserResponse, String oldass, String newPass) {

        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }
        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .resetPassword(aUserResponse.getResponse().getAccessToken(), aUserResponse.getResponse().getUserId(), oldass, newPass)
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        Log.e("on response :", response.body().getMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        UserResponse userResponse = response.body();
                        liveUserResponse.setValue(userResponse);
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }
                });

        return liveUserResponse;
    }

    /**
     * @Module class/module		    :	getMatches
     * @Author Name                 :	Rohit Puri
     * @Date                        :	Jan 11 , 2018
     * @Purpose                     :	This method return the MutableLiveData for get Matches api
     */
    public LiveData<MatchesResponse> getMatches(String aSeriesid, UserResponse aUserResponse, String aType, String aOffset) {


        final MutableLiveData<MatchesResponse> liveMatchResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .getMatches(aUserResponse.getResponse().getAccessToken(), aSeriesid, aType, aOffset)
                .enqueue(new Callback<MatchesResponse>() {

                    @Override
                    public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> aMatchesResponse) {
                        Log.e("on getAccessToken :", aUserResponse.getResponse().getAccessToken());
                        Log.e("on getUserId :", aUserResponse.getResponse().getUserId());
                        Log.e("on type :", aType);
                        Log.e("on offset :", aOffset);
                        Log.e("on response :", aMatchesResponse.body().getStatus());
                        Log.e("on response :", aMatchesResponse.body().getMessage());
                        MatchesResponse matchesResponse = aMatchesResponse.body();
                        liveMatchResponse.setValue(matchesResponse);
                    }

                    @Override
                    public void onFailure(Call<MatchesResponse> call, Throwable t) {

                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                    }
                });

        return liveMatchResponse;
    }

    /**
     * @Module class/module		:	getMatchContests
     * @Author Name                :	Rohit Puri
     * @Date :	Jan 11 , 2018
     * @Purpose :	This method return the MutableLiveData for get Matches api
     */
    public LiveData<MatchContestsResponse> getMatchContests(UserResponse aUserResponse, String aMatchId, String aLeagueId, String price, String aOffset, String aLimit) {

        final MutableLiveData<MatchContestsResponse> matchContestsResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .getMatchContests(aUserResponse.getResponse().getAccessToken(), aMatchId, aLeagueId, price,aOffset, aLimit)
                .enqueue(new Callback<MatchContestsResponse>() {

                    @Override
                    public void onResponse(Call<MatchContestsResponse> call, Response<MatchContestsResponse> aMatchesResponse) {
                        Log.e("on getAccessToken :", aUserResponse.getResponse().getAccessToken());
                        Log.e("on getUserId :", aUserResponse.getResponse().getUserId());
                        Log.e("on offset :", aOffset);

                        MatchContestsResponse matcheContestsResponse = aMatchesResponse.body();
                        matchContestsResponse.setValue(matcheContestsResponse);
                    }

                    @Override
                    public void onFailure(Call<MatchContestsResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                    }
                });

        return matchContestsResponse;
    }

    /**
     * @Module class/module		    :	getMatchContests
     * @Author Name                 :	Rohit Puri
     * @Date                        :	Jan 11 , 2018
     * @Purpose                     :	This method return the MutableLiveData for get Matches api
     */
    public LiveData<SeriesResponse> getFilters(UserResponse aUserResponse) {

        final MutableLiveData<SeriesResponse> liveSeriesRespose = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .getFilters(aUserResponse.getResponse().getAccessToken(), aUserResponse.getResponse().getUserId())
                .enqueue(new Callback<SeriesResponse>() {

                    @Override
                    public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> aSeriesResponse) {
                        Log.e("on getAccessToken :", aUserResponse.getResponse().getAccessToken());
                        Log.e("on response :", aSeriesResponse.body().getStatus());
                        Log.e("on response :", new Gson().toJson(aSeriesResponse));

                        SeriesResponse seriesResponse = aSeriesResponse.body();
                        liveSeriesRespose.setValue(seriesResponse);
                    }

                    @Override
                    public void onFailure(Call<SeriesResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                        t.getStackTrace();
                    }
                });

        return liveSeriesRespose;
    }

    /**
     * @Module class/module		    :	doLogout
     * @Author Name                 :	Rohit Puri
     * @Date                        :	Jan 15 , 2018
     * @Purpose                     :	This method return the MutableLiveData for logout api
     */
    public LiveData<UserResponse> doLogout(ProgressDialog progressDoalog, UserResponse aUserResponse) {
        if (!progressDoalog.isShowing()) {
            progressDoalog.show();
        }
        final MutableLiveData<UserResponse> liveUserResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .doLogout(aUserResponse.getResponse().getAccessToken(), aUserResponse.getResponse().getAccessToken())
                .enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> aSeriesResponse) {
                        Log.e("on response :", aSeriesResponse.body().getStatus());
                        UserResponse seriesResponse = aSeriesResponse.body();
                        liveUserResponse.setValue(seriesResponse);
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                    }
                });

        return liveUserResponse;
    }

    /**
     * @Module class/module		    :	getMatchContests
     * @Author Name                 :	Sachin Arora
     * @Date                        :	Jan 11 , 2018
     * @Purpose                     :	This method return the MutableLiveData for get Matches api
     */
    public LiveData<LeaguesResponse> getLeague( UserResponse aUserResponse) {

        final MutableLiveData<LeaguesResponse> leaguesResponse = new MutableLiveData<>();

        getRetrofitClient()
                .create(ApiInterface.class)
                .getLeague(aUserResponse.getResponse().getAccessToken(), aUserResponse.getResponse().getUserId())
                .enqueue(new Callback<LeaguesResponse>() {

                    @Override
                    public void onResponse(Call<LeaguesResponse> call, Response<LeaguesResponse> aSeriesResponse) {

                        LeaguesResponse response = aSeriesResponse.body();
                        leaguesResponse.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<LeaguesResponse> call, Throwable t) {
                        Log.e("on Failure :", "retrofit error" + t.getLocalizedMessage());
                        t.getStackTrace();
                    }
                });

        return leaguesResponse;
    }
}
