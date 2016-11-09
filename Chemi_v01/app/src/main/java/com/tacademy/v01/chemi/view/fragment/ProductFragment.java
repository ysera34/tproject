package com.tacademy.v01.chemi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tacademy.v01.chemi.R;
import com.tacademy.v01.chemi.model.Product;
import com.tacademy.v01.chemi.model.ProductStorage;
import com.tacademy.v01.chemi.view.activity.ProductPagerActivity;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 6..
 */
public class ProductFragment extends Fragment {

    public static final String ARG_PRODUCT_ID = "product_id";

    private Product mProduct;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    private ImageView mToolbarImageView;
    private TextView mBrandTextView;
    private RatingBar mRatingAvgRagingBar;
    private TextView mRatingAvgTextView;
    private TextView mPriceTextView;

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mFragmentsTitles;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static ProductFragment newInstance(UUID productId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, productId);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProduct = new Product();
        UUID productId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        mProduct = ProductStorage.get(getActivity()).getProduct(productId);

        mFragments = new ArrayList<>();
        mFragmentsTitles = new ArrayList<>();

        addFragment(new ChemicalListFragment(), getString(R.string.chemical_fragment_title));
        addFragment(new ReviewListFragment(), getString(R.string.review_fragment_title));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);

        ((ProductPagerActivity) container.getContext()).setSupportActionBar(mToolbar);
        ((ProductPagerActivity) container.getContext()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
//        ((ProductPagerActivity) container.getContext()).getSupportActionBar()
//                .setDisplayShowTitleEnabled(false);
//        ((ProductPagerActivity) container.getContext()).getSupportActionBar()
//                .setHomeButtonEnabled(false);
//        ((ProductPagerActivity) container.getContext()).getSupportActionBar()
//                .dispatchMenuVisibilityChanged(false);

        mToolbarImageView = (ImageView) view.findViewById(R.id.toolbar_product_image);
        mBrandTextView = (TextView) view.findViewById(R.id.product_brand);
        mRatingAvgRagingBar = (RatingBar) view.findViewById(R.id.product_ratingBar);
        mRatingAvgTextView = (TextView) view.findViewById(R.id.product_rating_value);
        mPriceTextView = (TextView) view.findViewById(R.id.product_price);

        mTabLayout = (TabLayout) view.findViewById(R.id.product_detail_tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_product_detail_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentsTitles.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCollapsingToolbarLayout.setTitle(mProduct.getName());
//        mToolbarTitle.setText(mProduct.getName());
        mToolbarImageView.setImageResource(mProduct.getImageResId());

    }

    private void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentsTitles.add(title);
    }
}
