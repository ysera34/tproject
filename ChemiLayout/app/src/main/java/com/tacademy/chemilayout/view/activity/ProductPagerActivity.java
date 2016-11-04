package com.tacademy.chemilayout.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.tacademy.chemilayout.R;
import com.tacademy.chemilayout.model.Product;
import com.tacademy.chemilayout.model.ProductStorage;
import com.tacademy.chemilayout.view.fragment.ProductFragment;

import java.util.List;
import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 24..
 */
public class ProductPagerActivity extends ProductActivity {

    private static final String EXTRA_PRODUCT_ID =
            "com.tacademy.chemi.product_id";

//    private CollapsingToolbarLayout collapsingToolbarLayout;
//    private FloatingActionButton mFloatingActionButton;

    private ViewPager mViewPager;
    private List<Product> mProducts;
//    private Product mProduct;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductPagerActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_pager);

        UUID productId = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
//        mProduct = ProductStorage.get(getApplicationContext()).getProduct(productId);

        mViewPager = (ViewPager) findViewById(R.id.activity_product_pager_view_pager);

        mProducts = ProductStorage.get(getApplicationContext()).getProducts();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
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
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
