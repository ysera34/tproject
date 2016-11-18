package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "product_id";

    private Product mProduct;

    private TextView mProductTitleTextView;

    private TabLayout mProductDetailTabLayout;
    private ViewPager mProductDetailViewPager;
    private ArrayList<Fragment> mProductDetailListFragments;
    private ArrayList<String> mProductDetailFragmentsTitles;

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

        setHasOptionsMenu(true);

        mProductDetailListFragments = new ArrayList<>();
        mProductDetailFragmentsTitles = new ArrayList<>();

        addProductDetailFragment(ChemicalListFragment.newInstance(), getString(R.string.product_detail_chemical_list_name));
        addProductDetailFragment(ReviewListFragment.newInstance(), getString(R.string.product_detail_review_list_name));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
//        mProductTitleTextView = (TextView) view.findViewById(R.id.product_name);
//        mProductTitleTextView.setText(mProduct.getName());

        mProductDetailTabLayout = (TabLayout) view.findViewById(R.id.product_detail_tabLayout);
        mProductDetailViewPager = (ViewPager) view.findViewById(R.id.product_detail_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mProductDetailViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mProductDetailListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mProductDetailListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mProductDetailFragmentsTitles.get(position);
            }
        });

        mProductDetailTabLayout.setupWithViewPager(mProductDetailViewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addProductDetailFragment(Fragment fragment, String title) {
        mProductDetailListFragments.add(fragment);
        mProductDetailFragmentsTitles.add(title);
    }
}
