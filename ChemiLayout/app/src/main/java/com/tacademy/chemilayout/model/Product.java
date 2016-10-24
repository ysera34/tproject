package com.tacademy.chemilayout.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 21..
 */
public class Product {

    private UUID mId;
    private int mCategoryId;
    private String mMaker;
    private String mBrand;
    private String mName;
    private String mType;
    private String mUse;
    private ArrayList<Integer> mChemicals;
    private String imagePath;

    public Product() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
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

    public String getUse() {
        return mUse;
    }

    public void setUse(String use) {
        mUse = use;
    }

    public ArrayList<Integer> getChemicals() {
        return mChemicals;
    }

    public void setChemicals(ArrayList<Integer> chemicals) {
        mChemicals = chemicals;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
