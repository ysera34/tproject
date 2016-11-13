package com.tacademy.v01.chemi.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.tacademy.v01.chemi.R;
import com.tacademy.v01.chemi.common.AppBaseActivity;
import com.tacademy.v01.chemi.model.Product;
import com.tacademy.v01.chemi.model.ProductStorage;
import com.tacademy.v01.chemi.view.fragment.ProductFragment;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 8..
 */

public class ProductPagerActivity extends AppBaseActivity {

    private static final String EXTRA_PRODUCT_ID = "com.tacademy.chemi.product_id";

    private ViewPager mProductViewPager;
    private ArrayList<Product> mProducts;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductPagerActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_pager);
        mProductViewPager = (ViewPager) findViewById(R.id.activity_product_pager_view_pager);
        mProducts = ProductStorage.get(this).getProducts();

        UUID productId = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);

        FragmentManager fm = getSupportFragmentManager();
        mProductViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Product product = mProducts.get(position);
                return ProductFragment.newInstance(product.getId());
            }

            @Override
            public int getCount() {
                return mProducts.size();
            }
        });

        for (int i = 0; i < mProducts.size(); i++) {
            if (mProducts.get(i).getId().equals(productId)) {
                mProductViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
