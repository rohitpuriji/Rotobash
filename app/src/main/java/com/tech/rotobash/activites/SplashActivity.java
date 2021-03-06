package com.tech.rotobash.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tech.rotobash.R;
import com.tech.rotobash.model.UserResponse;
import com.tech.rotobash.utils.AppPreferences;

import static com.tech.rotobash.utils.AppConstant.sUserResponseKey;

/**
 * @Module Name/Class		:	SplashActivity
 * @Author Name            :	Rohit Puri
 * @Date :	Jan 1st , 2018
 * @Purpose :	This class contains the splash screen
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private Intent i;
    private AppPreferences mAppPreferences;
    private UserResponse mUserResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAppPreferences = new AppPreferences(SplashActivity.this);
        mUserResponse = mAppPreferences.getUserData();
        new Handler().postDelayed(() -> {

            if (mAppPreferences.getPreferenceBoolean()) {
                i = new Intent(SplashActivity.this, MatchListActivity.class);
                i.putExtra(sUserResponseKey, mUserResponse);

            } else {
                i = new Intent(SplashActivity.this, AuthenticationActivity.class);
            }
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);

    }
}
