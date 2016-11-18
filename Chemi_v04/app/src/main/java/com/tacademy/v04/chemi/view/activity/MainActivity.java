package com.tacademy.v04.chemi.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.fragment.navigation.MainFragment;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class MainActivity extends AppNavigationActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_app_navigation);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.setDrawerListener(toggle);
//        toggle.syncState();
//
//        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
//        mNavigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            containerFragment = MainFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, containerFragment)
                    .commit();
        }

    }
    /*
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_main) {
            fragment = MainFragment.newInstance();
        } else if (id == R.id.nav_custom_search) {
            fragment = CustomSearchFragment.newInstance();
        } else if (id == R.id.nav_content) {
//            startActivity(new Intent(MainActivity.this, CustomSearchActivity.class));
//            mDrawerLayout.closeDrawers();
        } else if (id == R.id.nav_archive) {
            fragment = ArchiveFragment.newInstance();
        } else if (id == R.id.nav_reviews) {
            fragment = ReviewLogFragment.newInstance();
        } else if (id == R.id.nav_item_analyze_request) {

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_login) {

        }

        FragmentManager fm = getSupportFragmentManager();
//        Fragment mFragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment != null) {
            containerFragment = fragment;
            fm.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, containerFragment)
                    .commit();
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);
        return true;
    }*/
}