package com.tacademy.chemilayout.view.activity;

import android.support.v4.app.Fragment;

import com.tacademy.chemilayout.view.fragment.ProductListFragment;

/**
 * Created by yoon on 2016. 10. 24..
 */
public class ProductListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ProductListFragment();
    }

}
