package com.tacademy.v01.chemi.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.tacademy.v01.chemi.model.Product;
import com.tacademy.v01.chemi.model.ProductStorage;
import com.tacademy.v01.chemi.view.fragment.ProductFragment;

import java.util.UUID;

public class ProductActivity extends SingleFragmentActivity {

    private static final String EXTRA_PRODUCT_ID = "com.tacademy.chemi.product_id";

    @Override
    protected Fragment createFragment() {
//        return new ProductFragment();
        UUID productId = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        Product product = ProductStorage.get(getApplicationContext()).getProduct(productId);
        return ProductFragment.newInstance(product.getId());
    }

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
