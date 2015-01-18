package com.ciandt.cursoandroid.worldwondersapp.integrator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ciandt.cursoandroid.worldwondersapp.entity.User;

public class GeneralIntegrator {

    private SharedPreferences mDBSP;
    private String BUNDLE_USER_NAME_DB = "user_name_db";
    private String BUNDLE_USER_EMAIL_DB = "user_email_db";
    private String BUNDLE_USER_PASSWORD_DB = "user_password_db";

    public GeneralIntegrator(final Context context) {
        mDBSP = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public User loginUserOnBackend(final String userEmail, final String userPassword) {

        String userNameDB = mDBSP.getString(BUNDLE_USER_NAME_DB, null);
        String userEmailDB = mDBSP.getString(BUNDLE_USER_EMAIL_DB, null);
        String userPasswordDB = mDBSP.getString(BUNDLE_USER_PASSWORD_DB, null);

        if (userEmailDB != null) {

            if (userEmailDB.equals(userEmail) && userPasswordDB.equals(userPassword)) {

                User user = new User();
                user.setUserName(userNameDB);
                user.setUserEmail(userEmailDB);
                return user;
            }
        }

        return null;
    }

    public Boolean registerUserOnBackend(final User user) {

        SharedPreferences.Editor editor = mDBSP.edit();
        editor.putString(BUNDLE_USER_NAME_DB, user.getUserName());
        editor.putString(BUNDLE_USER_EMAIL_DB, user.getUserEmail());
        editor.putString(BUNDLE_USER_PASSWORD_DB, user.getUserPassword());
        editor.commit();

        return Boolean.TRUE;
    }
}