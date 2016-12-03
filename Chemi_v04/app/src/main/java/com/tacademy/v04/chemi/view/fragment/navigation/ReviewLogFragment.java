package com.tacademy.v04.chemi.view.fragment.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;
import com.tacademy.v04.chemi.model.User;
import com.tacademy.v04.chemi.view.activity.product.ReviewActivity;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 * flows from Navigation Drawer menu Button
 * Manage user's written Review
 */

public class ReviewLogFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";

    private RecyclerView mReviewLogRecyclerView;
    private ReviewLogAdapter mReviewLogAdapter;
    private ArrayList<Review> mReviews;

    private User mUser;
    private int mUserId;

    public static ReviewLogFragment newInstance() {

        ReviewLogFragment fragment = new ReviewLogFragment();
        return fragment;
    }

    public static ReviewLogFragment newInstance(int userId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);

        ReviewLogFragment fragment = new ReviewLogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReviewLogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (mUserId != 0) {
            mUserId = getArguments().getInt(ARG_USER_ID);
        }

        ReviewStorage reviewStorage = ReviewStorage.get(getActivity());
        mReviews = reviewStorage.getReviews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_review_log, container, false);

        mReviewLogRecyclerView = (RecyclerView) view.findViewById(R.id.review_log_recycler_view);
        mReviewLogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mReviewLogAdapter == null) {
            mReviewLogAdapter = new ReviewLogAdapter(mReviews);
            mReviewLogRecyclerView.setAdapter(mReviewLogAdapter);
        } else {
            mReviewLogAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }

    private class ReviewLogAdapter extends RecyclerView.Adapter<ReviewLogHolder> {

        private ArrayList<Review> mReviews;

        public ReviewLogAdapter(ArrayList<Review> reviews) {
            mReviews = reviews;
        }

        @Override
        public ReviewLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_review, parent, false);
            return new ReviewLogHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewLogHolder holder, int position) {
            Review review = mReviews.get(position);
            holder.bindReviewLog(review);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }
    }

    private class ReviewLogHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Review mReview;

        private ImageView mReviewCardUserImageView;
        private TextView mReviewCardWriterTextView;
        private RatingBar mReviewCardRatingBar;
        private TextView mReviewCardUserInfoStateTextView;
        private ImageView mReviewCardPhotoImageView;

        private TextView mReviewPositiveContent;
        private TextView mReviewNegativeContent;

        public ReviewLogHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mReviewCardUserImageView = (ImageView) itemView.findViewById(R.id.list_item_review_card_user_image_view);
            mReviewCardWriterTextView = (TextView) itemView.findViewById(R.id.list_item_review_card_user_nickname_text_view);
            mReviewCardRatingBar = (RatingBar) itemView.findViewById(R.id.list_item_review_card_rating_bar);
            mReviewCardUserInfoStateTextView = (TextView) itemView.findViewById(R.id.list_item_review_card_user_info_state_text);
            mReviewCardPhotoImageView = (ImageView) itemView.findViewById(R.id.list_item_review_card_photo_image_view);

            mReviewPositiveContent = (TextView) itemView.findViewById(R.id.list_item_review_card_positive_content);
            mReviewNegativeContent = (TextView) itemView.findViewById(R.id.list_item_review_card_negative_content);
        }

        public void bindReviewLog(Review review) {
            mReview = review;

            mReviewCardWriterTextView.setText(mReview.getName());
            mReviewCardUserInfoStateTextView.setText(getString(
                    R.string.review_card_user_symptom, mReview.getGender(),
                    String.valueOf(mReview.getBirthYear()), String.valueOf(mReview.getChild())));
            if (mReview.isPhotoCheck()) {
                mReviewCardPhotoImageView.setImageResource(R.drawable.ic_photo_camera_24dp);
            } else {
                mReviewCardPhotoImageView.setImageResource(R.drawable.ic_photo_camera_border_24dp);
            }

            mReviewPositiveContent.setText(mReview.getPositiveContent());
            mReviewNegativeContent.setText(mReview.getNegativeContent());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ReviewActivity.newIntent(getActivity(), mReview.getId());
            startActivity(intent);
        }
    }
}
