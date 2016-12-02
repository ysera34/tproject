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
 * ChildFragment of ArchiveFragment
 */

public class ArchiveContentTabFragment extends Fragment {

    private TabLayout mArchiveContentTabLayout;
    private ViewPager mArchiveContentViewPager;
    private ArrayList<Fragment> mArchiveContentListFragments;
    private ArrayList<String> mArchiveContentFragmentsTitles;

    public ArchiveContentTabFragment() {
    }

    public static ArchiveContentTabFragment newInstance() {

        ArchiveContentTabFragment fragment = new ArchiveContentTabFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArchiveContentListFragments = new ArrayList<>();
        mArchiveContentFragmentsTitles = new ArrayList<>();

        addArchiveContentListFragment(ArchiveContentT1ListFragment.newInstance(1), "케미데스크");
        addArchiveContentListFragment(ArchiveContentT2ListFragment.newInstance(2), "케미 PICK");
        addArchiveContentListFragment(ArchiveContentT3ListFragment.newInstance(3), "생활 정보");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archive_content_tab, container, false);

        mArchiveContentTabLayout = (TabLayout) view.findViewById(R.id.archive_content_tabLayout);
        mArchiveContentViewPager = (ViewPager) view.findViewById(R.id.archive_content_view_pager);

        FragmentManager fm = getChildFragmentManager();

        mArchiveContentViewPager.setOffscreenPageLimit(2);
        mArchiveContentViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mArchiveContentListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mArchiveContentListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mArchiveContentFragmentsTitles.get(position);
            }
        });

        mArchiveContentTabLayout.setupWithViewPager(mArchiveContentViewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addArchiveContentListFragment(Fragment fragment, String title) {
        mArchiveContentListFragments.add(fragment);
        mArchiveContentFragmentsTitles.add(title);
    }

}
