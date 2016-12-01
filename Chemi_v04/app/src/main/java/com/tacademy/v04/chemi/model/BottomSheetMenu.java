package com.tacademy.v04.chemi.model;

/**
 * Created by yoon on 2016. 12. 1..
 */

public class BottomSheetMenu {

    private int mImageId;
    private int mTitleId;

    public BottomSheetMenu(int imageId, int titleId) {
        mImageId = imageId;
        mTitleId = titleId;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public int getTitleId() {
        return mTitleId;
    }

    public void setTitleId(int titleId) {
        mTitleId = titleId;
    }
}
