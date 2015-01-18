package com.ciandt.cursoandroid.worldwondersapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.adapter.PlaceCursorAdapter;
import com.ciandt.cursoandroid.worldwondersapp.contentprovider.WorldWondersContentProvider;
import com.ciandt.cursoandroid.worldwondersapp.database.table.PlaceTable;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.ciandt.cursoandroid.worldwondersapp.manager.DatabaseManager;

public class PlaceListFragment extends Fragment{

    private Activity mActivity;
    private DatabaseManager mDatabaseManager;
    private ListView mListView;
    private PlaceCursorAdapter mPlaceCursorAdapter;
    private OnPlaceSelectedListener mListener;

    public PlaceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_place_list, container, false);

        mPlaceCursorAdapter  = new PlaceCursorAdapter(mActivity, null);

        mListView = (ListView) view.findViewById(R.id.listview_place_list_fragment);

        mListView.setAdapter(mPlaceCursorAdapter);

        mDatabaseManager = new DatabaseManager(mActivity);

        loadDd();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor =  (Cursor) mListView.getAdapter().getItem(position);
                Place place = new Place(cursor);

                Boolean isTablet = getResources().getBoolean(R.bool.tablet);
                if(isTablet){
                    mListener.onPlaceSelected(place);
                }else{
                    mListener.onPlaceSelected(place, position, mListView.getCount());
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mListener = (OnPlaceSelectedListener) activity;

    }

    private void loadDd(){

        getLoaderManager().initLoader(0, null, new
                LoaderManager.LoaderCallbacks<Cursor>() {

                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        CursorLoader cursorLoader =
                                mDatabaseManager.query(WorldWondersContentProvider.PLACE_CONTENT_URI,
                                        PlaceTable.ALL_COLUMNS, null, null, null);
                        return cursorLoader;
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                        // Swap the new cursor in. (The framework will take care of closing
                        // the old cursor once we return.)
                        mPlaceCursorAdapter.swapCursor(data);

                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                        mPlaceCursorAdapter.swapCursor(null);
                    }
                });
    }

    public interface OnPlaceSelectedListener {
        public void onPlaceSelected(Place place);
        public void onPlaceSelected(Place place, int position ,int count);
    }

}
