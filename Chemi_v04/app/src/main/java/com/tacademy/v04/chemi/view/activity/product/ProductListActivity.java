package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.AppNavigationActivity;
import com.tacademy.v04.chemi.view.fragment.product.ProductListFragment;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductListActivity extends AppNavigationActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ProductListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_activity_product_list);

        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            containerFragment = ProductListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, containerFragment)
                    .commit();
        }
    }
}
