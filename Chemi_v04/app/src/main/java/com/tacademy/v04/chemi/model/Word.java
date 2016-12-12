package com.tacademy.v04.chemi.model;

/**
 * Created by yoon on 2016. 11. 30..
 */

public class Word {

    private int mProductId;
    private String mBrand;
    private String mName;
    private String mNameKO;
    private String mNameEN;

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

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public String getNameKO() {
        return mNameKO;
    }

    public void setNameKO(String nameKO) {
        mNameKO = nameKO;
    }

    public String getNameEN() {
        return mNameEN;
    }

    public void setNameEN(String nameEN) {
        mNameEN = nameEN;
    }
}
