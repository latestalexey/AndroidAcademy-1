package com.ciandt.cursoandroid.worldwondersapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.ciandt.cursoandroid.worldwondersapp.R;


public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout toolBarLayout = (LinearLayout)findViewById(R.id.layout_toolbar);
        Toolbar toolBar = (Toolbar) toolBarLayout.findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.action_bar_logo);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
