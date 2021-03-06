package com.tacademy.chemilayout.view.fragment;

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
import android.widget.Toast;

import com.tacademy.chemilayout.R;
import com.tacademy.chemilayout.model.Product;
import com.tacademy.chemilayout.model.ProductStorage;
import com.tacademy.chemilayout.view.activity.ProductActivity;

import java.util.List;

/**
 * Created by yoon on 2016. 10. 24..
 */
public class ProductListFragment extends Fragment {

    private RecyclerView mProductRecyclerView;
    private ProductAdapter mProductAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
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
        ProductStorage productStorage = ProductStorage.get(getActivity());
        List<Product> products = productStorage.getProducts();

        if (mProductAdapter == null) {
            mProductAdapter = new ProductAdapter(products);
            mProductRecyclerView.setAdapter(mProductAdapter);
        } else {
            mProductAdapter.notifyDataSetChanged();
        }

    }

    private class ProductHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private ImageView mProductImageView;
        private Product mProduct;

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
}
