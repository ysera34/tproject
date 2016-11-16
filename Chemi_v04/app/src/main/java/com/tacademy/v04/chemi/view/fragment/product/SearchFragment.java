package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.product.ProductListActivity;

/**
 * Created by yoon on 2016. 11. 16..
 */

public class SearchFragment extends Fragment implements View.OnClickListener {

    private Button mShowAllProductsButton;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {

        SearchFragment fragment = new SearchFragment();
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mShowAllProductsButton = (Button) view.findViewById(R.id.product_show_all_button);
        mShowAllProductsButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_show_all_button :
                startActivity(ProductListActivity.newIntent(getActivity()));
                break;
        }
    }
}
