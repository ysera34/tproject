package com.tacademy.v01.chemi.view.activity;

import android.support.v4.app.Fragment;

import com.tacademy.v01.chemi.view.fragment.ProductListFragment;

/**
 * Created by yoon on 2016. 11. 7..
 */

public class ProductListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProductListFragment();
    }
}
