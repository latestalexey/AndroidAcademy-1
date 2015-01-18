package com.ciandt.cursoandroid.worldwondersapp.database.table;

/**
 * Created by Wellington on 11/11/2014.
 */
public interface PlaceTable {

    String TABLE_NAME = "place";

    String ID = "_id";

    String PLACE_NAME = "place_name";

    String PLACE_COUNTRY = "place_country";

    String PLACE_DESCRIPTION = "place_description";

    String PLACE_URL = "place_url";

    String WHERE_ID_EQUALS = "name = ?";

    String[] ALL_COLUMNS = {ID, PLACE_NAME, PLACE_COUNTRY, PLACE_DESCRIPTION, PLACE_URL} ;


     String deleteSelection = null;
     String[] deleteSelectionArgs = {};

    String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
            + "," + PLACE_NAME + " TEXT "
            + "," + PLACE_COUNTRY + " TEXT "
            + "," + PLACE_DESCRIPTION + " TEXT "
            + "," + PLACE_URL + " TEXT " + ")";

}
