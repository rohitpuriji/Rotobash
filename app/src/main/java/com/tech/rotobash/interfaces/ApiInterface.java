package com.tech.rotobash.interfaces;

import com.tech.rotobash.model.LeaguesResponse;
import com.tech.rotobash.model.MatchContestsResponse;
import com.tech.rotobash.model.MatchesResponse;
import com.tech.rotobash.model.SeriesResponse;
import com.tech.rotobash.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @Module Interface        :	ApiInterface
 * @Author Name            :	Rohit Puri
 * @Date :	Jan 4st , 2018
 * @Purpose :	This interface defines the basic retrofit calls
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signin")
    Call<UserResponse> signin(@Field("email") String name,
                              @Field("password") String password,
                              @Field("device_token") String deviceToken,
                              @Field("device_type") String deviceType);

    @FormUrlEncoded
    @POST("signup")
    Call<UserResponse> signup(@Field("name") String name,
                              @Field("email") String email,
                              @Field("password") String password,
                              @Field("device_token") String deviceToken,
                              @Field("device_type") String deviceType);

    @FormUrlEncoded
    @POST("social_connect")
    Call<UserResponse> socialConnect(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("social_id") String socialId,
                                     @Field("social_type") String socialType,
                                     @Field("device_token") String deviceToken,
                                     @Field("device_type") String deviceType);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<UserResponse> forgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("resetpassword")
    Call<UserResponse> resetPassword(@Header("Authorization") String token,
                                     @Field("user_id") String userId,
                                     @Field("oldpass") String oldPassword,
                                     @Field("newpass") String newPassword);

    @FormUrlEncoded
    @POST("getMatches")
    Call<MatchesResponse> getMatches(@Header("Authorization") String token,
                                     @Field("series_id") String seriesId,
                                     @Field("type") String matchType,
                                     @Field("offset") String offset);

    @FormUrlEncoded
    @POST("getContest")
    Call<MatchContestsResponse> getMatchContests(@Header("Authorization") String token,
                                                 @Field("match_id") String matchId,
                                                 @Field("league_id") String leagueId,
                                                 @Field("offset") String offset,
                                                 @Field("limit") String aLimit);


    @FormUrlEncoded
    @POST("getFilters")
    Call<SeriesResponse> getFilters(@Header("Authorization") String token,
                                    @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("getLeagues")
    Call<LeaguesResponse> getLeague(@Header("Authorization") String token,
                                    @Field("user_id") String userId);


    @FormUrlEncoded
    @POST("logout")
    Call<UserResponse> doLogout(@Header("Authorization") String token,
                                    @Field("user_id") String userId);

}
