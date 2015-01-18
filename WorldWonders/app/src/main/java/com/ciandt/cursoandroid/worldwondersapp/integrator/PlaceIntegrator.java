package com.ciandt.cursoandroid.worldwondersapp.integrator;

import com.ciandt.cursoandroid.worldwondersapp.infrastructure.Constants;

/**
 * Created by Wellington on 04/12/2014.
 */
public class PlaceIntegrator extends BaseIntegrator {

    public  String getAllPlace(){

       return doGetRequest(Constants.HTTP_PROTOCOL, Constants.Integrator.WorldWondersApi.HOST, Constants.Integrator.WorldWondersApi.WORLD_WONDERS_LIST);
    }
}
