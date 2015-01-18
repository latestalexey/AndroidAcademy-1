package com.ciandt.cursoandroid.worldwondersapp.manager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;
import android.os.AsyncTask;

import com.ciandt.cursoandroid.worldwondersapp.listener.DatabaseOperatorCallback;

/**
 * Created by Wellington on 11/11/2014.
 */
public class DatabaseManager {

    private Context mContext;
    private ContentResolver contentResolver;

    public DatabaseManager(Context context) {
        mContext = context;
        contentResolver = mContext.getContentResolver();
    }

    public long insert(final Uri uri, final ContentValues contValues) {

        Uri newPlace = contentResolver.insert(uri, contValues);

        return ContentUris.parseId(newPlace);
    }

    public void bulkInsert(final Uri uri,
                           final ContentValues[] contValues,
                           final boolean deleteBeforeInsert,
                           final String deleteSelection,
                           final String[] deleteSelectionArgs,
                           final DatabaseOperatorCallback<Integer> callback) {

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                // do something hard
                Integer result = 0;

                if (deleteBeforeInsert) {
                    contentResolver.delete(uri, deleteSelection, deleteSelectionArgs);
                    result = contentResolver.bulkInsert(uri, contValues);
                }

                return result;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                    callback.onOperationSuccess(result);
            }
        }.execute();
    }

    public CursorLoader query(final Uri uri, final String[] projection, final String selection,
                              final String[] selectionArgs, final String sortOrder) {

        CursorLoader cursorLoader = new CursorLoader(mContext, uri, projection, selection, selectionArgs, sortOrder);

        return cursorLoader;
    }

}
