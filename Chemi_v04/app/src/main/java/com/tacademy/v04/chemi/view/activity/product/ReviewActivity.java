package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.fragment.product.ReviewFormFragment;
import com.tacademy.v04.chemi.view.fragment.product.ReviewFragment;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 22..
 */

public class ReviewActivity extends AppBaseActivity {

    private static final String EXTRA_REVIEW_ID = "com.tacademy.chemi.review_id";
    private static final String EXTRA_REVIEW_FORM = "com.tacademy.chemi.review_form";
    private static final String EXTRA_PRODUCT_ID = "com.tacademy.chemi.product_id";

    private int requestId;
    private int mProductId;

    private Toolbar mToolbar;

    private Review mReview;

    public static Intent newIntent(Context packageContext, int productId) {
        Intent intent = new Intent(packageContext, ReviewActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

//    public static Intent newIntent(Context packageContext, int fragmentRequestId) {
//        Intent intent = new Intent(packageContext, ReviewActivity.class);
//        intent.putExtra(EXTRA_REVIEW_FORM, fragmentRequestId);
//        return intent;
//    }

    public static Intent newIntent(Context packageContext, UUID reviewId) {
        Intent intent = new Intent(packageContext, ReviewActivity.class);
        intent.putExtra(EXTRA_REVIEW_ID, reviewId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        setTitle(R.string.title_activity_review);

//        requestId = getIntent().getIntExtra(EXTRA_REVIEW_FORM, 0);

        mProductId = getIntent().getIntExtra(EXTRA_PRODUCT_ID, 0);

        UUID reviewId = (UUID) getIntent().getSerializableExtra(EXTRA_REVIEW_ID);
        mReview = ReviewStorage.get(getApplicationContext()).getReview(reviewId);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_review_container);


//         if (requestId > 0) {
//            if (requestId == REQUEST_REVIEW_FORM) {
//                fragment = ReviewFormFragment.newInstance();
//                fm.beginTransaction()
//                        .add(R.id.fragment_review_container, fragment)
//                        .commit();
//            }
//        }

        if (mProductId > 0) {
            fragment = ReviewFormFragment.newInstance(mProductId);
            fm.beginTransaction()
                    .add(R.id.fragment_review_container, fragment)
                    .commit();
        } else {
            if (fragment == null) {
                fragment = ReviewFragment.newInstance(reviewId);
                fm.beginTransaction()
                        .add(R.id.fragment_review_container, fragment)
                        .commit();
            }
        }

        mToolbar = (Toolbar) findViewById(R.id.review_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_review_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
