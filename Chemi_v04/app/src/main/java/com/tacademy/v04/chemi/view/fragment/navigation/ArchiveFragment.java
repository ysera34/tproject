package com.tacademy.v04.chemi.view.fragment.navigation;

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

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 * flows from Navigation Drawer menu Button
 * User's Archive, Storage : ViewPager (ArchiveContentListFragment, ArchiveProductListFragment)
 */

public class ArchiveFragment extends Fragment {

    private TabLayout mArchiveTabLayout;
    private ViewPager mArchiveViewPager;
    private ArrayList<Fragment> mArchiveListFragments;
    private ArrayList<String> mArchiveFragmentsTitles;

    public ArchiveFragment() {
    }

    public static ArchiveFragment newInstance() {

        ArchiveFragment fragment = new ArchiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArchiveListFragments = new ArrayList<>();
        mArchiveFragmentsTitles = new ArrayList<>();

        addArchiveFragment(
                ArchiveProductListFragment.newInstance(), getString(R.string.archive_product_tab_title));
        addArchiveFragment(
                ArchiveContentListFragment.newInstance(), getString(R.string.archive_content_tab_title));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archive, container, false);
        mArchiveTabLayout = (TabLayout) view.findViewById(R.id.archive_tabLayout);
        mArchiveViewPager = (ViewPager) view.findViewById(R.id.archive_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mArchiveViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mArchiveListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mArchiveListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mArchiveFragmentsTitles.get(position);
            }
        });

        mArchiveTabLayout.setupWithViewPager(mArchiveViewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addArchiveFragment(Fragment fragment, String title) {
        mArchiveListFragments.add(fragment);
        mArchiveFragmentsTitles.add(title);
    }
}
