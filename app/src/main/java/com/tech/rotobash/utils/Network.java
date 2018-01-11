/*
 * Network.java 
 *
 * Copyright  2014 iHealth Technologies Private Limited. All rights reserved.
 *
 * This software is the confidential and proprietary information of iHealth Technologies Private Limited.
 * ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with iSpace.
 *
 */

package com.tech.rotobash.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network
{
    public static boolean isAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo != null)
        {
            return activeNetworkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
