package com.tacademy.v04.chemi.view.activity.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.AppNavigationActivity;
import com.tacademy.v04.chemi.view.fragment.content.ContentFragment;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ContentActivity extends AppNavigationActivity {

    private static final String TAG = ContentActivity.class.getSimpleName();

    private static final String EXTRA_CONTENT_ID = "com.tacademy.chemi.content_id";
    private static final String EXTRA_CONTENT_TYPE = "com.tacademy.chemi.content_type";

    private int mContentType;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ContentActivity.class);
        return intent;
    }

    public static Intent newIntent(Context packageContext, int contentType) {
        Intent intent = new Intent(packageContext, ContentActivity.class);
        intent.putExtra(EXTRA_CONTENT_TYPE, contentType);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFloatingActionButton.setVisibility(View.GONE);

        mContentType = getIntent().getIntExtra(EXTRA_CONTENT_TYPE, 0);
        Log.i(TAG, "mContentType : " + mContentType);
        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            containerFragment = ContentFragment.newInstance(mContentType);
            fm.beginTransaction()
                    .add(R.id.fragment_container, containerFragment)
                    .commit();
        }
    }
}
