package com.tacademy.v04.chemi.view.fragment.product;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 22..
 */

public class ReviewFragment extends Fragment {

    private static final String ARG_REVIEW = "review_id";

    private Review mReview;
    private TextView mReviewCardDetailPositiveTextView;
    private TextView mReviewCardDetailNegativeTextView;

    private RecyclerView mReviewCardDetailImageRecyclerView;
    private ReviewImageAdapter mReviewImageAdapter;
    private ArrayList<Integer> mReviewImageResIds;

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
        mReviewImageResIds = mReview.getImageResIdArray();
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

        mReviewCardDetailImageRecyclerView = (RecyclerView)
                view.findViewById(R.id.review_card_detail_image_recycler_view);
        mReviewCardDetailImageRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mReviewImageAdapter = new ReviewImageAdapter(mReviewImageResIds);
        mReviewCardDetailImageRecyclerView.setAdapter(mReviewImageAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReviewCardDetailPositiveTextView.setText(mReview.getPositiveContent());
        mReviewCardDetailNegativeTextView.setText(mReview.getNegativeContent());
    }

    private class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageHolder> {

        private ArrayList<Integer> mReviewImageResIds;

        public ReviewImageAdapter(ArrayList<Integer> reviewImageResIds) {
            mReviewImageResIds = reviewImageResIds;
        }

        @Override
        public ReviewImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_review_image, parent, false);
            return new ReviewImageHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewImageHolder holder, int position) {
            Integer integer = mReviewImageResIds.get(position);
            holder.bindReviewImage(integer);
        }

        @Override
        public int getItemCount() {
            return mReviewImageResIds.size();
        }
    }

    private class ReviewImageHolder extends RecyclerView.ViewHolder {

        private Integer mInteger;

        private ImageView mReviewCardDetailReviewImage;

        public ReviewImageHolder(View itemView) {
            super(itemView);

            mReviewCardDetailReviewImage = (ImageView)
                    itemView.findViewById(R.id.list_item_review_card_detail_review_image);
        }

        public void bindReviewImage(Integer integer) {
            mInteger = integer;
            mReviewCardDetailReviewImage.setImageResource(mInteger);
        }
    }
}
