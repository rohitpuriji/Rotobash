package com.tech.rotobash.interfaces;

import org.json.JSONObject;

public interface SocialMediaListners {
      void  loginSuccess(JSONObject jsonObject);
      void loginError(String error);
}
