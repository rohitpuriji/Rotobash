package com.tech.rotobash.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tech.rotobash.model.UserResponse;

/**
 @Module class/module		:	AppPreferences
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 11 , 2018
 @Purpose				    :	This class contains app shared preferences only
 */
public class AppPreferences {

    private static final String NAME_KEY = "used_data";

    private static SharedPreferences mPref;
    private static SharedPreferences.Editor editor;
    private String mPrefName = "com.tech.rotobash.utils.AppPreferences";

    public AppPreferences(Context mContext) {
        mPref = mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE);
        editor=mPref.edit();
    }

    public boolean setPreferenceSocial(Boolean value) {
        editor.putBoolean("social", value);
        return editor.commit();
    }

    public boolean getPreferenceSocial() {
        Boolean value = mPref.getBoolean("social", false);
        return value;
    }

    public boolean setPreferenceBoolean(Boolean value) {
        editor.putBoolean("value", value);
        return editor.commit();
    }

    public boolean getPreferenceBoolean() {
        Boolean value = mPref.getBoolean("value", false);
        return value;
    }

    public void setUserData(UserResponse aUserResponse){
        Gson gson = new Gson();
        String json = gson.toJson(aUserResponse);
        editor.putString("user", json);
        editor.apply();
    }
    public UserResponse getUserData(){
        Gson gson = new Gson();
        String json = mPref.getString("user", "");
        UserResponse user = gson.fromJson(json, UserResponse.class);
        return user;
    }

    public void clearAll(){
        SharedPreferences.Editor editor = mPref.edit();
        editor.clear();
        editor.commit();
    }

}