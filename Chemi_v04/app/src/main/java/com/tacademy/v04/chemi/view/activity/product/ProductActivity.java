package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.activity.MainActivity;
import com.tacademy.v04.chemi.view.fragment.product.ProductFragment;

import java.util.UUID;

import static com.tacademy.v04.chemi.common.Common.REQUEST_NAVIGATION_FAQ;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductActivity extends AppBaseActivity {

    private static final String EXTRA_PRODUCT_ID =
            "com.tacademy.chemi.product_id";

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mProductImageView;

    private Product mProduct;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        UUID productId = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        mProduct = ProductStorage.get(getApplicationContext()).getProduct(productId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_product_container);

        if (fragment == null) {
            fragment = ProductFragment.newInstance(productId);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_product_container, fragment)
                    .commit();
        }

        mToolbar = (Toolbar) findViewById(R.id.product_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setTitle(R.string.title_activity_product);
//        setTitle(R.string.title_activity_product);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(mProduct.getName());

//        Toast.makeText(getApplicationContext(), mProduct.getName().toString(),
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_product_detail_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_faq) {
            Toast.makeText(getApplicationContext(), "자주 묻는 질문을 삼가하세요.", Toast.LENGTH_SHORT).show();
            Intent intent = MainActivity.newIntent(getApplicationContext(), REQUEST_NAVIGATION_FAQ);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_archive) {
            Toast.makeText(getApplicationContext(), "보관함에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share) {
            Toast.makeText(getApplicationContext(), "공유하겠습니당.", Toast.LENGTH_SHORT).show();
            return true;
        }

//        if (id == R.id.action_search) {
//            startActivity(SearchActivity.newIntent(getApplicationContext()));
//            return true;
//        } else if (id == R.id.action_home) {
//            startActivity(MainActivity.newIntent(getApplicationContext()));
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



}
