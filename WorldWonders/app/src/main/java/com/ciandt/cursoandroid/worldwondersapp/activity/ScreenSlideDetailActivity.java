package com.ciandt.cursoandroid.worldwondersapp.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ciandt.cursoandroid.worldwondersapp.R;
import com.ciandt.cursoandroid.worldwondersapp.adapter.PlaceCursorAdapter;
import com.ciandt.cursoandroid.worldwondersapp.adapter.ScreenSlidePagerAdapter;
import com.ciandt.cursoandroid.worldwondersapp.contentprovider.WorldWondersContentProvider;
import com.ciandt.cursoandroid.worldwondersapp.database.table.PlaceTable;
import com.ciandt.cursoandroid.worldwondersapp.fragment.ScreenSlideDetailFragment;
import com.ciandt.cursoandroid.worldwondersapp.manager.DatabaseManager;

/**
 * Created by Wellington on 08/12/2014.
 */
public class ScreenSlideDetailActivity extends ActionBarActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    public static int NUM_PAGES;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private PlaceCursorAdapter mPlaceCursorAdapter;
    private DatabaseManager mDatabaseManager;

    private DrawerLayout mDrawerLayout;

    private LinearLayout mLinearLayout;

    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    private int lastPosition;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        LinearLayout toolBarLayout = (LinearLayout)findViewById(R.id.layout_toolbar);
         toolBar = (Toolbar) toolBarLayout.findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setLogo(R.drawable.action_bar_logo);

        // Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mLinearLayout = (LinearLayout) mDrawerLayout.findViewById(R.id.drawer_linear_layout);

        mDrawerList = (ListView) mLinearLayout.findViewById(R.id.drawer_list);

        mPlaceCursorAdapter = new PlaceCursorAdapter(this, null);
        mDatabaseManager = new DatabaseManager(this);

        mPager = (ViewPager) findViewById(R.id.pager);

        NUM_PAGES = getIntent().getIntExtra(ScreenSlideDetailFragment.NUMBER_OF_PLACES, 0);
        lastPosition = getIntent().getIntExtra(ScreenSlideDetailFragment.LAST_POSITION, 0);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolBar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        loadDd();

        setListeners();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {

        // Setting item click listener for the listview mDrawerList
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                mDrawerLayout.closeDrawer(mLinearLayout);
                mPager.setCurrentItem(position);
            }
        });
    }


    @Override
    public View onCreateView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadDd() {

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
                        mPagerAdapter = new ScreenSlidePagerAdapter(ScreenSlideDetailActivity.this, getSupportFragmentManager(), mPlaceCursorAdapter);
                        mPager.setAdapter(mPagerAdapter);
                        mPager.setCurrentItem(lastPosition);
                        mDrawerList.setAdapter(mPlaceCursorAdapter);

                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                        mPlaceCursorAdapter.swapCursor(null);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            //super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            //mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

}