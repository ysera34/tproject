package com.tacademy.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 16..
 */
public class ProductStorage {

    private static ProductStorage sProductStorage;

    private ArrayList<Product> mProducts;

    public static ProductStorage get() {
        if (sProductStorage == null) {
            sProductStorage = new ProductStorage();
        }
        return sProductStorage;
    }

    public static ProductStorage get(Context context) {
        if (sProductStorage == null) {
            sProductStorage = new ProductStorage(context);
        }
        return sProductStorage;
    }

    public ProductStorage() {
        mProducts = new ArrayList<>();
    }

    public ProductStorage(Context context) {
        mProducts = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setTitle("product title #" + i);
            mProducts.add(product);
        }
    }

    public void setProducts(ArrayList<Product> products) {
        mProducts = products;
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
