package com.tech.rotobash.interfaces;

import org.json.JSONObject;

/**
 @Module Interface		:	SocialMediaListners
 @Author Name			:	Rohit Puri
 @Date					:	Jan 4st , 2018
 @Purpose				:	This interface defines the call back method for social media manger class
 */

public interface SocialMediaListners {
      void  loginSuccess(JSONObject jsonObject);
      void loginError(String error);
}
