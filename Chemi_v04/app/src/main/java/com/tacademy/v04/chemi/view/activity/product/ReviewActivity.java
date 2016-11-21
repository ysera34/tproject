package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.fragment.product.ReviewFragment;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 22..
 */

public class ReviewActivity extends AppBaseActivity {

    private static final String EXTRA_REVIEW_ID =
            "com.tacademy.chemi.review_id";

    private Review mReview;

    public static Intent newIntent(Context packageContext, UUID reviewId) {
        Intent intent = new Intent(packageContext, ReviewActivity.class);
        intent.putExtra(EXTRA_REVIEW_ID, reviewId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        UUID reviewId = (UUID) getIntent().getSerializableExtra(EXTRA_REVIEW_ID);
        mReview = ReviewStorage.get(getApplicationContext()).getReview(reviewId);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_review_container);

        if (fragment == null) {
            fragment = ReviewFragment.newInstance(reviewId);
            fm.beginTransaction()
                    .add(R.id.fragment_review_container, fragment)
                    .commit();
        }
    }
}
