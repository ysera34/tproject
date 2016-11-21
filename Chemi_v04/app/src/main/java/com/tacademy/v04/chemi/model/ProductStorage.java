package com.tacademy.v04.chemi.model;

import android.content.Context;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class ProductStorage {

    private static ProductStorage sProductStorage;
    private Context mAppContext;

    private ArrayList<Product> mProducts;

    private ProductStorage(Context appContext) {
        mAppContext = appContext;
        mProducts = new ArrayList<>();

        /*
        sample data
         */
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

    public Product getProduct(UUID id) {
        for (Product product : mProducts) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
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
