package com.tacademy.v01.chemi.model;

import android.content.Context;

import com.tacademy.v01.chemi.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 6..
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
            product.setName("product product name: " + i);
            if (i % 5 == 1) {
                product.setImageResId(R.drawable.sample01);
            } else if (i % 5 == 2) {
                product.setImageResId(R.drawable.sample02);
            } else if (i % 5 == 3) {
                product.setImageResId(R.drawable.sample03);
            } else if (i % 5 == 4) {
                product.setImageResId(R.drawable.sample04);
            }
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
