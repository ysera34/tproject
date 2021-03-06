package com.tacademy.v04.chemi.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class Product {

    private UUID mId;
    private int mProductId;
    private int mCategoryId;
    private String mMaker;
    private String mBrand;
    private String mName;
    private String mType;
    private String mPurpose;
    private float mRatingAvg;
    private int mRatingCount;
    private int mPrice;
    private String mReleaseDate;
    private int mImageResId;
    private String mImagePath;
    private ArrayList<Chemical> mChemicals;
    private boolean mArchiveSelect;

    public Product() {
        mId = UUID.randomUUID();
        mChemicals = new ArrayList<>();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        mCategoryId = categoryId;
    }

    public String getMaker() {
        return mMaker;
    }

    public void setMaker(String maker) {
        mMaker = maker;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getPurpose() {
        return mPurpose;
    }

    public void setPurpose(String purpose) {
        mPurpose = purpose;
    }

    public float getRatingAvg() {
        return mRatingAvg;
    }

    public void setRatingAvg(float ratingAvg) {
        mRatingAvg = Math.round(ratingAvg * 100) / 100.0f;
//        mRatingAvg = ratingAvg;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public void setRatingCount(int ratingCount) {
        mRatingCount = ratingCount;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public void setImageResId(int imageResId) {
        mImageResId = imageResId;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public ArrayList<Chemical> getChemicals() {
        return mChemicals;
    }

    public void setChemicals(ArrayList<Chemical> chemicals) {
        mChemicals = chemicals;
    }

    public int[] getHazardGrade() {
        int[] grades = new int[4];
        for (Chemical chemical : mChemicals) {
            if (chemical.getHazard()[0] == 0) {
                grades[3]++;
            } else if (chemical.getHazard()[0] >= 1 && chemical.getHazard()[0] <= 2) {
                grades[0]++;
            } else if (chemical.getHazard()[0] >= 3 && chemical.getHazard()[0] <= 6) {
                grades[1]++;
            } else if (chemical.getHazard()[0] >= 7) {
                grades[2]++;
            }
        }
        return grades;
    }

    public boolean isArchiveSelect() {
        return mArchiveSelect;
    }

    public void setArchiveSelect(boolean archiveSelect) {
        mArchiveSelect = archiveSelect;
    }

    public String toStringId() {
        return "Product{" +
                "mId=" + mId +
                ", mProductId=" + mProductId +
                '}';
    }

    @Override
    public String toString() {
        return "Product{" +
                "mId=" + mId +
                ", mProductId=" + mProductId +
                ", mCategoryId=" + mCategoryId +
                ", mMaker='" + mMaker + '\'' +
                ", mBrand='" + mBrand + '\'' +
                ", mName='" + mName + '\'' +
                ", mType='" + mType + '\'' +
                ", mPurpose='" + mPurpose + '\'' +
                ", mRatingAvg=" + mRatingAvg +
                ", mRatingCount=" + mRatingCount +
                ", mPrice=" + mPrice +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mImageResId=" + mImageResId +
                ", mImagePath='" + mImagePath + '\'' +
                ", mChemicals=" + mChemicals +
                ", mArchiveSelect=" + mArchiveSelect +
                '}';
    }
}
