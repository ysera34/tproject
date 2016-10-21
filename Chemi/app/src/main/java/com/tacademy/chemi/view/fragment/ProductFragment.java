package com.tacademy.chemi.view.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private static final String ARG_TRANSITION_NAME = "transition_name";
    private static final String ARG_IMAGE_REF_ID = "ARG_IMAGE_REF_ID";

    private Product mProduct;
    private TextView mTitleTextView;
    private ImageView mProductImageView;

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


    public static ProductFragment newInstance(UUID productId, Context context,
                                              String transitionName, int imageRefId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, productId);

        args.putString(ARG_TRANSITION_NAME, transitionName);
        args.putInt(ARG_IMAGE_REF_ID, imageRefId);

        ProductFragment fragment = new ProductFragment();
        fragment.setSharedElementEnterTransition(TransitionInflater.from(context)
                .inflateTransition(R.transition.change_image_transform));

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

        mProductImageView = (ImageView) view.findViewById(R.id.list_item_product_image);
        mProductImageView.setTransitionName(getArguments().getString(ARG_TRANSITION_NAME));
        mProductImageView.setImageResource(getArguments().getInt(ARG_IMAGE_REF_ID));

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
//                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM){
                    @Override
                    public int getSize(Paint paint, CharSequence text,
                                       int start, int end, Paint.FontMetricsInt fontMetricsInt) {
//                        return super.getSize(paint, text, start, end, fm);
                        Drawable drawable = getDrawable();
                        Rect rect = drawable.getBounds();
                        if (fontMetricsInt != null) {
                            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                            int fontHeight = fmPaint.descent - fmPaint.ascent;
                            int drHeight = rect.bottom - rect.top;
                            int centerY = fmPaint.ascent + fontHeight / 2;

                            fontMetricsInt.ascent = centerY - drHeight / 2;
                            fontMetricsInt.top = fontMetricsInt.ascent;
                            fontMetricsInt.bottom = centerY + drHeight / 2;
                            fontMetricsInt.descent = fontMetricsInt.bottom;
                        }
                        return rect.right;
                    }

                    @Override
                    public void draw(Canvas canvas, CharSequence text, int start, int end,
                                     float x, int top, int y, int bottom, Paint paint) {
//                        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
                        Drawable drawable = getDrawable();
                        canvas.save();

                        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                        int fontHeight = fmPaint.descent - fmPaint.ascent;
                        int centerY = y + fmPaint.descent - fontHeight / 2;
                        int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;

                        canvas.translate(x, transY);
                        drawable.draw(canvas);
                        canvas.restore();
                    }
                };
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
