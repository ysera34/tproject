package com.tacademy.v04.chemi.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.product.SearchActivity;
import com.tacademy.v04.chemi.view.fragment.navigation.ArchiveFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.ConfigureFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.FAQFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.MainFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.NoticeFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.RequestChemicalFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.ReviewLogFragment;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class AppNavigationActivity extends AppBaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected Handler mPendingHandler;
    protected static Fragment containerFragment;
    protected View mNavigationHeader;
    protected Button mNavigationCustomSearchButton;
    protected Button mNavigationContentButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_navigation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavigationHeader = mNavigationView.getHeaderView(0);
        mNavigationCustomSearchButton = (Button) mNavigationHeader.findViewById(R.id.nav_header_custom_search_button);
        mNavigationCustomSearchButton.setOnClickListener(this);

        mNavigationContentButton = (Button) mNavigationHeader.findViewById(R.id.nav_header_content_button);
        mNavigationContentButton.setOnClickListener(this);

//        FragmentManager fm = getSupportFragmentManager();
//        containerFragment = fm.findFragmentById(R.id.fragment_container);
//
//        if (containerFragment == null) {
//            containerFragment = MainFragment.newInstance();
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, containerFragment)
//                    .commit();
//        }
    }

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
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Text has changed, apply filtering?
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Perform the final search
//                return false;
//            }
//        });
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_header_custom_search_button :
                Toast.makeText(getApplicationContext(), "nav_header_custom_search_button",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_header_content_button :
                Toast.makeText(getApplicationContext(), "nav_header_content_button",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(SearchActivity.newIntent(getApplicationContext()));
            return true;
        }

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
        } else if (id == R.id.nav_archive) {
            fragment = ArchiveFragment.newInstance();
        } else if (id == R.id.nav_reviews) {
            fragment = ReviewLogFragment.newInstance();
        } else if (id == R.id.nav_item_analyze_request) {
            fragment = RequestChemicalFragment.newInstance();
        } else if (id == R.id.nav_notifications) {
            fragment = NoticeFragment.newInstance();
        } else if (id == R.id.nav_faq) {
            fragment = FAQFragment.newInstance();
        } else if (id == R.id.nav_settings) {
            fragment = ConfigureFragment.newInstance();
        }

//        else if (id == R.id.nav_custom_search) {
//            startActivity(new Intent(AppNavigationActivity.this, CustomSearchActivity.class));
//            mDrawerLayout.closeDrawers();
//        } else if (id == R.id.nav_content) {
//            startActivity(new Intent(AppNavigationActivity.this, ContentListActivity.class));
//            mDrawerLayout.closeDrawers();
//        }

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
    }
}
