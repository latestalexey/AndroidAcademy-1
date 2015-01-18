package com.ciandt.cursoandroid.worldwondersapp.manager;

import android.os.AsyncTask;

import com.ciandt.cursoandroid.worldwondersapp.businesscoordinator.PlaceBusinessCoordinator;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.ciandt.cursoandroid.worldwondersapp.listener.IntegratorOperatorCallback;

import java.util.List;

/**
 * Created by Wellington on 04/12/2014.
 */
public class PlaceManager  {

    PlaceBusinessCoordinator mPlaceBusinessCoordinator;

    public PlaceManager(){
        mPlaceBusinessCoordinator = new PlaceBusinessCoordinator();
    }

    public void getAllPlace(final IntegratorOperatorCallback<List<Place>> callback){

        new AsyncTask<Void, Void, List<Place>>() {
            @Override
            protected List<Place> doInBackground(Void... params) {
                // do something hard
                return mPlaceBusinessCoordinator.getAllPlace();
            }

            @Override
            protected void onPostExecute(List<Place> resultList) {
                super.onPostExecute(resultList);
                if (resultList != null) {
                    callback.onOperationCompleteSuccess(resultList);
                } else {
                    callback.onOperationCompleteError();
                }
            }
        }.execute();
    }
}
