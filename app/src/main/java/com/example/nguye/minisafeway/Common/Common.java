package com.example.nguye.minisafeway.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.nguye.minisafeway.Model.User;

/**
 * Created by nguye on 11/4/2017.
 */

public class Common{
    public static User currentUser;

    public static final String DELETE = "Delete";

    public static  boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null){
                for(int i = 0; i < info.length; i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
