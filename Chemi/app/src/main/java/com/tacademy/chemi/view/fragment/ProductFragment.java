package com.tacademy.chemi.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.chemi.R;
import com.tacademy.chemi.model.Product;
import com.tacademy.chemi.model.ProductStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 16..
 */
public class ProductFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "product_id";

    private Product mProduct;
    private TextView mTitleTextView;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mChemicalFragments;
    private List<String> mChemicalFragmentTitles;

    /* icon */
    private int[] tabIcons = {
            R.drawable.ic_hourglass_empty_white_24dp,
            R.drawable.ic_comment_white_24dp,
            R.drawable.ic_share_white_24dp,
            R.drawable.ic_insert_link_white_24dp
    };


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
        UUID productId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        mProduct = ProductStorage.get(getActivity()).getProduct(productId);

        mChemicalFragments = new ArrayList<>();
        mChemicalFragmentTitles = new ArrayList<>();

        addChemicalFragment(new CompositionListFragment(), "성분");
        addChemicalFragment(new ReviewListFragment(), "리뷰");
        addChemicalFragment(new ReviewListFragment(), "공유");
        addChemicalFragment(new ReviewListFragment(), "추천");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_product, container, false);

        mTitleTextView = (TextView) view.findViewById(R.id.product_title);
        mTitleTextView.setText(mProduct.getTitle());

        mViewPager = (ViewPager) view.findViewById(R.id.chemical_components_viewpager);
        mViewPager.setOffscreenPageLimit(3);
        FragmentManager fm = getChildFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                return mChemicalFragments.get(position);
            }

            @Override
            public int getCount() {
                return mChemicalFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
//                return mChemicalFragmentTitles.get(position);
                Drawable image = getContext().getResources().getDrawable(tabIcons[position]);
                image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());

                SpannableString sb =
                        new SpannableString("   " + mChemicalFragmentTitles.get(position));
                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sb;
            }
        });

        mTabLayout = (TabLayout) view.findViewById(R.id.chemical_components_tabs);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(mViewPager);
//        setupTabIcons();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void addChemicalFragment(Fragment fragment, String title) {
        mChemicalFragments.add(fragment);
        mChemicalFragmentTitles.add(title);
    }

    private void setupTabIcons() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }
}
