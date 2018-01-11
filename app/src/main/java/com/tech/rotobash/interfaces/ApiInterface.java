package com.tech.rotobash.interfaces;

import com.tech.rotobash.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by rohitpuri on 2/1/18.
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
                                     @Field("newpass") String newPassword );

}
