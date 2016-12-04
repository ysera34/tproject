package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.AppNavigationActivity;
import com.tacademy.v04.chemi.view.fragment.product.ProductListFragment;

import static com.tacademy.v04.chemi.common.Common.CATEGORY_DEFAULT_VALUE;
import static com.tacademy.v04.chemi.common.Common.PRODUCT_DEFAULT_VALUE;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductListActivity extends AppNavigationActivity {

    private static final String TAG = ProductListActivity.class.getSimpleName();

    private static final String EXTRA_CATEGORY_ID = "com.tacademy.chemi.category_id";
    private static final String EXTRA_PRODUCT_ID = "com.tacademy.chemi.product_id";

    private int mCategoryId;
    private long mProductId;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ProductListActivity.class);
        return intent;
    }

    public static Intent newIntent(Context packageContext, long productId) {
        Intent intent = new Intent(packageContext, ProductListActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
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
        mFloatingActionButton.setVisibility(View.INVISIBLE);

//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarLogoImageView.setVisibility(View.GONE);
        mToolbarTextView.setVisibility(View.VISIBLE);
        mToolbarTextView.setText(R.string.title_activity_product_list);
//        setTitle(R.string.title_activity_product_list);

        mCategoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, CATEGORY_DEFAULT_VALUE);
        mProductId = getIntent().getLongExtra(EXTRA_PRODUCT_ID, PRODUCT_DEFAULT_VALUE);

        FragmentManager fm = getSupportFragmentManager();
        containerFragment = fm.findFragmentById(R.id.fragment_container);

        if (containerFragment == null) {
            if (mProductId > 0) {
                containerFragment = ProductListFragment.newInstance(mProductId);
            } else if (mCategoryId > 0) {
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