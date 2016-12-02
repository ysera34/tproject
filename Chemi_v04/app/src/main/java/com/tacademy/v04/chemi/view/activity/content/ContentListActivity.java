package com.tacademy.v04.chemi.view.activity.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.AppNavigationActivity;
import com.tacademy.v04.chemi.view.fragment.content.ContentTabFragment;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ContentListActivity extends AppNavigationActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ContentListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.fragment_title_contents);
        mFloatingActionButton.setVisibility(View.GONE);

        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            containerFragment = ContentTabFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, containerFragment)
                    .commit();
        }
    }
}
