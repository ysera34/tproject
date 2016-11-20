package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ReviewListFragment extends Fragment {

    private RecyclerView mReviewRecyclerView;
    private ReviewAdapter mReviewAdapter;
    private ArrayList<Review> mReviews;

    public ReviewListFragment() {
    }

    public static ReviewListFragment newInstance() {

        ReviewListFragment fragment = new ReviewListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReviewStorage reviewStorage = ReviewStorage.get(getActivity());
        mReviews = reviewStorage.getReviews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        mReviewRecyclerView = (RecyclerView) view.findViewById(R.id.product_detail_review_recycler_view);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if(mReviewAdapter == null) {
            mReviewAdapter = new ReviewAdapter(mReviews);
            mReviewRecyclerView.setAdapter(mReviewAdapter);
        } else {
            mReviewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

        private ArrayList<Review> mReviews;

        public ReviewAdapter(ArrayList<Review> reviews) {
            mReviews = reviews;
        }

        @Override
        public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_review, parent, false);
            return new ReviewHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewHolder holder, int position) {
            Review review = mReviews.get(position);
            holder.bindReview(review);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }
    }

    private class ReviewHolder extends RecyclerView.ViewHolder {

        private Review mReview;

        private TextView mReviewPositiveContent;
        private TextView mReviewNegativeContent;

        public ReviewHolder(View itemView) {
            super(itemView);

            mReviewPositiveContent = (TextView) itemView.findViewById(R.id.review_card_positive_content);
            mReviewNegativeContent = (TextView) itemView.findViewById(R.id.review_card_negative_content);
        }

        public void bindReview(Review review) {
            mReview = review;
            mReviewPositiveContent.setText(mReview.getPositiveContent());
            mReviewNegativeContent.setText(mReview.getNegativeContent());
        }
    }
}
