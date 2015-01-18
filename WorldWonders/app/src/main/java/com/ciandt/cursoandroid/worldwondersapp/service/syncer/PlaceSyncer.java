package com.ciandt.cursoandroid.worldwondersapp.service.syncer;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.ciandt.cursoandroid.worldwondersapp.contentprovider.WorldWondersContentProvider;
import com.ciandt.cursoandroid.worldwondersapp.database.table.PlaceTable;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.ciandt.cursoandroid.worldwondersapp.infrastructure.Constants;
import com.ciandt.cursoandroid.worldwondersapp.listener.DatabaseOperatorCallback;
import com.ciandt.cursoandroid.worldwondersapp.listener.IntegratorOperatorCallback;
import com.ciandt.cursoandroid.worldwondersapp.manager.DatabaseManager;
import com.ciandt.cursoandroid.worldwondersapp.manager.PlaceManager;

import java.util.List;

/**
 * Created by Wellington on 09/12/2014.
 */
public class PlaceSyncer extends Syncer {

    private final String LOG_TAG = PlaceSyncer.class.getCanonicalName();
    PlaceManager mPlaceManager;
    DatabaseManager mDatabaseManager;

    public PlaceSyncer(Service context){
        mPlaceManager = new PlaceManager();
        mDatabaseManager = new DatabaseManager(context);
    }

    @Override
    public void sync(Intent intent) {

        final ResultReceiver receiver = intent.getParcelableExtra(Constants.Service.Tag.RESULT_RECEIVER);

        mPlaceManager.getAllPlace(new IntegratorOperatorCallback<List<Place>>() {
            @Override
            public void onOperationCompleteSuccess(List<Place> places) {

                ContentValues[] contValuesArray = prepareBulkInsertPlace(places);

                mDatabaseManager.bulkInsert(WorldWondersContentProvider.PLACE_CONTENT_URI, contValuesArray,
                        Boolean.TRUE, PlaceTable.deleteSelection,PlaceTable.deleteSelectionArgs, new DatabaseOperatorCallback<Integer>() {
                            @Override
                            public void onOperationSuccess(Integer integer) {

                                Log.i(LOG_TAG, "Count "+ String.valueOf(integer));

                                Bundle bundleExtras = new Bundle();

                                bundleExtras.putInt(Constants.Service.Tag.BULK_INSERT_COUNT,integer);

                                receiver.send(Constants.Service.Status.FINISHED, bundleExtras);
                            }
                        });
            }

            @Override
            public void onOperationCompleteError() {
                Bundle bundleExtras = new Bundle();
                bundleExtras.putString(Constants.Service.Tag.ERROR_MSG, "Fatal Error !!!!");
                receiver.send(Constants.Service.Status.ERROR, bundleExtras);
            }
        });
    }

    private ContentValues[] prepareBulkInsertPlace(final List<Place> placeList) {

        ContentValues[] contValuesArray = new ContentValues[placeList.size()];
        int i = 0;

        for (Place place : placeList) {

            ContentValues contValues = new ContentValues();

            contValues.put(PlaceTable.ID, place.id);
            contValues.put(PlaceTable.PLACE_NAME, place.name);
            contValues.put(PlaceTable.PLACE_COUNTRY, place.country);
            contValues.put(PlaceTable.PLACE_DESCRIPTION, place.description);
            contValues.put(PlaceTable.PLACE_URL, place.url);

            contValuesArray[i++] = contValues;
        }

        return contValuesArray;
    }


}
