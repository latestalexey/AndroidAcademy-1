package com.ciandt.cursoandroid.worldwondersapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.infrastructure.NetworkUtil;
import com.ciandt.cursoandroid.worldwondersapp.service.syncer.PlaceSyncer;

/**
 * Created by Wellington on 09/12/2014.
 */
public class SyncService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(NetworkUtil.isConnectionAvailable(this.getBaseContext())){
            PlaceSyncer placeSyncer = new PlaceSyncer(this);
            placeSyncer.sync(intent);
        }else{
            Toast.makeText(getBaseContext(),getResources().getText(R.string.no_connection_msg),Toast.LENGTH_LONG).show();
        }

        return this.START_NOT_STICKY;
    }
}
