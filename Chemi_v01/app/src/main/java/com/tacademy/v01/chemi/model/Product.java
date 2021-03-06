package com.tacademy.v01.chemi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 6..
 */
public class Product {

    private UUID mId;
    private int mProductId;
    private String mMaker;
    private String mBrand;
    private String mName;
    private String mType;
    private String mPurpose;
    private float mRatingAvg;
    private int mVotedNumber;
    private Date mReleaseDate;
    private int mImageResId;
    private String mImagePath;
    private ArrayList<Chemical> mChemicals;

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
        mRatingAvg = ratingAvg;
    }

    public int getVotedNumber() {
        return mVotedNumber;
    }

    public void setVotedNumber(int votedNumber) {
        mVotedNumber = votedNumber;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
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
}
