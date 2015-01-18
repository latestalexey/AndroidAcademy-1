package com.ciandt.cursoandroid.worldwondersapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
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
 * Created by Wellington on 02/12/2014.
 */
public class PlaceDetailFragment extends Fragment {

    public static final String SELECTED_PLACE = "selected_place";

    private Activity mActivity;

    private TextView mTextViewPlaceName;
    private TextView mTextViewDescription;
    private TextView mTextViewCountry;
    private ImageView mImageViewPlace;

    public PlaceDetailFragment() {
        // Required empty public constructor
    }

    public static PlaceDetailFragment newInstance(final Place place) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
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
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_detail, container, false);

        Place place = loadPlace();

        mTextViewPlaceName = (TextView)  view.findViewById(R.id.textview_fragment_place_detail_name);
        mTextViewDescription = (TextView) view.findViewById(R.id.textview_fragment_place_detail_description);
        mTextViewCountry = (TextView) view.findViewById(R.id.textview_fragment_place_detail_country);
        mImageViewPlace = (ImageView) view.findViewById(R.id.imageview_fragment_place_detail);

        if(place != null) {
            mTextViewPlaceName.setText(place.name);
            mTextViewDescription.setText(place.description);
            mTextViewCountry.setText(place.country);

            UrlImageViewHelper.setUrlDrawable(mImageViewPlace, place.url, new UrlImageViewCallback() {
                @Override
                public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                }
            });
        }


        return view;
    }

    private Place loadPlace() {

        // primeiro, checar se o dado esta no Intent (estamos na PlaceDetailsActivity) - Smartphone
        Place place = (Place)
                mActivity.getIntent().getSerializableExtra(SELECTED_PLACE);
        if (place == null) {
        // Se nao encontramos no Intent, entao o dado deve estar nos argumentos do Fragment (estamos na MainActivity) - Tablet
            Bundle args = getArguments();
            if (args != null) {
                place = (Place) args.getSerializable(SELECTED_PLACE);
            }
        }
        return place;
    }



}
