package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductArchiveStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 * ChildFragment of ArchiveFragment
 */

public class ArchiveProductListFragment extends Fragment {

    private static final String ARCHIVE_PRODUCT = "ArchiveProduct";

    private RecyclerView mArchiveProductRecyclerView;
    private ArchiveProductAdapter mArchiveProductAdapter;
    private ArrayList<Product> mArchiveProducts;

    public ArchiveProductListFragment() {
    }

    public static ArchiveProductListFragment newInstance() {

        ArchiveProductListFragment fragment = new ArchiveProductListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductArchiveStorage productArchiveStorage = ProductArchiveStorage.get(getActivity());
        mArchiveProducts = productArchiveStorage.getArchiveProducts();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archive_product_list, container, false);

        mArchiveProductRecyclerView = (RecyclerView) view.findViewById(R.id.archive_product_recycler_view);
        mArchiveProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mArchiveProductAdapter == null) {
            mArchiveProductAdapter = new ArchiveProductAdapter(mArchiveProducts);
            mArchiveProductRecyclerView.setAdapter(mArchiveProductAdapter);
        } else {
            mArchiveProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class ArchiveProductAdapter extends RecyclerView.Adapter<ArchiveProductHolder> {

        ArrayList<Product> mArchiveProducts;

        public ArchiveProductAdapter(ArrayList<Product> archiveProducts) {
            mArchiveProducts = archiveProducts;
        }

        @Override
        public ArchiveProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_archive_product, parent, false);
            return new ArchiveProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ArchiveProductHolder holder, int position) {
            Product product = mArchiveProducts.get(position);
            holder.bindArchiveProduct(product);
        }

        @Override
        public int getItemCount() {
            return mArchiveProducts.size();
        }
    }

    private class ArchiveProductHolder extends RecyclerView.ViewHolder {

        private Product mProduct;

        private ImageView mArchiveProductImageView;
        private TextView mArchiveProductNameTextView;
        private RatingBar mArchiveProductRatingBar;
        private TextView mArchiveProductDueDateTextView;

        public ArchiveProductHolder(View itemView) {
            super(itemView);

            mArchiveProductImageView = (ImageView)
                    itemView.findViewById(R.id.list_item_archive_product_image);
            mArchiveProductNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_archive_product_name_text_view);
            mArchiveProductRatingBar = (RatingBar)
                    itemView.findViewById(R.id.list_item_archive_product_ratingBar);
            mArchiveProductDueDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_due_date_text_view);
        }

        public void bindArchiveProduct(Product product) {
            mProduct = product;
            mArchiveProductNameTextView.setText(mProduct.getName());
        }
    }
}
