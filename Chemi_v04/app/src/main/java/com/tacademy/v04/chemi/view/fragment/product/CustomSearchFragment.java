package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class CustomSearchFragment extends Fragment
        implements View.OnClickListener {

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
            case R.id.custom_search_save_log_button :
                break;
            case R.id.custom_search_submit_button :
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
            
            
        }
    }

}
