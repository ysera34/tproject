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

/**
 * Created by yoon on 2016. 10. 21..
 */
public class ProductFragment extends Fragment {

    private Product mProduct;

    private TextView mProductTitleTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProduct = new Product();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mProductTitleTextView = (TextView) view.findViewById(R.id.product_name);
        mProductTitleTextView.setText("hello fragment");
        return view;
    }
}
