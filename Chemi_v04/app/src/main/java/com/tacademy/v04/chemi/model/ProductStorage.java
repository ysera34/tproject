package com.tacademy.v04.chemi.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class ProductStorage {

    private static final String TAG = ProductStorage.class.getSimpleName();

    private static ProductStorage sProductStorage;
    private Context mAppContext;

    private ArrayList<Product> mProducts;

    private ArrayList<Integer> mProductIds;

    private ProductStorage() {
    }

    private ProductStorage(Context appContext) {
        mAppContext = appContext;
        mProducts = new ArrayList<>();
        mProductIds = new ArrayList<>();
    }

    public static ProductStorage get(Context context) {
        if (sProductStorage == null) {
            sProductStorage = new ProductStorage(context.getApplicationContext());
        }
        return sProductStorage;
    }

    public ArrayList<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(ArrayList<Product> products) {

        for (Product product : products) {
            setProduct(product);
        }
    }

    public Product getProduct(UUID id) {
        for (Product product : mProducts) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product getProduct(int productId) {
        for (Product product : mProducts) {
            if (product.getProductId()==productId) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getProduct(long productId) {

        int intProductId = (int) productId;
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : mProducts) {
            if (product.getProductId()==intProductId) {
                products.add(product);
            }
        }
        return products;
    }

    public boolean setProduct(Product product) {

        if (mProducts.isEmpty()) { //isEmpty()
            mProducts.add(product);
            mProductIds.add(product.getProductId());
            Log.i(TAG + " mProducts initial add", product.toString());
//            Log.i(TAG + " mProducts initial add", String.valueOf(product.getProductId()));
            return true;
        } else {

            if (!mProductIds.contains(product.getProductId())) {
                mProducts.add(product);
                mProductIds.add(product.getProductId());
    //            Log.i(TAG + "mProducts.size()", String.valueOf(mProducts.size()));
                Log.i(TAG + " mProducts add", "ProductId : " + String.valueOf(product.toString()));
            } else {
                Log.i(TAG + " mProducts do not add",
                        "already existed " + "ProductId : " + String.valueOf(product.toString()));
//                mProducts.set(product.getProductId()-1, product);
            }
            return true;
        }
    }

    public ArrayList<Product> getSearchProducts(ArrayList<Integer> productIds) {

        ArrayList<Product> products = new ArrayList<>();
        for (int i : productIds) {
            products.add(getProduct(i));
        }
        return products;
    }

    public ArrayList<Product> getSearchProducts(String searchQuery) {

        ArrayList<Product> products = new ArrayList<>();
        int searchQueryLength = searchQuery.length();
        String searchingStr;

        if (searchQueryLength != 0 ) {
            for (Product product : mProducts) {
                searchingStr = product.getName().substring(0, searchQueryLength);
                if (searchingStr.equals(searchQuery)) {
                    products.add(product);
                }
            }
            return products;
        }
        return null;
    }

    public ArrayList<Product> getCategoryProducts(int categoryId) {

        ArrayList<Product> products = new ArrayList<>();

        for (Product product : mProducts) {
            if (product.getCategoryId()==categoryId) {
                products.add(product);
            }
        }
        return products;
    }
}
