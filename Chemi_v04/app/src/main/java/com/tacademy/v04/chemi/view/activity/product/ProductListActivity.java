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

    private static final String EXTRA_CATEGORY_ID = "com.tacademy.chemi.category_id";
    private int mCategoryId;
    public static final int CATEGORY_DEFAULT_VALUE = 0;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ProductListActivity.class);
        return intent;
    }

    public static Intent newIntent(Context packageContext, int categoryId) {
        Intent intent = new Intent(packageContext, ProductListActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.title_activity_product_list);

        mCategoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, CATEGORY_DEFAULT_VALUE);

        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            if (mCategoryId != CATEGORY_DEFAULT_VALUE) {
                containerFragment = ProductListFragment.newInstance(mCategoryId);
            } else {
                containerFragment = ProductListFragment.newInstance();
            }
            fm.beginTransaction()
                    .add(R.id.fragment_container, containerFragment)
                    .commit();
        }
    }
}
