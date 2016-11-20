package com.tacademy.v04.chemi.model;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 20..
 */

public class Review {

    private UUID mId;
    private UUID mUserId;
    private float mRatingValue;
    private boolean mPhotoCheck;
    private String mPositiveContent;
    private String mNegativeContent;

    public Review() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID userId) {
        mUserId = userId;
    }

    public float getRatingValue() {
        return mRatingValue;
    }

    public void setRatingValue(float ratingValue) {
        mRatingValue = ratingValue;
    }

    public boolean isPhotoCheck() {
        return mPhotoCheck;
    }

    public void setPhotoCheck(boolean photoCheck) {
        mPhotoCheck = photoCheck;
    }

    public String getPositiveContent() {
        return mPositiveContent;
    }

    public void setPositiveContent(String positiveContent) {
        mPositiveContent = positiveContent;
    }

    public String getNegativeContent() {
        return mNegativeContent;
    }

    public void setNegativeContent(String negativeContent) {
        mNegativeContent = negativeContent;
    }
}
