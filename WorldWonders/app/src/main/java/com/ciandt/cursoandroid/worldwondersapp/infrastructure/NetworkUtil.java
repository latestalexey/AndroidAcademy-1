package com.ciandt.cursoandroid.worldwondersapp.infrastructure;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

    public static boolean isConnectionAvailable(final Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null) {

            boolean connectionAvailable = connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
            return connectionAvailable;
        } else {
            return false;
        }
    }
}
