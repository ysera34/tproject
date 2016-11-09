package com.tacademy.v01.chemi.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.v01.chemi.R;
import com.tacademy.v01.chemi.model.Product;
import com.tacademy.v01.chemi.model.ProductStorage;
import com.tacademy.v01.chemi.view.activity.ProductPagerActivity;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 7..
 */

public class ProductListFragment extends Fragment {

    private RecyclerView mProductListRecyclerView;
    private ProductAdapter mProductAdapter;

    public ProductListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        mProductListRecyclerView = (RecyclerView) view.findViewById(R.id.product_recycler_view);
        mProductListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ProductStorage productStorage = ProductStorage.get(getActivity());
        ArrayList<Product> products = productStorage.getProducts();

        if (mProductAdapter == null) {
            mProductAdapter = new ProductAdapter(products);
            mProductListRecyclerView.setAdapter(mProductAdapter);
        } else {
            mProductAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private ArrayList<Product> mProducts;

        public ProductAdapter(ArrayList<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_product, parent, false);
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
        private ImageView mProductImageView;
        private TextView mProductNameTextView;

        public ProductHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mProductNameTextView = (TextView) itemView.findViewById(R.id.list_item_product_name);
            mProductImageView = (ImageView) itemView.findViewById(R.id.list_item_product_image);
        }

        public void bindProduct(Product product) {
            mProduct = product;
            mProductNameTextView.setText(mProduct.getName());
            mProductImageView.setImageResource(mProduct.getImageResId());
        }

        @Override
        public void onClick(View view) {
//            Intent intent = ProductActivity.newIntent(getActivity(), mProduct.getId());
            Intent intent = ProductPagerActivity.newIntent(getActivity(), mProduct.getId());
            startActivity(intent);
        }
    }
}
