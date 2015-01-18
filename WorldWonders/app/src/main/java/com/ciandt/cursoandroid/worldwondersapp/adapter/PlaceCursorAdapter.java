package com.ciandt.cursoandroid.worldwondersapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by Wellington on 25/11/2014.
 */
public class PlaceCursorAdapter extends CursorAdapter {

    public PlaceCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        LayoutInflater inflater =
                LayoutInflater.from(viewGroup.getContext());

        View newView = inflater.inflate(R.layout.main_feed_item,
                viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(newView);
        newView.setTag(viewHolder);

        return newView;
    }

    @Override
    public void bindView(final View view, Context context, final Cursor cursor) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        final Place place = new Place(cursor);
        viewHolder.mTextViewPlaceName.setText(place.name);
        viewHolder.mTextViewCountry.setText(place.country);

        viewHolder.mProgressBar.setVisibility(View.VISIBLE);
        viewHolder.mImageViewPlace.setVisibility(View.GONE);

        UrlImageViewHelper.setUrlDrawable(viewHolder.mImageViewPlace, place.url, new UrlImageViewCallback() {

            @Override
            public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {

                imageView.setVisibility(View.VISIBLE);
                viewHolder.mProgressBar.setVisibility(View.GONE);
                PlaceCursorAdapter.this.notifyDataSetChanged();
            }

        });
    }

    private class ViewHolder {

        private TextView mTextViewPlaceName;
        private TextView mTextViewCountry;
        private ImageView mImageViewPlace;
        private ProgressBar mProgressBar;

        ViewHolder(View view) {

            mTextViewPlaceName = (TextView) view.findViewById(R.id.textview_main_feed_item_name);
            mTextViewCountry = (TextView) view.findViewById(R.id.textview_main_feed_item_country);
            mImageViewPlace = (ImageView) view.findViewById(R.id.imageview_main_feed_item_place);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_main_feed_item);
        }
    }
}
