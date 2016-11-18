package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.OnDataPass;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.fragment.product.SearchFragment;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class SearchActivity extends AppBaseActivity
        implements View.OnClickListener, OnDataPass {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private EditText mSearchProductEditText;
    private BottomSheetDialog mCategoryBottomSheetDialog;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearchProductEditText = (EditText) findViewById(R.id.search_product_edit_text);
        mSearchProductEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // category_fab
        (findViewById(R.id.category_fab)).setOnClickListener(this);

        // bottom_sheet
        mCategoryBottomSheetDialog = new BottomSheetDialog(SearchActivity.this);
        View mBottomSheetView = getLayoutInflater()
                .inflate(R.layout.bottom_sheet_category, null);
        mCategoryBottomSheetDialog.setContentView(mBottomSheetView);
        (mBottomSheetView.findViewById(R.id.bottom_sheet_button)).setOnClickListener(this);

        // search fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment mSearchFragmentContainer = fm.findFragmentById(R.id.fragment_search_container);

        if (mSearchFragmentContainer == null) {
            mSearchFragmentContainer = SearchFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_search_container, mSearchFragmentContainer)
                    .commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_fab :
                mCategoryBottomSheetDialog.show();
                break;
            case R.id.bottom_sheet_button :
                Toast.makeText(SearchActivity.this, "bottom sheet button test",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onStringDataPass(String data) {
        mSearchProductEditText.setText(data);
    }
}
