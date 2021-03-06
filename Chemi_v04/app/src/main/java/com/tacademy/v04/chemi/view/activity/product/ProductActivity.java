package com.tacademy.v04.chemi.view.activity.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.fragment.product.ProductFragment;

import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

import static com.tacademy.v04.chemi.common.network.NetworkConfig.IMAGE_URL_HOST;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.SOCKET_TIMEOUT_GET_REQ;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductActivity extends AppBaseActivity {

    private static final String TAG = ProductActivity.class.getSimpleName();

    private static final String EXTRA_PRODUCT_ID =
            "com.tacademy.chemi.product_id";

    private ProductStorage mProductStorage;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private TextView mProductNameCollapsingToolbarTextView;
    private TextView mProductNameToolbarTextView;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mProductImageView;
    private TextView mProductBrandTextView;
    private TextView mProductNameTextView;
    private RatingBar mProductReviewRatingBar;
    private TextView mProductReviewRatingValue;
    private TextView mProductPriceTextView;

    private Product mProduct;
    private UUID mProductId;

//    private BottomSheetDialog mProductShareBottomSheetDialog;
//    private ImageButton mProductShareImageButton1;
//    private ImageButton mProductShareImageButton2;
//    private ImageButton mProductShareImageButton3;

    public static Intent newIntent(Context packageContext, UUID productId) {
        Intent intent = new Intent(packageContext, ProductActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID, productId);
        return intent;
    }

    public ProductActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_product);

        mProductId = (UUID) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        mProductStorage = ProductStorage.get(getApplicationContext());
        mProduct = mProductStorage.getProduct(mProductId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_product_container);

        if (fragment == null) {
            fragment = ProductFragment.newInstance(mProductId);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_product_container, fragment)
                    .commit();
        }

        mToolbar = (Toolbar) findViewById(R.id.product_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#08A3F5")));
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);

//        mToolbar.setTitle(R.string.title_activity_product);
//        setTitle(R.string.title_activity_product);

        mProductNameCollapsingToolbarTextView = (TextView)
                findViewById(R.id.product_detail_product_name_collapsing_toolbar_text_view);
        mProductNameCollapsingToolbarTextView.setText(mProduct.getName());
        mProductNameCollapsingToolbarTextView.setSelected(true);
        mProductNameToolbarTextView = (TextView)
                findViewById(R.id.product_detail_product_name_toolbar_text_view);
//            mProductNameToolbarTextView.setText(mProduct.getName());
//            mProductNameToolbarTextView.setSelected(true);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        mCollapsingToolbarLayout.setTitle(mProduct.getName());

//        Toast.makeText(getApplicationContext(), mProduct.getName().toString(),
//                Toast.LENGTH_SHORT).show();
        mProductImageView = (ImageView) findViewById(R.id.toolbar_product_image);
        mProductBrandTextView = (TextView) findViewById(R.id.product_brand_text_view);
        mProductNameTextView= (TextView) findViewById(R.id.product_name_text_view);
        mProductReviewRatingBar = (RatingBar) findViewById(R.id.product_ratingBar);
        mProductReviewRatingValue = (TextView) findViewById(R.id.product_rating_value_text_view);
//        mProductPriceTextView = (TextView) findViewById(R.id.product_price_text_view);

        mProductImageView.setImageDrawable(getResources().getDrawable(R.drawable.unloaded_image_holder));
    }

    @Override
    public void onResume() {
        super.onResume();
//        requestProductJsonObject();
        bindProduct(mProduct);
    }

    private void bindProduct(Product product) {
        mProductBrandTextView.setText(product.getBrand());
        mProductNameTextView.setText(product.getName());
        mProductReviewRatingBar.setRating(product.getRatingAvg());
        mProductReviewRatingValue.setText(String.valueOf(product.getRatingAvg()));
//        mProductPriceTextView.setText();
//        mProductImageView.setImageResource(R.drawable.product3);
        Glide.with(getApplicationContext())
                .load(IMAGE_URL_HOST + product.getImagePath())
//                .placeholder(R.drawable.unloaded_image_holder)
                .error(R.drawable.unloaded_image_holder)
//                .override(360, 153)
                .centerCrop()
                .into(mProductImageView);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        return super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_product_detail_toolbar, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_faq) {
//            Toast.makeText(getApplicationContext(), "자주 묻는 질문을 삼가하세요.", Toast.LENGTH_SHORT).show();
//            Intent intent = MainActivity.newIntent(getApplicationContext(), REQUEST_NAVIGATION_FAQ);
//            startActivity(intent);
//            return true;
//        } else if (id == R.id.action_archive) {
//            Toast.makeText(getApplicationContext(), "보관함에 추가되었습니다.", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        else if (id == R.id.action_share) {
//            Toast.makeText(getApplicationContext(), "공유하겠습니다.", Toast.LENGTH_SHORT).show();
//
//            mProductShareBottomSheetDialog = new BottomSheetDialog(getApplicationContext());
//            View mShareBottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_product_share, null);
//            mProductShareBottomSheetDialog.setContentView(mShareBottomSheetView);
//
//            mProductShareImageButton1 = (ImageButton) mShareBottomSheetView
//                    .findViewById(R.id.bottom_sheet_product_share_button1);
//            mProductShareImageButton2 = (ImageButton) mShareBottomSheetView
//                    .findViewById(R.id.bottom_sheet_product_share_button2);
//            mProductShareImageButton3 = (ImageButton) mShareBottomSheetView
//                    .findViewById(R.id.bottom_sheet_product_share_button3);
//
//            mProductShareImageButton1.setOnClickListener(this);
//            mProductShareImageButton2.setOnClickListener(this);
//            mProductShareImageButton3.setOnClickListener(this);
//            mProductShareBottomSheetDialog.show();
//            return true;
//        }
//
//        if (id == R.id.action_search) {
//            startActivity(SearchActivity.newIntent(getApplicationContext()));
//            return true;
//        } else if (id == R.id.action_home) {
//            startActivity(MainActivity.newIntent(getApplicationContext()));
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bottom_sheet_product_share_button1 :
//                Toast.makeText(getApplicationContext(), "카카오톡으로 공유합니다. 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.bottom_sheet_product_share_button2 :
//                Toast.makeText(getApplicationContext(), "카카오 스토리로 공유합니다. 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.bottom_sheet_product_share_button3 :
//                Toast.makeText(getApplicationContext(), "네이버 블로그로 공유합니다. 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

    private void requestProductJsonObject() {

        final ProgressDialog pDialog =
                ProgressDialog.show(ProductActivity.this, getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_HOST + PATH
                + File.separator + mProduct.getProductId(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        // have to save storage product
//                        mProductStorage.getProduct(mProductId);
                        Log.i(TAG,  mProduct.toStringId());
                        mProductStorage.setProduct(Parser.parseProduct(response, mProduct));

                        mProduct = mProductStorage.getProduct(mProductId);
//                        Log.i(TAG, "requestJsonObject() " + mProduct.toStringId());
                        bindProduct(mProduct);
//                        Log.i(TAG, "jsonObjectRequest() " + mProduct.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        Toast.makeText(getApplicationContext(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

}
