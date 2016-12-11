package com.tacademy.v04.chemi.view.fragment.product;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;

import java.util.ArrayList;
import java.util.UUID;

import static com.tacademy.v04.chemi.common.network.NetworkConfig.IMAGE_URL_HOST;

/**
 * Created by yoon on 2016. 11. 22..
 */

public class ReviewFragment extends Fragment {

    private static final String TAG = ReviewFragment.class.getSimpleName();

    private static final String ARG_REVIEW = "review_id";

    private Review mReview;
    private ImageView mReviewCardDetailUserImageView;
    private TextView mReviewCardDetailWriterTextView;
    private RatingBar mReviewCardDetailRatingBar;
    private TextView mReviewCardDetailUserInfoStateTextView;
    private ImageView mReviewCardDetailPhotoImageView;
    private TextView mReviewCardDetailSymptomTextView1;
    private TextView mReviewCardDetailSymptomTextView2;
    private TextView mReviewCardDetailPositiveTextView;
    private TextView mReviewCardDetailNegativeTextView;

    private RecyclerView mReviewCardDetailImageRecyclerView;
    private ReviewImageAdapter mReviewImageAdapter;
    private ArrayList<Integer> mReviewImageResIds;
    private ArrayList<String> mReviewImagePaths;

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
        Log.i(TAG, mReview.toString());
//        mReviewImageResIds = mReview.getImageResIdArray();
        mReviewImagePaths = mReview.getImagePaths();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        mReviewCardDetailUserImageView = (ImageView) view.findViewById(R.id.review_card_user_image_view);
        mReviewCardDetailWriterTextView = (TextView) view.findViewById(R.id.review_card_user_nickname_text_view);
        mReviewCardDetailRatingBar = (RatingBar) view.findViewById(R.id.review_card_rating_bar);
        mReviewCardDetailUserInfoStateTextView = (TextView) view.findViewById(R.id.review_card_user_info_state_text);
        mReviewCardDetailPhotoImageView = (ImageView) view.findViewById(R.id.review_card_photo_image_view);

        mReviewCardDetailSymptomTextView1 = (TextView) view.findViewById(R.id.review_card_symptom_1);
        mReviewCardDetailSymptomTextView2 = (TextView) view.findViewById(R.id.review_card_symptom_2);

        mReviewCardDetailPositiveTextView = (TextView)
                view.findViewById(R.id.review_card_detail_positive_content);
        mReviewCardDetailNegativeTextView = (TextView)
                view.findViewById(R.id.review_card_detail_negative_content);

        mReviewCardDetailImageRecyclerView = (RecyclerView)
                view.findViewById(R.id.review_card_detail_image_recycler_view);
        mReviewCardDetailImageRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

//        mReviewImageAdapter = new ReviewImageAdapter(mReviewImageResIds);
        mReviewImageAdapter = new ReviewImageAdapter(mReviewImagePaths);
        mReviewCardDetailImageRecyclerView.setAdapter(mReviewImageAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindReview(mReview);
    }

    private void bindReview(Review review) {
        mReviewCardDetailWriterTextView.setText(mReview.getName());
        mReviewCardDetailUserInfoStateTextView.setText(getString(
                R.string.review_card_user_symptom, mReview.getGender(),
                String.valueOf(mReview.getBirthYear()), String.valueOf(mReview.getChild())));
        if (mReview.isPhotoCheck()) {
            mReviewCardDetailPhotoImageView.setImageResource(R.drawable.ic_photo_camera_24dp);
        } else {
            mReviewCardDetailPhotoImageView.setImageResource(R.drawable.ic_photo_camera_border_24dp);
        }
        mReviewCardDetailRatingBar.setRating(mReview.getRatingValue());
        if (mReview.getConstitution1()!=null) {
            mReviewCardDetailSymptomTextView1.setText(mReview.getConstitution1());
            mReviewCardDetailSymptomTextView1.setBackgroundResource(R.drawable.widget_oval_border_primary);
        }
        if (mReview.getConstitution2()!=null) {
            mReviewCardDetailSymptomTextView2.setText(mReview.getConstitution2());
            mReviewCardDetailSymptomTextView2.setBackgroundResource(R.drawable.widget_oval_border_primary);
        }

        mReviewCardDetailPositiveTextView.setText(review.getPositiveContent());
        mReviewCardDetailNegativeTextView.setText(review.getNegativeContent());

        mReviewImageAdapter.addItems(mReviewImagePaths);
        mReviewImageAdapter.notifyDataSetChanged();
    }

    private class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageHolder> {

//        private ArrayList<Integer> mReviewImageResIds;
        private ArrayList<String> mReviewImagePaths;

//        public ReviewImageAdapter(ArrayList<Integer> reviewImageResIds) {
//            mReviewImageResIds = reviewImageResIds;
//        }

        public ReviewImageAdapter(ArrayList<String> reviewImagePaths) {
            mReviewImagePaths = reviewImagePaths;
        }

        public void addItems(ArrayList<String> reviewImagePaths) {
            mReviewImagePaths = reviewImagePaths;
        }

        @Override
        public ReviewImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_review_image, parent, false);
            return new ReviewImageHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewImageHolder holder, int position) {
//            Integer integer = mReviewImageResIds.get(position);
//            holder.bindReviewImage(integer);
            String imagePath = mReviewImagePaths.get(position);
            holder.bindReviewImage(imagePath);
        }

        @Override
        public int getItemCount() {
//            return mReviewImageResIds.size();
            return mReviewImagePaths.size();
        }
    }

    private class ReviewImageHolder extends RecyclerView.ViewHolder {

        private Integer mInteger;
        private String mImagePath;

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

        public void bindReviewImage(String imagePath) {
            mImagePath = imagePath;
            Log.i("bindReviewImage", IMAGE_URL_HOST + mImagePath);
            Glide.with(getActivity())
                    .load(IMAGE_URL_HOST + mImagePath)   /*+ File.separator*/
//                    .placeholder(R.drawable.unloaded_image_holder)
                    .error(R.drawable.unloaded_image_holder)
                    .override(140, 140)
                    .centerCrop()
                    .into(mReviewCardDetailReviewImage);
        }
    }
}
