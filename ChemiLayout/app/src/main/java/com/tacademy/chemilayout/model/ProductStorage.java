package com.tacademy.chemilayout.model;

import android.content.Context;

import com.tacademy.chemilayout.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 21..
 */
public class ProductStorage {

    private static ProductStorage sProductStorage;

    private List<Product> mProducts;

    public static ProductStorage get(Context context) {
        if (sProductStorage == null) {
            sProductStorage = new ProductStorage(context);
        }
        return sProductStorage;
    }

    public ProductStorage(Context context) {
        mProducts = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName("product name #" + i);
            if (i % 4 == 0) {
                product.setImageResId(R.drawable.sample01);
            } else if (i % 4 == 1) {
                product.setImageResId(R.drawable.sample02);
            } else if (i % 4 == 2) {
                product.setImageResId(R.drawable.sample03);
            } else if (i % 4 == 3) {
                product.setImageResId(R.drawable.sample04);
            }
            mProducts.add(product);
        }
    }

    public List<Product> getProducts() {
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
