package com.tacademy.chemi.view.activity;

import android.support.v4.app.Fragment;

import com.tacademy.chemi.view.fragment.ProductListFragment;

/**
 * Created by yoon on 2016. 10. 16..
 */
public class ProductListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProductListFragment();
    }
}
