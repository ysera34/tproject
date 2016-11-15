package com.tacademy.v04.chemi.view.activity.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.AppNavigationActivity;
import com.tacademy.v04.chemi.view.fragment.content.ContentListFragment;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ContentListActivity extends AppNavigationActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        AppNavigationActivity.containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            containerFragment = ContentListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, containerFragment)
                    .commit();
        }
    }
}
