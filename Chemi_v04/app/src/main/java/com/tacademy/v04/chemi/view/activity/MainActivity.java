package com.tacademy.v04.chemi.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.fragment.navigation.FAQFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.MainFragment;
import com.tacademy.v04.chemi.view.fragment.navigation.AnalyzeRequestFragment;

import static com.tacademy.v04.chemi.common.Common.REQUEST_NAVIGATION_ANALYZE_REQUEST;
import static com.tacademy.v04.chemi.common.Common.REQUEST_NAVIGATION_FAQ;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class MainActivity extends AppNavigationActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String EXTRA_REQUEST_NAVIGATION_ID = "com.tacademy.chemi.navigation_id";

    private int requestId;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    public static Intent newIntent(Context packageContext, int fragmentRequestId) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_REQUEST_NAVIGATION_ID, fragmentRequestId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFloatingActionButton.setVisibility(View.INVISIBLE);

        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        requestId = getIntent().getIntExtra(EXTRA_REQUEST_NAVIGATION_ID, 0);

        if (requestId > 0) {
            if (requestId == REQUEST_NAVIGATION_FAQ) {
                containerFragment = FAQFragment.newInstance();
//                mToolbar.setTitle(R.string.fragment_title_faq);
                fm.beginTransaction()
                        .add(R.id.fragment_container, containerFragment)
                        .commit();
            } else if (requestId == REQUEST_NAVIGATION_ANALYZE_REQUEST) {
                containerFragment = AnalyzeRequestFragment.newInstance();
//                mToolbar.setTitle(R.string.fragment_title_faq);
                fm.beginTransaction()
                        .add(R.id.fragment_container, containerFragment)
                        .commit();
            } else {

            }
        } else {
            if (containerFragment == null) {
                containerFragment = MainFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.fragment_container, containerFragment)
                        .commit();
            }
        }


    }
}