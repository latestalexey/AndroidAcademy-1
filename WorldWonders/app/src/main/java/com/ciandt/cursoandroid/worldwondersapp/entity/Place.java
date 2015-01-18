package com.ciandt.cursoandroid.worldwondersapp.entity;

import android.database.Cursor;

import com.ciandt.cursoandroid.worldwondersapp.database.table.PlaceTable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Wellington on 11/11/2014.
 */
public class Place extends BaseEntity implements Serializable {

    public Integer id;
    public String name;
    public String country;
    public String description;
    public String url;

    public Place(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(PlaceTable.ID));
        this.name = cursor.getString(cursor.getColumnIndex(PlaceTable.PLACE_NAME));
        this.country = cursor.getString(cursor.getColumnIndex(PlaceTable.PLACE_COUNTRY));
        this.description = cursor.getString(cursor.getColumnIndex(PlaceTable.PLACE_DESCRIPTION));
        this.url = cursor.getString(cursor.getColumnIndex(PlaceTable.PLACE_URL));
    }

    public Place(final JSONObject jsonObject) {
        this.id =  this.getInteger(jsonObject, "id");
        this.name =  this.getString(jsonObject, "name");
        this.country =  this.getString(jsonObject, "country");
        this.description =  this.getString(jsonObject, "description");
        this.url =  this.getString(jsonObject, "image_url");
    }


    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(id).append(" ").append(name).append(" ").
                append(country).append(" ").append(description).
                append(" ").append(url);
        return strBuf.toString();

    }
}
