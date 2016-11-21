package com.tacademy.v04.chemi.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.content.ContentListActivity;
import com.tacademy.v04.chemi.view.activity.product.CustomSearchActivity;
import com.tacademy.v04.chemi.view.activity.product.SearchActivity;
import com.tacademy.v04.chemi.view.fragment.navigation.ArchiveFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.ConfigureFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.FAQFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.NoticeFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.RequestChemicalFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.ReviewLogFragment;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class AppNavigationActivity extends AppBaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = AppNavigationActivity.class.getSimpleName();

    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected Handler mPendingHandler;
    protected Fragment containerFragment;
    protected View mNavigationHeader;
    protected ImageView mNavigationHeaderUserImageView;
    protected TextView mNavigationHeaderNicknameTextView;
    protected TextView mNavigationHeaderSymptomTextView;
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
        mNavigationView.setItemBackground(
                ContextCompat.getDrawable(AppNavigationActivity.this, R.color.colorWhiteBackground));
        mNavigationView.setNavigationItemSelectedListener(this);

        mNavigationHeader = mNavigationView.getHeaderView(0);

        mNavigationHeaderUserImageView = (ImageView)
                mNavigationHeader.findViewById(R.id.nav_header_user_image_view);
//        mNavigationHeaderUserImageView.setImageResource(R.drawable.ic_arrow_upward_24dp);

        mNavigationHeaderNicknameTextView = (TextView)
                mNavigationHeader.findViewById(R.id.nav_header_nickname_text_view);
        mNavigationHeaderNicknameTextView.setText("Yujeong");
        mNavigationHeaderSymptomTextView = (TextView)
                mNavigationHeader.findViewById(R.id.nav_header_user_symptom_text_view);
        String mHeaderSymptomText = getString(R.string.nav_header_user_symptom,
                "여성", "40대", "자녀있음");
        mNavigationHeaderSymptomTextView.setText(mHeaderSymptomText);

        mNavigationCustomSearchButton = (Button)
                mNavigationHeader.findViewById(R.id.nav_header_custom_search_button);
        mNavigationCustomSearchButton.setOnClickListener(this);

        mNavigationContentButton = (Button)
                mNavigationHeader.findViewById(R.id.nav_header_content_button);
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
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_header_custom_search_button :
                Toast.makeText(getApplicationContext(), "nav_header_custom_search_button",
                        Toast.LENGTH_SHORT).show();
                startActivity(CustomSearchActivity.newIntent(getApplicationContext()));
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_header_content_button :
                Toast.makeText(getApplicationContext(), "nav_header_content_button",
                        Toast.LENGTH_SHORT).show();
                startActivity(ContentListActivity.newIntent(getApplicationContext()));
                mDrawerLayout.closeDrawers();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            startActivity(SearchActivity.newIntent(getApplicationContext()));
            return true;
        } else if (id == R.id.action_home) {
            if (true) {
                startActivity(MainActivity.newIntent(getApplicationContext()));
            }
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
//        if (id == R.id.nav_main) {
//            fragment = MainFragment.newInstance();
//            mToolbar.setTitle(R.string.fragment_title_main);
//        } else
        if (id == R.id.nav_archive) {
            fragment = ArchiveFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_archive);
        } else if (id == R.id.nav_reviews) {
            fragment = ReviewLogFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_reviews);
        } else if (id == R.id.nav_custom_search_log) {
            fragment = RequestChemicalFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_custom_search_log);
        } else if (id == R.id.nav_item_analyze_request) {
            fragment = RequestChemicalFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_analyze_request);
        } else if (id == R.id.nav_notifications) {
            fragment = NoticeFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_notifications);
        } else if (id == R.id.nav_faq) {
            fragment = FAQFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_faq);
        } else if (id == R.id.nav_settings) {
            fragment = ConfigureFragment.newInstance();
            mToolbar.setTitle(R.string.fragment_title_settings);
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
    }
}
