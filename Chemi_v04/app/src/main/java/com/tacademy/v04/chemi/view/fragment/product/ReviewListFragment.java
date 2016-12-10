package com.tacademy.v04.chemi.view.fragment.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.NetworkConfig;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;
import com.tacademy.v04.chemi.view.activity.product.ReviewActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.SOCKET_TIMEOUT_GET_REQ;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ReviewListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ReviewListFragment.class.getSimpleName();

    private static final String ARG_PRODUCT_ID = "product_id";

    private Button mReviewFormButton;

    private ReviewStorage mReviewStorage;
    private RecyclerView mReviewRecyclerView;
    private ReviewAdapter mReviewAdapter;
    private ArrayList<Review> mReviews;
    private Product mProduct;
    private UUID mProductId;

    public static ReviewListFragment newInstance() {

        ReviewListFragment fragment = new ReviewListFragment();
        return fragment;
    }

    public static ReviewListFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, id);

        ReviewListFragment fragment = new ReviewListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReviewListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        mProduct = ProductStorage.get(getActivity()).getProduct(mProductId);
        mReviewStorage = ReviewStorage.get(getActivity());
        mReviews = mReviewStorage.getReviews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        mReviewFormButton = (Button) view.findViewById(R.id.product_detail_review_form_button);
        mReviewFormButton.setOnClickListener(this);

        mReviewRecyclerView = (RecyclerView) view.findViewById(R.id.product_detail_review_recycler_view);
        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestReviewsJsonObject();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_detail_review_form_button :
//                Intent intent = ReviewActivity.newIntent(getActivity(), REQUEST_REVIEW_FORM);
                Intent intent = ReviewActivity.newIntent(getActivity(), mProduct.getProductId());
                startActivity(intent);
                break;
        }
    }

    private void setupAdapter() {
        if(isAdded()) {
            if(mReviewAdapter == null) {
                mReviewAdapter = new ReviewAdapter(mReviews);
                mReviewRecyclerView.setAdapter(mReviewAdapter);
            } else {
                mReviews = mReviewStorage.getReviews();
                mReviewAdapter.addItems(mReviews);
                mReviewAdapter.notifyDataSetChanged();
            }
        }
    }

    private void requestReviewsJsonObject() {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_HOST + PATH
                + File.separator + mProduct.getProductId() + NetworkConfig.Review.PATH,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

//                        mReviewStorage.setReviews(Parser.parseReviewList(response, mReviews));
                        mReviewStorage.setReviews(Parser.parseReviewList(response));
                        setupAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    private class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Review> mReviews;

        public ReviewAdapter(ArrayList<Review> reviews) {
            mReviews = reviews;
        }

        public void addItems(ArrayList<Review> reviews) {
            mReviews = reviews;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view;
            if (viewType == EMPTY_VIEW) {
                view = layoutInflater.inflate(R.layout.view_empty_review, parent, false);
                return new EmptyViewHolder(view);
            }
            if (viewType == SUMMERY_VIEW) {
                view = layoutInflater.inflate(R.layout.view_review_summary, parent, false);
                return new SummaryViewHolder(view);
            } else if (viewType == ITEM_VIEW) {
                view = layoutInflater.inflate(R.layout.list_item_review, parent, false);
                return new ReviewHolder(view);
            }
            throw new RuntimeException("there is no type that matches thy type");
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ReviewHolder) {
                Review review = mReviews.get(position - 1);
                ((ReviewHolder) holder).bindReview(review);
            } else if (holder instanceof SummaryViewHolder) {
                SummaryViewHolder summaryViewHolder = (SummaryViewHolder) holder;
            }

        }

        @Override
        public int getItemCount() {
            return mReviews.size() > 0 ? mReviews.size() + 1 : 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (mReviews.size() == 0) {
                return EMPTY_VIEW;
            }
            if (position == 0) {
                return SUMMERY_VIEW;
            }
            return ITEM_VIEW;
//            return super.getItemViewType(position);
        }
    }

    private static final int EMPTY_VIEW = -1;
    private static final int SUMMERY_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class SummaryViewHolder extends RecyclerView.ViewHolder {

        public SummaryViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ReviewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Review mReview;

        private ImageView mReviewCardUserImageView;
        private TextView mReviewCardWriterTextView;
        private RatingBar mReviewCardRatingBar;
        private TextView mReviewCardUserInfoStateTextView;
        private ImageView mReviewCardPhotoImageView;
        private TextView mReviewCardSymptomTextView1;
        private TextView mReviewCardSymptomTextView2;

        private TextView mReviewPositiveContent;
        private TextView mReviewNegativeContent;

        public ReviewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mReviewCardUserImageView = (ImageView) itemView.findViewById(R.id.list_item_review_card_user_image_view);
            mReviewCardWriterTextView = (TextView) itemView.findViewById(R.id.list_item_review_card_user_nickname_text_view);
            mReviewCardRatingBar = (RatingBar) itemView.findViewById(R.id.list_item_review_card_rating_bar);
            mReviewCardUserInfoStateTextView = (TextView) itemView.findViewById(R.id.list_item_review_card_user_info_state_text);
            mReviewCardPhotoImageView = (ImageView) itemView.findViewById(R.id.list_item_review_card_photo_image_view);
            mReviewCardSymptomTextView1 = (TextView) itemView.findViewById(R.id.list_item_review_card_symptom_1);
            mReviewCardSymptomTextView2 = (TextView) itemView.findViewById(R.id.list_item_review_card_symptom_2);

            mReviewPositiveContent = (TextView) itemView.findViewById(R.id.list_item_review_card_positive_content);
            mReviewNegativeContent = (TextView) itemView.findViewById(R.id.list_item_review_card_negative_content);
        }

        public void bindReview(Review review) {
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
            mReviewCardRatingBar.setRating(mReview.getRatingValue());
            if (mReview.getConstitution1()!=null) {
                mReviewCardSymptomTextView1.setText(mReview.getConstitution1());
                mReviewCardSymptomTextView1.setBackgroundResource(R.drawable.widget_oval_border_primary);
            }
            if (mReview.getConstitution2()!=null) {
                mReviewCardSymptomTextView2.setText(mReview.getConstitution2());
                mReviewCardSymptomTextView2.setBackgroundResource(R.drawable.widget_oval_border_primary);
            }

            mReviewCardSymptomTextView2.setText(mReview.getConstitution2());

            mReviewPositiveContent.setText(mReview.getPositiveContent());
            mReviewNegativeContent.setText(mReview.getNegativeContent());
        }

        @Override
        public void onClick(View view) {

//            FragmentManager fm = getChildFragmentManager();
//            Fragment containerFragment = fm.findFragmentById(R.id.fragment_product_container);
//
//            fm.beginTransaction()
//                    .addToBackStack(null)
//                    .add(R.id.fragment_product_container, containerFragment)
//                    .commit();

            requestReviewJsonObject(mReview);
        }
    }

     private void requestReviewJsonObject(final Review review) {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_HOST + PATH
                + File.separator + mProduct.getProductId() + NetworkConfig.Review.PATH + File.separator + review.getReviewId(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

//                        mReviewStorage.setReviews(Parser.parseReviewList(response, mReviews));
//                        mReviewStorage.setReviews(Parser.parseReviewList(response));
                        mReviewStorage.setReview(Parser.parseReview(response, review));

                        Intent intent = ReviewActivity.newIntent(getActivity(), review.getId());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

         jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }
}
