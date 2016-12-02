package com.tacademy.v04.chemi.view.fragment.content;

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
 */

public class ContentTabFragment extends Fragment {

    private TabLayout mContentTabLayout;
    private ViewPager mContentViewPager;
    private ArrayList<Fragment> mContentListFragments;
    private ArrayList<String> mContentFragmentsTitles;

    public ContentTabFragment() {
    }

    public static ContentTabFragment newInstance() {

        ContentTabFragment fragment = new ContentTabFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentListFragments = new ArrayList<>();
        mContentFragmentsTitles = new ArrayList<>();

        addContentListFragment(ContentT1ListFragment.newInstance(1), "케미데스크");
        addContentListFragment(ContentT2ListFragment.newInstance(2), "케미 PICK");
        addContentListFragment(ContentT3ListFragment.newInstance(3), "생활 정보");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_content_tab, container, false);
        mContentTabLayout = (TabLayout) view.findViewById(R.id.content_tabLayout);
        mContentViewPager = (ViewPager) view.findViewById(R.id.content_view_pager);

        FragmentManager fm = getChildFragmentManager();

        mContentViewPager.setOffscreenPageLimit(2);
        mContentViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mContentListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mContentListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mContentFragmentsTitles.get(position);
            }
        });

        mContentTabLayout.setupWithViewPager(mContentViewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addContentListFragment(Fragment fragment, String title) {
        mContentListFragments.add(fragment);
        mContentFragmentsTitles.add(title);
    }
}
