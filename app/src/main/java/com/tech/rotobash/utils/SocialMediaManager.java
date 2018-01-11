package com.tech.rotobash.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.tech.rotobash.interfaces.SocialMediaListners;

/**
 @Module class/module		:	SocialMediaManager
 @Author Name			    :	Rohit Puri
 @Date					    :	Jan 8 , 2018
 @Purpose				    :	It supports Callback method of Facebook login and call methods for Social media listener
 */
public class SocialMediaManager {

    private Activity activity;
    private SocialMediaListners socialMediaListners;

    public SocialMediaManager(Activity activity, SocialMediaListners socialMediaListners) {
        this.activity = activity;
        this.socialMediaListners = socialMediaListners;
    }

    public void initfb(CallbackManager callbackManager) {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> socialMediaListners.loginSuccess(object));

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(activity, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        socialMediaListners.loginError(exception.toString());
                        Log.e("Facebook Login Error", "exception " + exception);
                    }
                });
    }
}
