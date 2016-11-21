package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 22..
 */

public class ReviewFragment extends Fragment {

    private static final String ARG_REVIEW = "review_id";

    private Review mReview;
    private TextView mReviewCardDetailPositiveTextView;
    private TextView mReviewCardDetailNegativeTextView;
    private ViewPager mReviewCardDetailImageViewPager;
    private ReviewImageViewPagerAdapter mViewPagerAdapter;
    private int[] mReviewImageResIdArray;

    public ReviewFragment() {
    }

    public static ReviewFragment newInstance() {

        ReviewFragment fragment = new ReviewFragment();
        return fragment;
    }

    public static ReviewFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_REVIEW, id);

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID id = (UUID) getArguments().getSerializable(ARG_REVIEW);
        mReview = ReviewStorage.get(getActivity()).getReview(id);
        mReviewImageResIdArray = mReview.getImageResIdArray();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        mReviewCardDetailPositiveTextView = (TextView)
                view.findViewById(R.id.review_card_detail_positive_content);
        mReviewCardDetailNegativeTextView = (TextView)
                view.findViewById(R.id.review_card_detail_negative_content);

        mReviewCardDetailImageViewPager = (ViewPager)
                view.findViewById(R.id.review_card_detail_image_view_pager);
        mReviewCardDetailImageViewPager.setClipToPadding(false);
        mViewPagerAdapter = new ReviewImageViewPagerAdapter(mReviewImageResIdArray);
        mReviewCardDetailImageViewPager.setAdapter(mViewPagerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReviewCardDetailPositiveTextView.setText(mReview.getPositiveContent());
        mReviewCardDetailNegativeTextView.setText(mReview.getNegativeContent());
    }

    private class ReviewImageViewPagerAdapter extends PagerAdapter {

        private int[] mReviewImageArray;

        public ReviewImageViewPagerAdapter(int[] reviewImageArray) {
            mReviewImageArray = reviewImageArray;
        }

        @Override
        public int getCount() {
            return mReviewImageArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
//            return false;
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(mReviewImageArray[position]);
            container.addView(imageView);
            return imageView;
        }
    }
}
