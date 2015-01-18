package com.ciandt.cursoandroid.worldwondersapp.adapter;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.ciandt.cursoandroid.worldwondersapp.activity.ScreenSlideDetailActivity;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.ciandt.cursoandroid.worldwondersapp.fragment.ScreenSlideDetailFragment;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private ScreenSlideDetailActivity screenSlideDetailActivity;
    private PlaceCursorAdapter mPlaceCursorAdapter;

    public ScreenSlidePagerAdapter(ScreenSlideDetailActivity screenSlideDetailActivity, FragmentManager fm, PlaceCursorAdapter placeCursorAdapter) {
        super(fm);
        this.screenSlideDetailActivity = screenSlideDetailActivity;
        this.mPlaceCursorAdapter = placeCursorAdapter;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

            Cursor cursor =  (Cursor) mPlaceCursorAdapter.getItem(position);
            if(cursor != null ) {
                Place place = new Place(cursor);

                if(place != null) {
                   return ScreenSlideDetailFragment.newInstance(place);
                }
            }
        return null;
    }

    @Override
    public int getCount() {
        return ScreenSlideDetailActivity.NUM_PAGES;
    }

}
