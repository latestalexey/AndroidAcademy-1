package com.ciandt.cursoandroid.worldwondersapp.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ciandt.cursoandroid.worldwondersapp.entity.User;
import com.ciandt.cursoandroid.worldwondersapp.infrastructure.Constants;
import com.ciandt.cursoandroid.worldwondersapp.integrator.GeneralIntegrator;

public class LoginManager {

    private SharedPreferences mSharedPreferences;
    private GeneralIntegrator generalIntegrator;

    public LoginManager(final Context context) {
        // TODO: será implementado no futuro

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        generalIntegrator = new GeneralIntegrator(context);
    }

    public Boolean isUserLogged() {

        Boolean isUserLogged = Boolean.FALSE;

        // TODO: será implementado no futuro

        if( mSharedPreferences.contains(Constants.Bundle.BUNDLE_USER_EMAIL)){

            if( mSharedPreferences.getString(Constants.Bundle.BUNDLE_USER_EMAIL, null) != null){
                isUserLogged = Boolean.TRUE;
            }
        }

        return isUserLogged;
    }

    public User loginUser(final String userEmail, final String userPassword) {

        // TODO: será implementado no futuro
        User user = generalIntegrator.loginUserOnBackend(userEmail,userPassword);

        if(user != null){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(Constants.Bundle.BUNDLE_USER_EMAIL,userEmail);
            editor.putString(Constants.Bundle.BUNDLE_USER_PASSWORD,userPassword);
            editor.commit();
        }

        return user;
    }

    public void logoutUser() {

        // TODO: será implementado no futuro

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(Constants.Bundle.BUNDLE_USER_EMAIL);
        editor.remove(Constants.Bundle.BUNDLE_USER_PASSWORD);
        editor.commit();

    }

}