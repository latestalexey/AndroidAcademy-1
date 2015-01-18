package com.ciandt.cursoandroid.worldwondersapp.activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.entity.Place;
import com.ciandt.cursoandroid.worldwondersapp.fragment.PlaceDetailFragment;
import com.ciandt.cursoandroid.worldwondersapp.fragment.PlaceListFragment;
import com.ciandt.cursoandroid.worldwondersapp.fragment.ScreenSlideDetailFragment;
import com.ciandt.cursoandroid.worldwondersapp.infrastructure.Constants;
import com.ciandt.cursoandroid.worldwondersapp.manager.LoginManager;
import com.ciandt.cursoandroid.worldwondersapp.service.SyncService;

public class MainActivity extends ActionBarActivity implements PlaceListFragment.OnPlaceSelectedListener {

    private final String LOG_TAG = MainActivity.class.getCanonicalName();
    private LoginManager mLoginManager;
    private Boolean isTablet;
    private int mBulkInsertCount;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);

        LinearLayout toolBarLayout = (LinearLayout)findViewById(R.id.layout_toolbar);
        Toolbar toolBar = (Toolbar) toolBarLayout.findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);

         mProgressBar = (ProgressBar) toolBarLayout.findViewById(R.id.toobar_progress_spinner);

        //Make progress bar appear when you need it
        mProgressBar.setVisibility(View.VISIBLE);

       // setProgressBarIndeterminateVisibility(true);

        isTablet = getResources().getBoolean(R.bool.tablet);
        mLoginManager = new LoginManager(this);

        if (isTablet) {
            PlaceDetailFragment placeDetailFragment = new PlaceDetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.frame_layout_container_place_detail, placeDetailFragment);
            ft.commit();
        } else {
            findViewById(R.id.frame_layout_container_place_detail).setVisibility(View.GONE);
        }

        if (!mLoginManager.isUserLogged()) {
            actionClickLogout();
        }

        showDialog();

    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(null);
        builder.setTitle(R.string.dlg_confirmation_message);

        builder.setPositiveButton(R.string.dlg_confirmation_button_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     downloadContent();
                        //setProgressBarIndeterminateVisibility(false);
                        mProgressBar.setVisibility(View.GONE);
                        Log.i(LOG_TAG, "Dialog Ok!! bulkInsertCount: "+mBulkInsertCount);
                    }
                });

        builder.setNegativeButton(R.string.dlg_confirmation_button_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //setProgressBarIndeterminateVisibility(false);
                        mProgressBar.setVisibility(View.GONE);
                        Log.i(LOG_TAG, "Dialog Cancel!!");
                    }
                });

        // aqui é implementado a configuração do Dialog
        AlertDialog dialog = builder.create();
        // dialog.setCancelable(false);
        dialog.show();

    }

    private void downloadContent(){
        Intent intent = new Intent(MainActivity.this, SyncService.class);
        Bundle bundleExtras = new Bundle();

        bundleExtras.putParcelable(Constants.Service.Tag.RESULT_RECEIVER,
                new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        if (Constants.Service.Status.FINISHED == resultCode) {
                            mBulkInsertCount = resultData.getInt(Constants.Service.Tag.BULK_INSERT_COUNT);
                            showNotification();
                        } else if (Constants.Service.Status.ERROR == resultCode) {
                            // setProgressBarIndeterminateVisibility(false);
                            mProgressBar.setVisibility(View.GONE);
                            Log.i(LOG_TAG, "ERROR !!");
                        }
                    }
                });

        intent.putExtras(bundleExtras);
        startService(intent);
    }

    private void showNotification(){

        String conentText = String.valueOf(mBulkInsertCount).concat(" ").concat(getResources().getString(
                R.string.notification_content_text));

        // Create the Builder
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                       /// .setTicker(getResources().getString(R.string.notification_ticker_text))
                        .setSmallIcon(R.drawable.ic_launcher_app)
                        .setContentTitle(getResources().getString(
                                R.string.notification_content_title))
                        .setContentText(conentText);

        // Intent to be dispatch by SO on Notification click
        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        MainActivity.this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int notificationId = 001;

        // Gets an instance of the NotificationManager service
        NotificationManager notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Builds the notification and issues it
        notifyMgr.notify(notificationId, notificationBuilder.build());

    }

    private void actionClickLogout() {
        mLoginManager.logoutUser();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.main_menu_rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String uriString =
                    "market://details?id=com.ciandt.cursoandroid.worldwondersapp";
            intent.setData(Uri.parse(uriString));
            startActivity(intent);
            return true;
        } else if (id == R.id.main_menu_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.main_menu_logout) {
            actionClickLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPlaceSelected(Place place) {
        Boolean isTablet = getResources().getBoolean(R.bool.tablet);

        if (isTablet) {

            PlaceDetailFragment placeDetailFragment =
                    PlaceDetailFragment.newInstance(place);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout_container_place_detail, placeDetailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {
            /*Intent intent = new Intent(this, PlaceDetailActivity.class);
            intent.putExtra(PlaceDetailFragment.SELECTED_PLACE, place);
            startActivity(intent);*/
        }

    }

    @Override
    public void onPlaceSelected(Place place, int position, int count) {
        Boolean isTablet = getResources().getBoolean(R.bool.tablet);

        if (!isTablet) {
            Intent intent = new Intent(this, ScreenSlideDetailActivity.class);
            intent.putExtra(ScreenSlideDetailFragment.NUMBER_OF_PLACES, count);
            intent.putExtra(ScreenSlideDetailFragment.LAST_POSITION, position);
            startActivity(intent);

        }
    }

}
