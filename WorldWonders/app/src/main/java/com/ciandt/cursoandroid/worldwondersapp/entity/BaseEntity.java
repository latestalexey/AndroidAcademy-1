package com.ciandt.cursoandroid.worldwondersapp.entity;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseEntity {

    protected String getString(JSONObject jsonObject, String objectName) {
        String result = "";
        if (this.objectContainsEntry(jsonObject, objectName)) {
            try {
                if (!jsonObject.isNull(objectName)) {
                    result = jsonObject.getString(objectName);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected Integer getInteger(JSONObject jsonObject, String objectName) {
        Integer result = null;
        if (this.objectContainsEntry(jsonObject, objectName)) {
            try {
                if (!jsonObject.isNull(objectName)) {
                    result = jsonObject.getInt(objectName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected Boolean objectContainsEntry(JSONObject jsonObject, String objectName) {
        Boolean result = false;

        if (jsonObject != null && jsonObject.has(objectName)) {
            result = true;
        }

        return result;
    }

}