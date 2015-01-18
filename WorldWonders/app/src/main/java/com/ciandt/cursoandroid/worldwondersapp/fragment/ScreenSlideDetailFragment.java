package com.ciandt.cursoandroid.worldwondersapp.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by Wellington on 08/12/2014.
 */
public class ScreenSlideDetailFragment extends Fragment {
    public static final String SELECTED_PLACE = "selected_place";
    public static final String NUMBER_OF_PLACES = "number_of_places";
    public static final String LAST_POSITION = "last_position";
    private View view;

    public ScreenSlideDetailFragment() {
        // Required empty public constructor
    }

    public static ScreenSlideDetailFragment newInstance(final Place place) {
        ScreenSlideDetailFragment fragment = new ScreenSlideDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(SELECTED_PLACE, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place_detail, container, false);

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Place place = loadPlace();

        if (place != null) {
            viewHolder.mTextViewPlaceName.setText(place.name);
            viewHolder.mTextViewDescription.setText(place.description);
            viewHolder.mTextViewCountry.setText(place.country);

            UrlImageViewHelper.setUrlDrawable(viewHolder.mImageViewPlace, place.url, new UrlImageViewCallback() {
                @Override
                public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                }
            });
        }

        return view;
    }

    private Place loadPlace() {

        Place place = null;
        Bundle args = getArguments();

        if (args != null) {
            place = (Place) args.getSerializable(SELECTED_PLACE);
        }

        return place;
    }

    private class ViewHolder {

        private TextView mTextViewPlaceName;
        private TextView mTextViewCountry;
        private ImageView mImageViewPlace;
        private TextView mTextViewDescription;

        ViewHolder(View view) {

            mTextViewPlaceName = (TextView) view.findViewById(R.id.textview_fragment_place_detail_name);
            mTextViewDescription = (TextView) view.findViewById(R.id.textview_fragment_place_detail_description);
            mTextViewCountry = (TextView) view.findViewById(R.id.textview_fragment_place_detail_country);
            mImageViewPlace = (ImageView) view.findViewById(R.id.imageview_fragment_place_detail);
        }
    }
}
