package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 20..
 */

public class ReviewStorage {

    private static ReviewStorage sReviewStorage;
    private Context mAppContext;

    private ArrayList<Review> mReviews;

    private ArrayList<Integer> mReviewIds;

    private ReviewStorage(Context context) {
        mAppContext = context;
        mReviews = new ArrayList<>();
        mReviewIds = new ArrayList<>();

        /*
        sample data
         */
//        for (int i = 0; i < 20; i++) {
//            Review review = new Review();
//            review.setPositiveContent(i + " I have a working DrawerLayout with several elements within it. All elements inside the drawer are located within a RelativeLayout. The weird part is whenever the drawer is opened, the EditText receives focus automatically, and never looses it. I tried adding android:clickable=\"true\" attribute to the parent element (which is the RelativeLayout) to trick the focus issue but it does not help. Any ideas what is causing this behavior?");
//            review.setNegativeContent(i + " I have a working DrawerLayout with several elements within it. All elements inside the drawer are located within a RelativeLayout. The weird part is whenever the drawer is opened, the EditText receives focus automatically, and never looses it. I tried adding android:clickable=\"true\" attribute to the parent element (which is the RelativeLayout) to trick the focus issue but it does not help. Any ideas what is causing this behavior?I have a working DrawerLayout with several elements within it. All elements inside the drawer are located within a RelativeLayout. The weird part is whenever the drawer is opened, the EditText receives focus automatically, and never looses it. I tried adding android:clickable=\"true\" attribute to the parent element (which is the RelativeLayout) to trick the focus issue but it does not help. Any ideas what is causing this behavior?");
//            review.getImageResIdArray().add(R.drawable.review_sample01);
//            review.getImageResIdArray().add(R.drawable.review_sample02);
//            review.getImageResIdArray().add(R.drawable.review_sample03);
//
//            mReviews.add(review);
//        }
    }

    public static ReviewStorage get(Context context) {
        if (sReviewStorage == null) {
            sReviewStorage = new ReviewStorage(context.getApplicationContext());
        }
        return sReviewStorage;
    }

    public ArrayList<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        mReviews = reviews;
    }

    public Review getReview(UUID id) {
        for (Review review : mReviews) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

}
