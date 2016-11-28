package com.tacademy.v04.chemi.view.fragment.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.product.ProductActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.tacademy.v04.chemi.common.Common.CATEGORY_DEFAULT_VALUE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductListFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = ProductListFragment.class.getSimpleName();

    private static final String PRODUCT_NO_EXIST_INFO = "NoExistProduct";

    private static final String ARG_CATEGORY_ID = "category_id";

    private ProductStorage mProductStorage;
    private RecyclerView mProductRecyclerView;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProducts;
    private int mCategoryId;

    private TextView mProductTotalTextView;
    private View mProductSortView;
    private BottomSheetDialog mProductSortBottomSheetDialog;
    private Button mProductSortReviewButton;
    private Button mProductSortAvgButton;
    private Button mProductSortLatestButton;

    public static ProductListFragment newInstance() {

        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    public static ProductListFragment newInstance(int categoryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY_ID, categoryId);

        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductListFragment() {
        mProductStorage = ProductStorage.get(getActivity());
        mProducts = mProductStorage.getProducts();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(
                    ARG_CATEGORY_ID, CATEGORY_DEFAULT_VALUE);
            Log.d(TAG, "mCategoryId : " + mCategoryId);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        mProductTotalTextView = (TextView) view.findViewById(R.id.product_list_total);

        mProductSortView = view.findViewById(R.id.product_list_sort_button_view);
        mProductSortView.setOnClickListener(this);

        mProductRecyclerView = (RecyclerView) view.findViewById(R.id.product_recycler_view);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        switch above method
        setupAdapter();
//        mProductAdapter = new ProductAdapter(mProducts);
//        mProductRecyclerView.setAdapter(mProductAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestJsonObject();
        requestJsonObject();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_list_sort_button_view :
                // bottom_sheet_product_sort
                mProductSortBottomSheetDialog = new BottomSheetDialog(getActivity());
                View mSortBottomSheetView = getLayoutInflater(getArguments())
                        .inflate(R.layout.bottom_sheet_product_sort, null);
                mProductSortBottomSheetDialog.setContentView(mSortBottomSheetView);

                mProductSortReviewButton = (Button) mSortBottomSheetView
                        .findViewById(R.id.bottom_sheet_product_filter_section1);
                mProductSortReviewButton.setOnClickListener(this);
                mProductSortAvgButton = (Button) mSortBottomSheetView
                        .findViewById(R.id.bottom_sheet_product_filter_section2);
                mProductSortAvgButton.setOnClickListener(this);
                mProductSortLatestButton = (Button) mSortBottomSheetView
                        .findViewById(R.id.bottom_sheet_product_filter_section3);
                mProductSortLatestButton.setOnClickListener(this);
                mProductSortBottomSheetDialog.show();
                break;
            case R.id.bottom_sheet_product_filter_section1 :
                Toast.makeText(getActivity(), "product_list_sort_review_image_button",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_product_filter_section2 :
                Toast.makeText(getActivity(), "product_list_sort_avg_image_button",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_product_filter_section3 :
                Toast.makeText(getActivity(), "product_list_sort_latest_image_button",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void setupAdapter() {
        if (isAdded()) {
            if (mProductAdapter == null) {
                mProductAdapter = new ProductAdapter(mProducts);
                mProductRecyclerView.setAdapter(mProductAdapter);
            } else {
                // category product
                if (mCategoryId > 0) {
//                    Toast.makeText(getActivity(), "mCategoryId > 0", Toast.LENGTH_SHORT).show();
                    mProductAdapter.addItems(mProductStorage.getCategoryProducts(mCategoryId));
                    mProductAdapter.notifyDataSetChanged();
                    mProductTotalTextView.setText(String.valueOf(mProductAdapter.getItemCount()));
                    // all products
                } else {
//                    Toast.makeText(getActivity(), "mCategoryId < 0", Toast.LENGTH_SHORT).show();
                    mProductAdapter.addItems(mProductStorage.getProducts());
                    mProductAdapter.notifyDataSetChanged();
                    mProductTotalTextView.setText(String.valueOf(mProductAdapter.getItemCount()));
                }

                // category no product
                if (mCategoryId > 0 && mProductAdapter.getItemCount() == 0) {
                    FragmentManager manager = getFragmentManager();
                    NoProductDialogFragment dialogFragment =
                            NoProductDialogFragment.newInstance();
                    dialogFragment.show(manager, PRODUCT_NO_EXIST_INFO);
                }
            }
        }
    }


    private void requestJsonObject() {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_HOST + PATH,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        mProductStorage.setProducts(Parser.parseProductList(response));
//                        mProductStorage.setProducts(Parser.parseProductList(response, mProducts));
//                        switch above method
                        setupAdapter();
//                        mProductAdapter.addItems(mProductStorage.getProducts());
//                        mProductAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private ArrayList<Product> mProducts;

        public ProductAdapter(ArrayList<Product> products) {
            mProducts = products;
        }

        public void addItems(ArrayList<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_product, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bindProduct(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

    private class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Product mProduct;
        private ImageView mProductImageView;
        private TextView mProductBrandTextView;
        private TextView mProductTitleTextView;
        private RatingBar mProductReviewRatingBar;
        private TextView mProductReviewRatingAvgValue;
        private TextView mProductReviewRatingCount;

        public ProductHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mProductImageView = (ImageView) itemView.findViewById(R.id.list_item_product_image);
            mProductBrandTextView = (TextView) itemView.findViewById(R.id.list_item_product_brand);
            mProductTitleTextView = (TextView) itemView.findViewById(R.id.list_item_product_name);
            mProductReviewRatingBar = (RatingBar) itemView.findViewById(R.id.list_item_product_ratingBar);
            mProductReviewRatingAvgValue = (TextView) itemView.findViewById(R.id.list_item_product_ratingBar_value);
            mProductReviewRatingCount = (TextView) itemView.findViewById(R.id.list_item_product_review_rating_count);
        }

        public void bindProduct(Product product) {
            mProduct = product;
            mProductImageView.setImageResource(mProduct.getImageResId());
            mProductBrandTextView.setText(getString(
                    R.string.product_brand_name_format, mProduct.getBrand()));
            mProductTitleTextView.setText(mProduct.getName());
            mProductReviewRatingBar.setRating(mProduct.getRatingAvg());
            mProductReviewRatingAvgValue.setText(getString(
                    R.string.product_rating_value_format, String.valueOf(mProduct.getRatingAvg())));
            mProductReviewRatingCount.setText(getString(
                    R.string.list_item_product_review_rating_count, String.valueOf(mProduct.getVotedNumber())));
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mProduct.getName() + " selected", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(getActivity(), ProductActivity.class);
            Intent intent = ProductActivity.newIntent(getActivity(), mProduct.getId());
//            Intent intent = ProductPagerActivity.newIntent(getActivity(), mProduct.getId());
            startActivity(intent);
//            startActivityForResult(intent, REQUEST_PRODUCT_ACTIVITY);
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        ProductStorage productStorage = ProductStorage.get(getActivity());
//        ArrayList<Product> products = productStorage.getProducts();
//        for (Product product : products) {
//            Log.i(TAG + " onPause()", product.toString());
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        ProductStorage productStorage = ProductStorage.get(getActivity());
//        ArrayList<Product> products = productStorage.getProducts();
//        for (Product product : products) {
//            Log.i(TAG + " onStop()", product.toString());
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ProductStorage productStorage = ProductStorage.get(getActivity());
//        ArrayList<Product> products = productStorage.getProducts();
//        for (Product product : products) {
//            Log.i(TAG + " onDestroyView()", product.toString());
//        }
//    }
}
