package com.tacademy.v04.chemi.model;

/**
 * Created by yoon on 2016. 11. 25..
 */

public class Effect {

    private int mId;
    private int mConstitutionId;
    private int mImageId;
    private int mFontColorId;
    private String mConstitutionName;
    private String mDescription;
    private String mContent;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getConstitutionId() {
        return mConstitutionId;
    }

    public void setConstitutionId(int constitutionId) {
        mConstitutionId = constitutionId;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public int getFontColorId() {
        return mFontColorId;
    }

    public void setFontColorId(int fontColorId) {
        mFontColorId = fontColorId;
    }

    public String getConstitutionName() {
        return mConstitutionName;
    }

    public void setConstitutionName(String constitutionName) {
        mConstitutionName = constitutionName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "mId=" + mId +
                ", mConstitutionId=" + mConstitutionId +
                ", mImageId=" + mImageId +
                ", mFontColorId=" + mFontColorId +
                ", mConstitutionName='" + mConstitutionName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mContent='" + mContent + '\'' +
                '}';
    }
}