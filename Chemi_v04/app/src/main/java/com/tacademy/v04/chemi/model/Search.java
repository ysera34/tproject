package com.tacademy.v04.chemi.model;

import com.tacademy.v04.chemi.R;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class Search {

    private UUID mId;
    private int mRatingNumber;
    private String mSearchWord;
    private boolean mVariationState;
    private int mStateImageResId;
    private int mVariationValue;

    public Search() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getRatingNumber() {
        return mRatingNumber;
    }

    public void setRatingNumber(int ratingNumber) {
        mRatingNumber = ratingNumber + 1;
    }

    public String getSearchWord() {
        return mSearchWord;
    }

    public void setSearchWord(String searchWord) {
        mSearchWord = searchWord;
    }

    public boolean isVariationState() {
        return mVariationState;
    }

    public void setVariationState(boolean variationState) {
        mVariationState = variationState;
    }

    public int getStateImageResId() {
        if (isVariationState()) {
            mStateImageResId = R.drawable.ic_arrow_upward_24dp;
        } else {
            mStateImageResId = R.drawable.ic_arrow_downward_24dp;
        }
        return mStateImageResId;
    }

    public int getVariationValue() {
        return mVariationValue;
    }

    public void setVariationValue(int variationValue) {
        mVariationValue = variationValue;
    }
}
