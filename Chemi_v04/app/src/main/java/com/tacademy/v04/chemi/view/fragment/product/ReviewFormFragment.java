package com.tacademy.v04.chemi.view.fragment.product;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.NetworkConfig;
import com.tacademy.v04.chemi.common.util.adapter.BottomSheetMenuAdapter;
import com.tacademy.v04.chemi.model.BottomSheetMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ReviewFormFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ReviewFormFragment.class.getSimpleName();

    private static final String ARG_PRODUCT_ID = "product_id";

    private int mProductId;

    private ScrollView mReviewFormScrollView;
    private TextView mReviewFormReviewPositiveLengthTextView;
    private EditText mReviewFormReviewPositiveEditText;
    private TextView mReviewFormReviewNegativeLengthTextView;
    private EditText mReviewFormReviewNegativeEditText;
    private ImageButton mReviewFormReviewImageButton1;
    private ImageButton mReviewFormReviewImageButton2;
    private ImageButton mReviewFormReviewImageButton3;

    private BottomSheetDialog mMenuBottomSheetDialog;

    public static ReviewFormFragment newInstance(int productId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId);

        ReviewFormFragment fragment = new ReviewFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ReviewFormFragment newInstance() {

        Bundle args = new Bundle();

        ReviewFormFragment fragment = new ReviewFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReviewFormFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mProductId  = getArguments().getInt(ARG_PRODUCT_ID);
        Log.i(TAG, "mProductId : " + String.valueOf(mProductId));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_review_form, container, false);

        mReviewFormScrollView = (ScrollView) view.findViewById(R.id.review_form_scroll_view);
        mReviewFormScrollView.setVerticalScrollBarEnabled(false);
        mReviewFormReviewPositiveLengthTextView = (TextView)
                view.findViewById(R.id.review_form_review_positive_length_text_view);
        mReviewFormReviewNegativeLengthTextView = (TextView)
                view.findViewById(R.id.review_form_review_negative_length_text_view);
        mReviewFormReviewPositiveEditText = (EditText)
                view.findViewById(R.id.review_form_review_positive_edit_text);
        mReviewFormReviewPositiveLengthTextView.setText(
                getString(R.string.review_form_string_length_form, String.valueOf(0)));
        mReviewFormReviewPositiveEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mReviewFormReviewPositiveLengthTextView.setText(
                        getString(R.string.review_form_string_length_form, String.valueOf(charSequence.length())));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mReviewFormReviewNegativeEditText = (EditText)
                view.findViewById(R.id.review_form_review_negative_edit_text);
        mReviewFormReviewNegativeLengthTextView.setText(
                getString(R.string.review_form_string_length_form, String.valueOf(0)));
        mReviewFormReviewNegativeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mReviewFormReviewNegativeLengthTextView.setText(
                        getString(R.string.review_form_string_length_form, String.valueOf(charSequence.length())));
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mReviewFormReviewImageButton1 = (ImageButton)
                view.findViewById(R.id.review_form_review_image_button1);
        mReviewFormReviewImageButton2 = (ImageButton)
                view.findViewById(R.id.review_form_review_image_button2);
        mReviewFormReviewImageButton3 = (ImageButton)
                view.findViewById(R.id.review_form_review_image_button3);
        mReviewFormReviewImageButton1.setOnClickListener(this);
        mReviewFormReviewImageButton2.setOnClickListener(this);
        mReviewFormReviewImageButton3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.review_form_review_image_button1 :
                createMenuBottomSheetDialog();
                break;
            case R.id.review_form_review_image_button2 :
                break;
            case R.id.review_form_review_image_button3 :
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        if ((menu.findItem(R.id.action_home)) != null) {
            menu.findItem(R.id.action_home).setVisible(false);
        }
        if ((menu.findItem(R.id.action_search)) != null) {
            menu.findItem(R.id.action_search).setVisible(false);
        }
        inflater.inflate(R.menu.menu_review_form_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_send) {
            requestJsonObject();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean dismissDialog() {
        if (mMenuBottomSheetDialog != null && mMenuBottomSheetDialog.isShowing()) {
            mMenuBottomSheetDialog.dismiss();
            return true;
        }
        return false;
    }

    private void createMenuBottomSheetDialog() {
        if (dismissDialog()) {
            return;
        }

        ArrayList<BottomSheetMenu> menus = new ArrayList<>();
        menus.add(new BottomSheetMenu(R.drawable.ic_drawer_archive, R.string.bottom_sheet_menu_camera));
        menus.add(new BottomSheetMenu(R.drawable.ic_drawer_archive, R.string.bottom_sheet_menu_gallery));

        BottomSheetMenuAdapter menuAdapter = new BottomSheetMenuAdapter(menus);
        menuAdapter.setOnItemClickListener(new BottomSheetMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BottomSheetMenuAdapter.MenuItemHolder item, int position) {
                // dismissDialog();
            }
        });

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.bottom_sheet_menu_recycler_view, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.bottom_sheet_menu_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);

        mMenuBottomSheetDialog = new BottomSheetDialog(getActivity());
        mMenuBottomSheetDialog.setContentView(view);
        mMenuBottomSheetDialog.show();
    }

    private void requestJsonObject() {

        Log.w(TAG, "requestJsonObject : " + URL_HOST + PATH + File.separator + String.valueOf(mProductId) + NetworkConfig.Review.PATH);

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_send_data),
                        getString(R.string.load_please_wait), false, false);

        StringRequest postRequest = new StringRequest(POST,
                URL_HOST + PATH + File.separator + String.valueOf(mProductId) + NetworkConfig.Review.PATH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.i(TAG, "onResponse : " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", "3");
                params.put("rating", "3.5");
                params.put("reviewp", "안녕하세요~~``");
                params.put("reviewn", "영어test 1234 #$%#");
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(postRequest);
    }
}
