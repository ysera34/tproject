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
 * viewPager with User agreement and privacy
 */

public class TermsFragment extends Fragment {

    private TabLayout mTermsTabLayout;
    private ViewPager mTermsViewPager;
    private ArrayList<Fragment> mTermsListFragments;
    private ArrayList<String> mTermsFragmentsTitles;

    public static TermsFragment newInstance() {

        TermsFragment fragment = new TermsFragment();
        return fragment;
    }

    public TermsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTermsListFragments = new ArrayList<>();
        mTermsFragmentsTitles = new ArrayList<>();

        addTermsFragment(TermsAgreementFragment.newInstance(), "이용약관");
        addTermsFragment(TermsPrivacyFragment.newInstance(), "개인정보 취급방침");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_terms, container, false);
        mTermsTabLayout = (TabLayout) view.findViewById(R.id.terms_tabLayout);
        mTermsViewPager = (ViewPager) view.findViewById(R.id.terms_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mTermsViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mTermsListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mTermsListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mTermsFragmentsTitles.get(position);
            }
        });

        mTermsTabLayout.setupWithViewPager(mTermsViewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addTermsFragment(Fragment fragment, String title) {
        mTermsListFragments.add(fragment);
        mTermsFragmentsTitles.add(title);
    }
}
