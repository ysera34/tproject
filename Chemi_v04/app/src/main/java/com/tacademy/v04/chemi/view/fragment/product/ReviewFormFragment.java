package com.tacademy.v04.chemi.view.fragment.product;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.MultipartRequestHelper;
import com.tacademy.v04.chemi.common.network.NetworkConfig;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.common.network.VolleyMultipartRequest;
import com.tacademy.v04.chemi.common.util.adapter.BottomSheetMenuAdapter;
import com.tacademy.v04.chemi.model.BottomSheetMenu;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.ReviewStorage;
import com.tacademy.v04.chemi.view.activity.product.ReviewActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tacademy.v04.chemi.common.Common.PICK_FROM_CAMERA1;
import static com.tacademy.v04.chemi.common.Common.PICK_FROM_CAMERA2;
import static com.tacademy.v04.chemi.common.Common.PICK_FROM_CAMERA3;
import static com.tacademy.v04.chemi.common.Common.PICK_FROM_GALLERY1;
import static com.tacademy.v04.chemi.common.Common.PICK_FROM_GALLERY2;
import static com.tacademy.v04.chemi.common.Common.PICK_FROM_GALLERY3;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.SOCKET_TIMEOUT_POST_REQ;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ReviewFormFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ReviewFormFragment.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST = 301;
    private static final int GALLERY_IMAGE_REQUEST = 302;
    private static final String ARG_PRODUCT_ID = "product_id";

    private int mProductId;

    private ScrollView mReviewFormScrollView;
    private RatingBar mReviewFormRatingBar;
    private TextView mReviewFormRatingBarValueStatusTextView;
    private TextView mReviewFormReviewPositiveLengthTextView;
    private EditText mReviewFormReviewPositiveEditText;
    private TextView mReviewFormReviewNegativeLengthTextView;
    private EditText mReviewFormReviewNegativeEditText;
    private ImageButton mReviewFormReviewImageButton1;
    private ImageButton mReviewFormReviewImageButton2;
    private ImageButton mReviewFormReviewImageButton3;

    private BottomSheetDialog mMenuBottomSheetDialog;
    private Uri mImageUri;
    private File mImageDir;
    private String mImagePath;

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
        mReviewFormRatingBar = (RatingBar) view.findViewById(R.id.review_form_ratingBar);
        mReviewFormRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });
        mReviewFormRatingBarValueStatusTextView = (TextView)
                view.findViewById(R.id.review_form_rating_value_status_text_view);
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

    private static final String IMAGE_POSITION = "image_button_position";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.review_form_ratingBar :

                break;
            case R.id.review_form_review_image_button1 :
                createMenuBottomSheetDialog(0);
                break;
            case R.id.review_form_review_image_button2 :
                createMenuBottomSheetDialog(1);
                break;
            case R.id.review_form_review_image_button3 :
                createMenuBottomSheetDialog(2);
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

    private void createMenuBottomSheetDialog(final int imagePosition) {
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

                String state = Environment.getExternalStorageState();
                if (!Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                    Toast.makeText(getActivity(), "SD카드가 없어 종료 합니다.", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                }

                String currentAppPackage = getActivity().getPackageName();
                mImageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), currentAppPackage);

                checkPermission();

                if (!mImageDir.exists()) {
                    Toast.makeText(getActivity(), "저장할 디렉토리가 생성 되었습니다.", Toast.LENGTH_SHORT).show();
                }

                int[] cameraRequestCodeArr = {PICK_FROM_CAMERA1, PICK_FROM_CAMERA2, PICK_FROM_CAMERA3};

                if (position == 0) {
                    Toast.makeText(getActivity(), "카메라", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                    if (getActivity().getPackageManager().hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (mImagePath != null) {
                            mImagePath = "";
                        }
                        String imageName = "upload_" + String.valueOf(System.currentTimeMillis() / 1000) + ".png";
                        File imageFile = new File(mImageDir, imageName);
                        mImagePath = imageFile.getAbsolutePath();
                        mImageUri = Uri.fromFile(imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                        startActivityForResult(intent, cameraRequestCodeArr[imagePosition]);

                    } else {
                        Toast.makeText(getActivity(), "카메라를 사용 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }

                } else if (position == 1) {

                    int[] galleryRequestCodeArr = {PICK_FROM_GALLERY1, PICK_FROM_GALLERY2, PICK_FROM_GALLERY3};
                    Toast.makeText(getActivity(), "갤러리", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    startActivityForResult(intent, galleryRequestCodeArr[imagePosition]);
                }
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

    private static final int PERMISSION_REQUEST_STORAGE = 100;

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getActivity(), "Read/Write external storage", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);

            } else {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE :
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Log.d(TAG, "Permission always deny");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Uri returnedImageUri;
        if (resultCode != Activity.RESULT_OK || resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_GALLERY1 :
                returnedImageUri = data.getData();
                if (returnedImageUri != null) {
                    mReviewFormReviewImageButton1.setImageURI(returnedImageUri);
                } else {
                    Bundle extras = data.getExtras();
                    Bitmap returnedBitmap = (Bitmap) extras.get("data");
                }
                break;
            case PICK_FROM_GALLERY2 :
                returnedImageUri = data.getData();
                if (returnedImageUri != null) {
                    mReviewFormReviewImageButton2.setImageURI(returnedImageUri);
                } else {
                    Bundle extras = data.getExtras();
                    Bitmap returnedBitmap = (Bitmap) extras.get("data");
                }
                break;
            case PICK_FROM_GALLERY3 :
                returnedImageUri = data.getData();
                if (returnedImageUri != null) {
                    mReviewFormReviewImageButton3.setImageURI(returnedImageUri);
                } else {
                    Bundle extras = data.getExtras();
                    Bitmap returnedBitmap = (Bitmap) extras.get("data");
                }
                break;
            case PICK_FROM_CAMERA1 :
                mReviewFormReviewImageButton1.setImageURI(Uri.fromFile(new File(mImagePath)));
                break;
            case PICK_FROM_CAMERA2 :
                mReviewFormReviewImageButton1.setImageURI(Uri.fromFile(new File(mImagePath)));
                break;
            case PICK_FROM_CAMERA3 :
                mReviewFormReviewImageButton1.setImageURI(Uri.fromFile(new File(mImagePath)));
                break;
        }
    }


    private void requestJsonObject() {

        Log.w(TAG, "requestJsonObject : " +
                URL_HOST + PATH + File.separator + String.valueOf(mProductId) + NetworkConfig.Review.PATH);

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_send_data),
                        getString(R.string.load_please_wait), false, false);

//        StringRequest postRequest = new StringRequest(POST,
//                URL_HOST + PATH + File.separator + String.valueOf(mProductId) + NetworkConfig.Review.PATH,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        pDialog.dismiss();
//                        Log.i(TAG, "onResponse : " + response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pDialog.dismiss();
//                        Log.w(TAG, "onErrorResponse : " + error.toString());
//                    }
//                }
//        ) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return super.getBody();
//            }
//
//            @Override
//            public String getBodyContentType() {
////                return super.getBodyContentType();
////                return "application/x-www-form-urlencoded";
//                return "multipart/form-data; charset=UTF-8";
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("userid", "3");
//                params.put("rating", "3.5");
//                params.put("reviewp", "안녕하세요~~``");
//                params.put("reviewn", "영어test 1234 #$%#");
//                return params;
//            }
//        };

//        Volley.newRequestQueue(getActivity()).add(postRequest);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                URL_HOST + PATH + File.separator + String.valueOf(mProductId) + NetworkConfig.Review.PATH,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        pDialog.dismiss();
                        Log.i(TAG, "onResponse : " + response.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.w(TAG, "onErrorResponse : " + error.toString());
                Toast.makeText(getActivity(), "데이터 전송 중, 서버에서 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", "3");
                params.put("rating", String.valueOf(mReviewFormRatingBar.getRating()));
                params.put("reviewp", encodeUTF8(mReviewFormReviewPositiveEditText.getText().toString()));
                params.put("reviewn", encodeUTF8(mReviewFormReviewNegativeEditText.getText().toString()));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("images1", new DataPart("?.png", MultipartRequestHelper.getFileDataFromDrawable(getActivity(), mReviewFormReviewImageButton1.getDrawable()), "image/*"));
                params.put("images2", new DataPart("?.png", MultipartRequestHelper.getFileDataFromDrawable(getActivity(), mReviewFormReviewImageButton2.getDrawable()), "image/*"));
                params.put("images3", new DataPart("?.png", MultipartRequestHelper.getFileDataFromDrawable(getActivity(), mReviewFormReviewImageButton3.getDrawable()), "image/*"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_POST_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(multipartRequest);

//        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    private String encodeUTF8 (String rawStr) {
        String utf8String = null;
        try {
            utf8String = URLEncoder.encode(rawStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "UnsupportedEncodingException : " + e.toString());
        }
        return utf8String;
    }

    private void requestReviewJsonObject(final Review review) {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_HOST + PATH
                + File.separator + String.valueOf(mProductId) + NetworkConfig.Review.PATH + File.separator + review.getReviewId(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

//                        mReviewStorage.setReviews(Parser.parseReviewList(response, mReviews));
//                        mReviewStorage.setReviews(Parser.parseReviewList(response));
                        ReviewStorage.get(getActivity()).setReview(Parser.parseReview(response, review));

                        Intent intent = ReviewActivity.newIntent(getActivity(), review.getId());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        pDialog.dismiss();
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

}
