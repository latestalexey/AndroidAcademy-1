package com.ciandt.cursoandroid.worldwondersapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.ciandt.cursoandroid.worldwondersapp.database.Database;
import com.ciandt.cursoandroid.worldwondersapp.database.table.PlaceTable;

public class WorldWondersContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.ciandt.cursoandroid.worldwondersapp";
    private static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    private static UriMatcher URI_MATCHER;
    private Database database;

    // References of DIR and ID
    private static final int PLACE_DIR = 0;
    private static final int PLACE_ID = 1;

    // Configuration of UriMatcher
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(AUTHORITY, PlaceContent.CONTENT_PATH, PLACE_DIR);
        URI_MATCHER.addURI(AUTHORITY, PlaceContent.CONTENT_PATH + "/#", PLACE_ID);
    }

    // Content URIs
    public static final Uri PLACE_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PlaceContent.CONTENT_PATH);

    // Content Information of Entities
    /**
     * Provides the content information of the PlaceTable.
     */
    private static final class PlaceContent implements BaseColumns {

        /**
         * Specifies the content path of the PostTable for the required Uri.
         */
        public static final String CONTENT_PATH = "place";

        /**
         * Sepecifies the type for the folder and the single item of the PlaceTable.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.mdsdacp.place";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.mdsdacp.place";
    }

    /**
     * Instantiate the database, when the content provider is created.
     *
     * @return true
     */
    @Override
    public final boolean onCreate() {
        database = new Database(getContext());
        return true;
    }

    /**
     * Provide information whether uri returns an item or an directory.
     *
     * @param uri
     *
     * @return content type from type Content.CONTENT_TYPE or Content.CONTENT_ITEM_TYPE
     */
    @Override
    public final String getType(Uri uri) {

        switch (URI_MATCHER.match(uri)) {

            case PLACE_DIR:
                return PlaceContent.CONTENT_TYPE;
            case PLACE_ID:
                return PlaceContent.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    /**
     * Execute a query on a given uri and return a Cursor with results.
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     *
     * @return Cursor with results
     */
    @Override
    public final Cursor query(Uri uri, String[] projection, String selection,
                              String[] selectionArgs, String sortOrder) {

        SQLiteDatabase dbConnection = database.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (URI_MATCHER.match(uri)) {

            case PLACE_DIR:
                queryBuilder.setTables(PlaceTable.TABLE_NAME);
                break;
            case PLACE_ID:
                queryBuilder.appendWhere(PlaceTable.ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(dbConnection, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert given values to given uri.
     *
     * @param uri
     * @param values from type ContentValues
     *
     * @return uri of inserted element from type Uri
     */
    @Override
    public final Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase dbConnection = database.getWritableDatabase();

        try {
            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {

                case PLACE_DIR:
                case PLACE_ID:
                    Long postId = dbConnection.insertOrThrow(PlaceTable.TABLE_NAME, null, values);
                    Uri newPlace = ContentUris.withAppendedId(PLACE_CONTENT_URI, postId);
                    getContext().getContentResolver().notifyChange(newPlace, null);
                    dbConnection.setTransactionSuccessful();
                    return newPlace;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        } finally {
            dbConnection.endTransaction();
        }
    }

    /**
     * Update given values of given uri, returning number of affected rows.
     *
     * @param uri
     * @param values from type ContentValues
     * @param selection
     * @param selectionArgs
     *
     * @return number of affected rows
     */
    @Override
    public final int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase dbConnection = database.getWritableDatabase();
        int updateCount = 0;

        try {

            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {

                case PLACE_DIR:
                    updateCount = dbConnection.update(PlaceTable.TABLE_NAME, values, selection, selectionArgs);
                    dbConnection.setTransactionSuccessful();
                    break;
                case PLACE_ID:
                    Long postId = ContentUris.parseId(uri);
                    updateCount = dbConnection.update(PlaceTable.TABLE_NAME, values,
                            PlaceTable.ID + "=" + postId + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"),
                            selectionArgs);
                    dbConnection.setTransactionSuccessful();
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        } finally {
            dbConnection.endTransaction();
        }

        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updateCount;
    }

    /**
     * Delete given elements by their uri and return the number of deleted rows.
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     *
     * @return number of deleted rows
     */
    @Override
    public final int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase dbConnection = database.getWritableDatabase();
        int deleteCount = 0;

        try {

            dbConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {

                case PLACE_DIR:
                    deleteCount = dbConnection.delete(PlaceTable.TABLE_NAME, selection, selectionArgs);
                    dbConnection.setTransactionSuccessful();
                    break;
                case PLACE_ID:
                    deleteCount = dbConnection.delete(PlaceTable.TABLE_NAME, PlaceTable.WHERE_ID_EQUALS, new String[]{uri.getPathSegments().get(1)});
                    dbConnection.setTransactionSuccessful();
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }

        } finally {
            dbConnection.endTransaction();
        }

        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteCount;
    }

    /**
     * Insert a batch given contValues to given uri.
     *
     * @param uri
     * @param contValues
     *
     * @return number of inserted rows
     */
    @Override
    public final int bulkInsert(Uri uri, ContentValues[] contValues) {

        SQLiteDatabase dbConnection = database.getWritableDatabase();
        String table = null;
        int insertCount = 0;

        try {

            dbConnection.beginTransaction();

            int matchUri = URI_MATCHER.match(uri);

            // delete table
            switch (matchUri) {

                case PLACE_DIR:
                case PLACE_ID:
                    table = PlaceTable.TABLE_NAME;
                    dbConnection.delete(table, null, null);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }

            // insert the batch values
            for (ContentValues contValue : contValues) {
                dbConnection.insert(table, null, contValue);
                insertCount++;
            }

            // notify the UI when necessary
            switch (matchUri) {

                case PLACE_DIR:
                case PLACE_ID:
                    dbConnection.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        } finally {
            dbConnection.endTransaction();
        }

        return insertCount;
    }
}