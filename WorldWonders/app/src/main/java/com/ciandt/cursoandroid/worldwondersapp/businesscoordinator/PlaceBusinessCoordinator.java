package com.ciandt.cursoandroid.worldwondersapp.businesscoordinator;

import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.ciandt.cursoandroid.worldwondersapp.integrator.PlaceIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wellington on 04/12/2014.
 */
public class PlaceBusinessCoordinator {

    public List<Place> getAllPlace() {

        String jsonString = new PlaceIntegrator().getAllPlace();

        List<Place> places = new ArrayList<Place>();

        try {
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObj.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                Place place = new Place(jsonArray.getJSONObject(i));
                places.add(place);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  places;
    }

}
