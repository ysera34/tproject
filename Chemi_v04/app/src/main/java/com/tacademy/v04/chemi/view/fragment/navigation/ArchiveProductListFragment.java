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
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductArchiveStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 * ChildFragment of ArchiveFragment
 */

public class ArchiveProductListFragment extends Fragment implements View.OnClickListener {

    private static final String ARCHIVE_PRODUCT = "ArchiveProduct";

    private RecyclerView mArchiveProductRecyclerView;
    private ArchiveProductAdapter mArchiveProductAdapter;
    private ArrayList<Product> mArchiveProducts;
    private ArrayList<Product> mArchiveProductDeleteSelected;

    private View mArchiveProductListHeaderA;
    private View mArchiveProductListHeaderB;
    private View mArchiveProductTotalSelectLayout;

    private TextView mArchiveProductTotalTextView;
    private TextView mArchiveProductEditTextView;
    private TextView mArchiveProductDeleteTextView;
    private TextView mArchiveProductCompleteTextView;

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

        mArchiveProductDeleteSelected = productArchiveStorage.getArchiveDeleteSelectProducts();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archive_product_list, container, false);
        mArchiveProductListHeaderA = view.findViewById(R.id.archive_product_list_header_a);
        mArchiveProductListHeaderB = view.findViewById(R.id.archive_product_list_header_b);
        mArchiveProductTotalSelectLayout = view.findViewById(R.id.archive_product_list_total_select_layout);
        mArchiveProductTotalSelectLayout.setOnClickListener(this);

        mArchiveProductTotalTextView = (TextView) view.findViewById(R.id.archive_product_list_total);
        mArchiveProductEditTextView = (TextView) view.findViewById(R.id.archive_product_list_edit_text_view);
        mArchiveProductEditTextView.setOnClickListener(this);
        mArchiveProductDeleteTextView = (TextView) view.findViewById(R.id.archive_product_list_delete_text_view);
        mArchiveProductCompleteTextView = (TextView) view.findViewById(R.id.archive_product_list_complete_text_view);

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
        mArchiveProductTotalTextView.setText(String.valueOf(mArchiveProducts.size()));

        if (mArchiveProductDeleteSelected.size() > 0) {
            String deleteNumberStr = getString(R.string.archive_product_delete_number,
                    String.valueOf(mArchiveProductDeleteSelected.size()));
            mArchiveProductDeleteTextView.setText(getString(R.string.archive_product_list_delete_text) + deleteNumberStr);
            mArchiveProductDeleteTextView.setTextColor(getResources().getColor(R.color.colorArchiveEditFont));
            mArchiveProductCompleteTextView.setTextColor(getResources().getColor(R.color.colorArchiveEditFont));
        }

    }

    private boolean mHeaderState = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.archive_product_list_edit_text_view :
                if (!mHeaderState) {
                    mArchiveProductListHeaderA.setVisibility(View.GONE);
                    mArchiveProductListHeaderB.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.archive_product_list_total_select_layout :
                Toast.makeText(getActivity(), "보관함에 있는 상품을 전체 선택하였습니다.", Toast.LENGTH_SHORT).show();

                break;
        }
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

    private class ArchiveProductHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Product mProduct;

        private ImageView mArchiveProductSelectImageView;
        private ImageView mArchiveProductImageView;
        private TextView mArchiveProductNameTextView;
        private RatingBar mArchiveProductRatingBar;
        private TextView mArchiveProductDueDateTextView;

        public ArchiveProductHolder(View itemView) {
            super(itemView);

            mArchiveProductSelectImageView = (ImageView)
                    itemView.findViewById(R.id.list_item_archive_product_select_image_view);
            mArchiveProductSelectImageView.setOnClickListener(this);
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
            if (mProduct.isArchiveEditSelect()) {
                mArchiveProductSelectImageView.setImageResource(R.drawable.ic_check_circle_check_24dp);
            }

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_item_archive_product_select_image_view :
                    if (!mProduct.isArchiveEditSelect()) {
                        mArchiveProductSelectImageView.setImageResource(R.drawable.ic_check_circle_check_24dp);
                        mProduct.setArchiveEditSelect(true);
                        Toast.makeText(getActivity(), mProduct.getName() + "이(가) 삭제 항목에 추가 되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        mArchiveProductSelectImageView.setImageResource(R.drawable.ic_check_circle_uncheck_24dp);
                        mProduct.setArchiveEditSelect(false);
                        Toast.makeText(getActivity(), mProduct.getName() + "이(가) 삭제 항목에서 해제 되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }
}
