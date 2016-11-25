package com.tacademy.v04.chemi.model;

/**
 * Created by yoon on 2016. 11. 25..
 */

public class Effect {

    private int mId;
    private int mType;
    private int mImageId;
    private String mContent;
    private String mConstitution;
    private String mDescription;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getConstitution() {
        return mConstitution;
    }

    public void setConstitution(String constitution) {
        mConstitution = constitution;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "mId=" + mId +
                ", mType=" + mType +
                ", mImageId=" + mImageId +
                ", mContent='" + mContent + '\'' +
                ", mConstitution='" + mConstitution + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}