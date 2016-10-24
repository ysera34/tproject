package com.tacademy.chemilayout.model;

import android.content.Context;

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
