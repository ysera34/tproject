package com.tacademy.v04.chemi.view.fragment.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.NetworkConfig;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.model.CustomSearchStorage;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.Word;
import com.tacademy.v04.chemi.view.activity.product.ProductListActivity;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.android.volley.Request.Method.GET;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.SOCKET_TIMEOUT_GET_REQ;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.KEYWORD_PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_CATEGORY_QUERY;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_EXCHEMICAL_QUERY;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_FILTER_PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_INCHEMICAL_QUERY;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_LETTER_QUERY;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_TARGET_PRODUCT;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class CustomSearchFragment extends Fragment
        implements View.OnClickListener {

    private static final String TAG = CustomSearchFragment.class.getSimpleName();
    public static final int INCHEMICAL_RESULT = 1001;
    public static final int EXCHEMICAL_RESULT = 1002;

    private CustomSearchStorage mCustomSearchStorage;
    private InputMethodManager mInputMethodManager;

    private Button mLoadLogButton;
    private Button mResetButton;
    private Button mCategoryButton;
    private Button mConstitutionButton;
    private Button mChemicalButton;
    private Button mSaveButton;
    private Button mSubmitButton;

    private LayoutInflater mLayoutInflater;
    private BottomSheetDialog mCategoryBottomSheetDialog;
    private BottomSheetDialog mConstitutionBottomSheetDialog;
    private BottomSheetDialog mChemicalBottomSheetDialog;

    private AutoCompleteTextView mChemicalIncludeAutoCompleteTextView;
    private AutoCompleteTextView mChemicalExcludeAutoCompleteTextView;
    private ArrayAdapter<String> mChemicalIncludeResultAdapter;
    private ArrayAdapter<String> mChemicalExcludeResultAdapter;
    private ArrayList<Word> mInchemicalResult;
    private ArrayList<Word> mExchemicalResult;
    private int mIncludeChemicalId;
    private int mExcludeChemicalId;
    private int mCategoryId;

    private AutoCompleteTextView mBottomSheetChemicalIncludeAutoCompleteTextView;
    private AutoCompleteTextView mBottomSheetChemicalExcludeAutoCompleteTextView;


    public CustomSearchFragment() {
    }

    public static CustomSearchFragment newInstance() {

        CustomSearchFragment fragment = new CustomSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomSearchStorage = CustomSearchStorage.get(getActivity());

        mLayoutInflater = LayoutInflater.from(getActivity());
        mInchemicalResult = new ArrayList<>();
        mExchemicalResult = new ArrayList<>();
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_custom_search, container, false);
        mLoadLogButton = (Button) view.findViewById(R.id.custom_search_load_log_button);
        mLoadLogButton.setOnClickListener(this);
        mResetButton = (Button) view.findViewById(R.id.custom_search_reset_button);
        mResetButton.setOnClickListener(this);
        mCategoryButton = (Button) view.findViewById(R.id.custom_search_category_button);
        mCategoryButton.setOnClickListener(this);
        mConstitutionButton = (Button) view.findViewById(R.id.custom_search_constitution_button);
        mConstitutionButton.setOnClickListener(this);
        mChemicalButton = (Button) view.findViewById(R.id.custom_search_chemical_button);
        mChemicalButton.setOnClickListener(this);

        mSaveButton = (Button) view.findViewById(R.id.custom_search_save_log_button);
        mSaveButton.setOnClickListener(this);
        mSubmitButton = (Button) view.findViewById(R.id.custom_search_submit_button);
        mSubmitButton.setOnClickListener(this);

        mChemicalIncludeAutoCompleteTextView = (AutoCompleteTextView)
                view.findViewById(R.id.chemical_include_auto_complete_text_view);
        mChemicalIncludeAutoCompleteTextView.setThreshold(0);
        mChemicalIncludeAutoCompleteTextView.setSelection(mChemicalIncludeAutoCompleteTextView.getText().length());
        mChemicalIncludeAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, charSequence.toString());
//                Toast.makeText(getActivity(), "업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
                if (charSequence.length() <= 1) {
                    requestIncludeChemicalWordsJsonObject(view, charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mChemicalExcludeAutoCompleteTextView = (AutoCompleteTextView)
                view.findViewById(R.id.chemical_exclude_auto_complete_text_view);
        mChemicalExcludeAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, charSequence.toString());
//                Toast.makeText(getActivity(), "업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
                if (charSequence.length() <= 1) {
                    requestExcludeChemicalWordsJsonObject(view, charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        BottomSheetBehavior bottomSheetBehavior;
        switch (view.getId()) {
            case R.id.custom_search_load_log_button :
                break;
            case R.id.custom_search_reset_button :
                break;
            case R.id.custom_search_category_button :
                mCategoryBottomSheetDialog = new BottomSheetDialog(getActivity());
                View mBottomSheetDialog1 = mLayoutInflater
                        .inflate(R.layout.bottom_sheet_custom_search_category, null);
                mCategoryBottomSheetDialog.setContentView(mBottomSheetDialog1);
                bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetDialog1.getParent());
                bottomSheetBehavior.setPeekHeight(R.dimen.custom_peek_height);

                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section11)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section12)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section13)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section21)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section22)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section23)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section31)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section32)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section33)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section41)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section42)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section43)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section51)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section52)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section53)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section61)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section62)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section63)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section71)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section72)).setOnClickListener(this);
                (mBottomSheetDialog1.findViewById(R.id.bottom_sheet_category_section73)).setOnClickListener(this);
                
                mCategoryBottomSheetDialog.show();
                break;
            case R.id.custom_search_constitution_button :
                mConstitutionBottomSheetDialog = new BottomSheetDialog(getActivity());
                View mBottomSheetDialog2 = mLayoutInflater
                        .inflate(R.layout.bottom_sheet_custom_search_constitution, null);
                mConstitutionBottomSheetDialog.setContentView(mBottomSheetDialog2);
                bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetDialog2.getParent());
                bottomSheetBehavior.setPeekHeight(R.dimen.custom_peek_height);
                mConstitutionBottomSheetDialog.show();
                break;
            case R.id.custom_search_chemical_button :
                mChemicalBottomSheetDialog = new BottomSheetDialog(getActivity());
                View mBottomSheetDialog3 = mLayoutInflater
                        .inflate(R.layout.bottom_sheet_custom_search_chemical, null);
                mChemicalBottomSheetDialog.setContentView(mBottomSheetDialog3);
                bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetDialog3.getParent());
                bottomSheetBehavior.setPeekHeight(R.dimen.custom_peek_height);

                mBottomSheetChemicalIncludeAutoCompleteTextView = (AutoCompleteTextView) mBottomSheetDialog3.
                        findViewById(R.id.bottom_sheet_chemical_include_auto_complete_text_view);
                mBottomSheetChemicalExcludeAutoCompleteTextView = (AutoCompleteTextView) mBottomSheetDialog3.
                        findViewById(R.id.bottom_sheet_chemical_exclude_auto_complete_text_view);
                mBottomSheetChemicalIncludeAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Toast.makeText(getActivity(), "include" + charSequence, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                mBottomSheetChemicalExcludeAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Toast.makeText(getActivity(), "exclude" + charSequence, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                mChemicalBottomSheetDialog.show();

                break;

            case R.id.bottom_sheet_category_section11 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section11",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("세탁세제");
                break;
            case R.id.bottom_sheet_category_section12 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section12",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("섬유유연제");
                break;
            case R.id.bottom_sheet_category_section13 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section13",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("표백제");
                break;
            case R.id.bottom_sheet_category_section21 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section21",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("식기세척세제");
                break;
            case R.id.bottom_sheet_category_section22 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section22",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("주방세정제");
                break;
            case R.id.bottom_sheet_category_section23 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section23",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_category_section31 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section31",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("헤어");
                break;
            case R.id.bottom_sheet_category_section32 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section32",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("바디 / 세안");
                break;
            case R.id.bottom_sheet_category_section33 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section33",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("욕실 세정제");
                break;
            case R.id.bottom_sheet_category_section41 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section41",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("섬유 탈취제");
                break;
            case R.id.bottom_sheet_category_section42 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section42",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("방향제");
                break;
            case R.id.bottom_sheet_category_section43 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section43",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_category_section51 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section51",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("유아용 기저귀");
                break;
            case R.id.bottom_sheet_category_section52 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section52",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("유아용 물티슈");
                break;
            case R.id.bottom_sheet_category_section53 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section53",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                break;
            case R.id.bottom_sheet_category_section61 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section61",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("생리대");
                break;
            case R.id.bottom_sheet_category_section62 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section62",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("여성 청결제");
                break;
            case R.id.bottom_sheet_category_section63 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section63",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("성인용 기저귀");
                break;
            case R.id.bottom_sheet_category_section71 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section71",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("물티슈");
                break;
            case R.id.bottom_sheet_category_section72 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section72",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("렌즈 세척액");
                break;
            case R.id.bottom_sheet_category_section73 :
                Toast.makeText(getActivity(), "bottom_sheet_category_section73",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                mCategoryButton.setText("콘돔");
                break;

            case R.id.custom_search_save_log_button :
                break;
            case R.id.custom_search_submit_button :

//                Toast.makeText(getActivity(), "Category : " + mCategoryButton.getText().toString(), Toast.LENGTH_SHORT).show();

                for (Word word : mInchemicalResult) {
                    if (mChemicalIncludeAutoCompleteTextView.getText().toString().equals(word.getNameKO())) {
                        mIncludeChemicalId = word.getProductId();
                    }
                    break;
                }
                Log.i(TAG, "mInchemicalId : " + mIncludeChemicalId);

                for (Word word : mExchemicalResult) {
                    if (mChemicalExcludeAutoCompleteTextView.getText().toString().equals(word.getNameKO())) {
                        mExcludeChemicalId = word.getProductId();
                    }
                    break;
                }
                Log.i(TAG, "mExchemicalId : " + mExcludeChemicalId);

                Log.i(TAG, "category id : " + mCategoryButton.getText().toString());

                if (!mCategoryButton.getText().toString().equals(getString(R.string.select_category_info))) {
                    Log.i(TAG, "category id : " + mCustomSearchStorage.getCategoryId(mCategoryButton.getText().toString()));
                    mCategoryId = mCustomSearchStorage.getCategoryId(mCategoryButton.getText().toString());
                }


                if (mCategoryId!=-1) {
                    if (mChemicalIncludeAutoCompleteTextView.getText().length()==0) {
                        mIncludeChemicalId = 0;
                    }
                    if (mChemicalExcludeAutoCompleteTextView.getText().length()==0) {
                        mExcludeChemicalId = 0;
                    }


                    requestCustomSearchProductJsonObject(mCategoryId, mIncludeChemicalId, mExcludeChemicalId);
                } else {
                    Toast.makeText(getActivity(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }

//                if (mIncludeChemicalId!=0 && mExcludeChemicalId==0 && mCategoryId!=-1) {
//                    requestCustomSearchProductJsonObject(mCategoryId, mIncludeChemicalId, mExcludeChemicalId);
//                } else if (mIncludeChemicalId==0 && mExcludeChemicalId!=0 && mCategoryId!=-1) {
//                    requestCustomSearchProductJsonObject(mCategoryId, mIncludeChemicalId, mExcludeChemicalId);
//                } else if (mIncludeChemicalId!=0 && mExcludeChemicalId!=0 && mCategoryId!=-1) {
//                    requestCustomSearchProductJsonObject(mCategoryId, mIncludeChemicalId, mExcludeChemicalId);
//                } else {
//                    Toast.makeText(getActivity(), "입력 사항을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                }
                break;
        }
    }

    private void requestIncludeChemicalWordsJsonObject(final View view, String query) {

        String utf8Query = null;
        try {
            utf8Query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "UnsupportedEncodingException : " + e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET,
                URL_HOST + NetworkConfig.Chemical.PATH + KEYWORD_PATH + SEARCH_LETTER_QUERY + utf8Query,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        mInchemicalResult = Parser.parseChemicalKeyword(response);
                        ArrayList<String> inchemicalResultStrings = new ArrayList<>();
                        for (Word word : mInchemicalResult) {
                            inchemicalResultStrings.add(word.getNameKO());
                        }

                        mChemicalIncludeAutoCompleteTextView = (AutoCompleteTextView)
                                view.findViewById(R.id.chemical_include_auto_complete_text_view);

                        mChemicalIncludeResultAdapter = new ArrayAdapter<>(getActivity(),
                                R.layout.list_item_searched_wordpart, inchemicalResultStrings);
                        mChemicalIncludeAutoCompleteTextView.setThreshold(1);
                        mChemicalIncludeAutoCompleteTextView.setAdapter(mChemicalIncludeResultAdapter);
                        mChemicalIncludeResultAdapter.notifyDataSetChanged();

                        mInputMethodManager.hideSoftInputFromWindow(mChemicalIncludeAutoCompleteTextView.getWindowToken(), 0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        Toast.makeText(getActivity(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

    private void requestExcludeChemicalWordsJsonObject(final View view, String query) {

        String utf8Query = null;
        try {
            utf8Query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "UnsupportedEncodingException : " + e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET,
                URL_HOST + NetworkConfig.Chemical.PATH + KEYWORD_PATH + SEARCH_LETTER_QUERY + utf8Query,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        mExchemicalResult = Parser.parseChemicalKeyword(response);
                        ArrayList<String> exchemicalResultStrings = new ArrayList<>();
                        for (Word word : mExchemicalResult) {
                            exchemicalResultStrings.add(word.getNameKO());
                        }

                        mChemicalExcludeAutoCompleteTextView = (AutoCompleteTextView)
                                view.findViewById(R.id.chemical_exclude_auto_complete_text_view);

                        mChemicalExcludeResultAdapter = new ArrayAdapter<>(getActivity(),
                                R.layout.list_item_searched_wordpart, exchemicalResultStrings);
                        mChemicalExcludeAutoCompleteTextView.setThreshold(1);
                        mChemicalExcludeAutoCompleteTextView.setAdapter(mChemicalExcludeResultAdapter);
                        mChemicalExcludeResultAdapter.notifyDataSetChanged();

                        mInputMethodManager.hideSoftInputFromWindow(mChemicalExcludeAutoCompleteTextView.getWindowToken(), 0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        Toast.makeText(getActivity(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

    private void requestCustomSearchProductJsonObject(int categoryId, int includeChemicalId, int excludeChemicalId) {

        StringBuilder urlString = new StringBuilder();
        urlString.append(URL_HOST + SEARCH_TARGET_PRODUCT + SEARCH_FILTER_PATH + SEARCH_CATEGORY_QUERY + categoryId);

        if (includeChemicalId!=0) {
            urlString.append(SEARCH_INCHEMICAL_QUERY + includeChemicalId);
        }
        if (excludeChemicalId!=0) {
            urlString.append(SEARCH_EXCHEMICAL_QUERY + excludeChemicalId);
        }
        Log.d(TAG, "URL : " + urlString.toString());
        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, urlString.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        Log.d(TAG, response.toString());

                        ArrayList<Product> products = Parser.parseCustomSearchProductList(response);
                        Log.i(TAG, "products.size() " + products.size());
                        ArrayList<Integer> productIds = new ArrayList<>();
                        if (products.size() > 1) {
                            for (int i = 0; i < products.size(); i++) {
                                productIds.add(products.get(i).getProductId());
                            }
                            Intent intent = ProductListActivity.newIntent(getActivity().getApplication(), productIds);
                            startActivity(intent);
                            Log.i(TAG, "productIds.size() " + productIds.size());
                        } else if (products.size() == 1) {
                            long productId = Long.parseLong(String.valueOf(products.get(0).getProductId()));
                            Intent intent = ProductListActivity.newIntent(getActivity().getApplication(), productId);
                            startActivity(intent);
                        } else {
                            startActivity(ProductListActivity.newIntent(getContext(), 63));
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Log.w(TAG, "onErrorResponse : " + error.toString());
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
