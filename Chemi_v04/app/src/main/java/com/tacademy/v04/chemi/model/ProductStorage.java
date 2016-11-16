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
        sample date
         */
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName("product name: " + i);
            product.setImageResId(R.mipmap.ic_launcher);
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
}
