package com.tacademy.chemilayout.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.chemilayout.R;
import com.tacademy.chemilayout.model.Product;
import com.tacademy.chemilayout.model.ProductStorage;

import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 21..
 */
public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "product_id";

    private Product mProduct;

    private TextView mProductTitleTextView;

    public static ProductFragment newInstance(UUID productId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, productId);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProduct = new Product();
//        UUID productId = (UUID) getActivity().getIntent()
//                .getSerializableExtra(ProductActivity.EXTRA_PRODUCT_ID);
        UUID productId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        mProduct = ProductStorage.get(getActivity()).getProduct(productId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mProductTitleTextView = (TextView) view.findViewById(R.id.product_name);
        mProductTitleTextView.setText(mProduct.getName());
        return view;
    }
}
