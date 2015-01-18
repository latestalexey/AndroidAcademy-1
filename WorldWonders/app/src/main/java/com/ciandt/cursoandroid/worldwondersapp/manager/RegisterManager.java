package com.ciandt.cursoandroid.worldwondersapp.manager;

import android.content.Context;

import com.ciandt.cursoandroid.worldwondersapp.entity.User;
import com.ciandt.cursoandroid.worldwondersapp.integrator.GeneralIntegrator;

public class RegisterManager {

    private GeneralIntegrator generalIntegrator;

    public RegisterManager(final Context context) {
        generalIntegrator = new GeneralIntegrator(context);
    }

    public Boolean registerUser(final User user) {
        return generalIntegrator.registerUserOnBackend(user);
    }
}