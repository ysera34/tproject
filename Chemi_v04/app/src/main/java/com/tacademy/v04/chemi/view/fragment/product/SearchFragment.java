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
import android.widget.Button;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.view.activity.product.ProductListActivity;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 16..
 */

public class SearchFragment extends Fragment implements View.OnClickListener {

    private Button mShowAllProductsButton;

    private TabLayout mProductSearchTabLayout;
    private ViewPager mProductSearchViewPager;
    private ArrayList<Fragment> mProductSearchListFragments;
    private ArrayList<String> mProductSearchFragmentsTitles;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {

        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductSearchListFragments = new ArrayList<>();
        mProductSearchFragmentsTitles = new ArrayList<>();

        addProductSearchFragment(SearchPopularListFragment.newInstance(), getString(R.string.product_popular_list_name));
        addProductSearchFragment(SearchLatestListFragment.newInstance(), getString(R.string.product_latest_list_name));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mShowAllProductsButton = (Button) view.findViewById(R.id.product_show_all_button);
//        mShowAllProductsButton.setVisibility(View.GONE);
        mShowAllProductsButton.setOnClickListener(this);

        mProductSearchTabLayout = (TabLayout) view.findViewById(R.id.product_search_tabLayout);
        mProductSearchViewPager = (ViewPager) view.findViewById(R.id.product_search_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mProductSearchViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mProductSearchListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mProductSearchListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mProductSearchFragmentsTitles.get(position);
            }
        });

        mProductSearchTabLayout.setupWithViewPager(mProductSearchViewPager);
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

    private void addProductSearchFragment(Fragment fragment, String title) {
        mProductSearchListFragments.add(fragment);
        mProductSearchFragmentsTitles.add(title);
    }
}
