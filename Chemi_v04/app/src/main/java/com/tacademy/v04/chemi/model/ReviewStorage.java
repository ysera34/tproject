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

    private ReviewStorage(Context context) {
        mAppContext = context;
        mReviews = new ArrayList<>();

        /*
        sample data
         */
        for (int i = 0; i < 20; i++) {
            Review review = new Review();
            review.setPositiveContent("좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE " +
                    "좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE" +
                    "좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE 좋아용~~~123EE" + i);
            review.setNegativeContent("좋지 아니한가~~~123EF 좋지 아니한가~~~123EF 좋지 아니한가~~~123EF 좋지 아니한가~~~123EF" +
                    "좋지 아니한가~~~123EF 좋지 아니한가~~~123EF 좋지 아니한가~~~123EF 좋지 아니한가~~~123EF" +
                    "좋지 아니한가~~~123EF 좋지 아니한가~~~123EF 좋지 아니한가~~~123EF 좋지 아니한가~~~123EF" + i);
            mReviews.add(review);
        }
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

    public Review getReview(UUID id) {
        for (Review review : mReviews) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

}