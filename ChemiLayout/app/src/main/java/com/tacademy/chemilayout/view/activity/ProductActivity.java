package com.tacademy.chemilayout.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.tacademy.chemilayout.R;
import com.tacademy.chemilayout.model.Product;
import com.tacademy.chemilayout.model.ProductStorage;
import com.tacademy.chemilayout.view.fragment.ProductFragment;

import java.util.UUID;

public class ProductActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String EXTRA_PRODUCT_ID =
            "com.tacademy.chemi.product_id";

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mProductImageView;

    private Product mProduct;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    protected Fragment createFragment() {
//        return new ProductFragment();
        UUID productId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PRODUCT_ID);
        return ProductFragment.newInstance(productId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_product_container);

        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_product_container, fragment)
                    .commit();
        }

        UUID productId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PRODUCT_ID);
        mProduct = ProductStorage.get(getApplicationContext()).getProduct(productId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mProduct.getName());

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.product_detail_fab);
        mFloatingActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        mFloatingActionButton.setOnClickListener(this);

        mProductImageView = (ImageView) findViewById(R.id.toolbar_product_image);
        mProductImageView.setImageResource(mProduct.getImageResId());

    //        dynamicToolbarColor();
    //        toolbarTextAppernce();

    }

    private boolean favoriteProduct = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product_detail_fab:
                if (!favoriteProduct) {
                    Snackbar.make(v, "즐겨찾기에 추가되었습니다.", Snackbar.LENGTH_SHORT).show();
                    favoriteProduct = true;
                    mFloatingActionButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                } else {
                    Snackbar.make(v, "즐겨찾기에서 해제되었습니다.", Snackbar.LENGTH_SHORT).show();
                    favoriteProduct = false;
                    mFloatingActionButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.product_toolbar_menu, menu);
        return true;
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample01);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(
                        palette.getMutedColor(R.color.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(
                        palette.getMutedColor(R.color.colorPrimary));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

}