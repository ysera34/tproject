package com.tacademy.v04.chemi.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 20..
 */

public class  Review {

    private UUID mId;
    private int mReviewId;
    private int mUserId;
    private float mRatingValue;
    private boolean mPhotoCheck;
    private String mPositiveContent;
    private String mNegativeContent;
    private ArrayList<Integer> mImageResIdArray;
    private ArrayList<String> mImagePaths;

    private String mName;
    private String mGender;
    private int mBirthYear;
    private int mChild;
    private double mRating;
    private String mCreatedDate;
    private String mConstitution;
    private String mConstitution1;
    private String mConstitution2;

    public Review() {
        mId = UUID.randomUUID();
        mImageResIdArray = new ArrayList<>();
        mImagePaths = new ArrayList<>();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getReviewId() {
        return mReviewId;
    }

    public void setReviewId(int reviewId) {
        mReviewId = reviewId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
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

    public ArrayList<Integer> getImageResIdArray() {
        return mImageResIdArray;
    }

    public void setImageResIdArray(ArrayList<Integer> imageResIdArray) {
        mImageResIdArray = imageResIdArray;
    }

    public ArrayList<String> getImagePaths() {
        return mImagePaths;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        mImagePaths = imagePaths;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public int getBirthYear() {
        return mBirthYear;
    }

    public void setBirthYear(int birthYear) {
        mBirthYear = birthYear;
    }

//    public int getChild() {
//        return mChild;
//    }

    public String getChild() {
        if (mChild > 0) {
            return "자녀 있음";
        }
        return "자녀 없음";
    }

    public void setChild(int child) {
        mChild = child;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        mCreatedDate = createdDate;
    }

    public String getConstitution() {
        return mConstitution;
    }

    public void setConstitution(String constitution) {
        mConstitution = constitution;
    }

    public String getConstitution1() {
        return mConstitution1;
    }

    public void setConstitution1(String constitution1) {
        mConstitution1 = constitution1;
    }

    public String getConstitution2() {
        return mConstitution2;
    }

    public void setConstitution2(String constitution2) {
        mConstitution2 = constitution2;
    }

    @Override
    public String toString() {
        return "Review{" +
                "mId=" + mId +
                ", mReviewId=" + mReviewId +
                ", mUserId=" + mUserId +
                ", mRatingValue=" + mRatingValue +
                ", mPhotoCheck=" + mPhotoCheck +
                ", mPositiveContent='" + mPositiveContent + '\'' +
                ", mNegativeContent='" + mNegativeContent + '\'' +
                ", mImageResIdArray=" + mImageResIdArray +
                ", mImagePaths=" + mImagePaths +
                ", mName='" + mName + '\'' +
                ", mGender='" + mGender + '\'' +
                ", mBirthYear=" + mBirthYear +
                ", mChild=" + mChild +
                ", mRating=" + mRating +
                ", mCreatedDate='" + mCreatedDate + '\'' +
                ", mConstitution='" + mConstitution + '\'' +
                ", mConstitution1='" + mConstitution1 + '\'' +
                ", mConstitution2='" + mConstitution2 + '\'' +
                '}';
    }
}
