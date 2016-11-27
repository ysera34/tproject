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

        /*
        sample data

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("productname:" + i);
            product.setImageResId(R.mipmap.ic_launcher);
            product.setCategoryId((i%2 == 0) ? 11 : 12 );
            mProducts.add(product);
        }

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("라네즈:" + i);
            product.setImageResId(R.mipmap.ic_launcher);
            product.setCategoryId((i%2 == 0) ? 13 : 21 );
            mProducts.add(product);
        }

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("앙블랑:" + i);
            product.setImageResId(R.mipmap.ic_launcher);
            product.setCategoryId((i%2 == 0) ? 22 : 23 );
            mProducts.add(product);
        }

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName("비오템:" + i);
            product.setImageResId(R.mipmap.ic_launcher);
            product.setCategoryId((i%2 == 0) ? 22 : 23 );
            mProducts.add(product);
        }
         */
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
//            if (!mProducts.contains(product.getProductId())) {
//                mProducts.add(product);
//                Log.i(TAG + " mProducts add", product.toString());
//            }
        }
//        mProducts = products;
//        for (Product product : mProducts) {
//            Log.i(TAG + " mProducts total", product.toString());
//        }
    }

    public Product getProduct(UUID id) {
        for (Product product : mProducts) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public void setProduct(Product product) {
        if (mProducts.size() == 0) {
            mProducts.add(product);
            mProductIds.add(product.getProductId());
            Log.i(TAG + " mProducts initial add", String.valueOf(product.getProductId()));
        }

        if (!mProductIds.contains(product.getProductId())) {
            mProducts.add(product);
            mProductIds.add(product.getProductId());
            Log.i(TAG + "mProducts.size()", String.valueOf(mProducts.size()));
            Log.i(TAG + " mProducts add", "ProductId : " + String.valueOf(product.getProductId()));
        } else {
            Log.i(TAG + " mProducts do not add",
                    "already existed " + "ProductId : " + String.valueOf(product.getProductId()));
        }

//        for (int i = 0; i < mProducts.size(); i++) {
//            if (mProducts.get(i).getProductId() != product.getProductId()) {
//                mProducts.add(product);
//                Log.i("mProducts.size()", String.valueOf(mProducts.size()));
//                Log.i(TAG + " mProducts add", String.valueOf(product.getProductId()));
//                break;
//            } else {
//                Log.i(TAG + " mProducts no add", "already existed" + String.valueOf(product.getProductId()));
//            }
//        }

//        for (int i = 0; i < mProducts.size(); i++) {
//            if (mProducts.get(i).getProductId() != product.getProductId()) {
//                mProducts.add(product);
//                Log.i(TAG + " mProducts add", product.toString());
//            } else {
//                Log.i(TAG + " mProducts no add", "already existed" + product.toString());
//            }
//        }

//        if (!mProducts.contains(product.getProductId())) {
//            mProducts.add(product);
//
//        } else {
//
//        }
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
