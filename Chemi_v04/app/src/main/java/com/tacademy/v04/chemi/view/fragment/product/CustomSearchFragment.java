package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tacademy.v04.chemi.R;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class CustomSearchFragment extends Fragment implements View.OnClickListener {

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


    public CustomSearchFragment() {
    }

    public static CustomSearchFragment newInstance() {

        CustomSearchFragment fragment = new CustomSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutInflater = LayoutInflater.from(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_custom_search, container, false);
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
                mChemicalBottomSheetDialog.show();
                break;
            case R.id.custom_search_save_log_button :
                break;
            case R.id.custom_search_submit_button :
                break;
        }
    }
}
