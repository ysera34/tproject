package com.tacademy.v04.chemi.view.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.product.ProductActivity;

import java.util.List;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductListFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mProductRecyclerView;
    private ProductAdapter mProductAdapter;
    private List<Product> mProducts;

    private TextView mProductTotalTextView;
    private View mProductSortView;
    private BottomSheetDialog mProductSortBottomSheetDialog;
    private ImageButton mProductSortReviewButton;
    private ImageButton mProductSortAvgButton;
    private ImageButton mProductSortLatestButton;

    public ProductListFragment() {
    }

    public static ProductListFragment newInstance() {

        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductStorage productStorage = ProductStorage.get(getActivity());
        mProducts = productStorage.getProducts();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        mProductTotalTextView = (TextView) view.findViewById(R.id.product_list_total);

        // bottom_sheet_product_sort
        mProductSortBottomSheetDialog = new BottomSheetDialog(getActivity());
        View mSortBottomSheetView = getLayoutInflater(savedInstanceState)
                .inflate(R.layout.bottom_sheet_product_sort, null);
        mProductSortBottomSheetDialog.setContentView(mSortBottomSheetView);

        mProductSortView = view.findViewById(R.id.product_list_sort_button_view);
        mProductSortView.setOnClickListener(this);

        mProductSortReviewButton = (ImageButton) mProductSortView
                .findViewById(R.id.product_list_sort_review_image_button);
        mProductSortReviewButton.setOnClickListener(this);
        mProductSortAvgButton = (ImageButton) mProductSortView
                .findViewById(R.id.product_list_sort_avg_image_button);
        mProductSortAvgButton.setOnClickListener(this);
        mProductSortLatestButton = (ImageButton) mProductSortView
                .findViewById(R.id.product_list_sort_latest_image_button);
        mProductSortLatestButton.setOnClickListener(this);


        mProductRecyclerView = (RecyclerView) view.findViewById(R.id.product_recycler_view);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mProductAdapter == null) {
            mProductAdapter = new ProductAdapter(mProducts);
            mProductRecyclerView.setAdapter(mProductAdapter);
        } else {
            mProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_list_sort_button_view :
                mProductSortBottomSheetDialog.show();
                break;
            case R.id.product_list_sort_review_image_button :
                Toast.makeText(getActivity(), "product_list_sort_review_image_button",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_list_sort_avg_image_button :
                Toast.makeText(getActivity(), "product_list_sort_avg_image_button",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_list_sort_latest_image_button :
                Toast.makeText(getActivity(), "product_list_sort_latest_image_button",
                        Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProductTotalTextView.setText(String.valueOf(mProducts.size()));

    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
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

    private class ProductHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Product mProduct;
        private TextView mTitleTextView;
        private ImageView mProductImageView;

        public ProductHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_product_name);
            mProductImageView = (ImageView) itemView.findViewById(R.id.list_item_product_image);
        }

        public void bindProduct(Product product) {
            mProduct = product;
            mTitleTextView.setText(mProduct.getName());
            mProductImageView.setImageResource(mProduct.getImageResId());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mProduct.getName() + " selected", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(getActivity(), ProductActivity.class);
            Intent intent = ProductActivity.newIntent(getActivity(), mProduct.getId());
//            Intent intent = ProductPagerActivity.newIntent(getActivity(), mProduct.getId());
            startActivity(intent);
        }
    }
}
