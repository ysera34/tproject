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
import com.tacademy.v04.chemi.common.OnPassDataListener;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.fragment.product.SearchFragment;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class SearchActivity extends AppBaseActivity
        implements View.OnClickListener, OnPassDataListener {

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
//        mCategoryBottomSheetDialog = new BottomSheetDialog(SearchActivity.this);
//        mBottomSheetView = getLayoutInflater()
//                .inflate(R.layout.bottom_sheet_category, null);
//        mCategoryBottomSheetDialog.setContentView(mBottomSheetView);
//        (mBottomSheetView.findViewById(R.id.bottom_sheet_button)).setOnClickListener(this);

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
                mCategoryBottomSheetDialog = new BottomSheetDialog(SearchActivity.this);
                View mBottomSheetView = getLayoutInflater()
                        .inflate(R.layout.bottom_sheet_category, null);
                mCategoryBottomSheetDialog.setContentView(mBottomSheetView);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_button)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section11)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section12)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section13)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section21)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section22)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section23)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section31)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section32)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section33)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section41)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section42)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section43)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section51)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section52)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section53)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section61)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section62)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section63)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section71)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section72)).setOnClickListener(this);
                (mBottomSheetView.findViewById(R.id.bottom_sheet_category_section73)).setOnClickListener(this);
                mCategoryBottomSheetDialog.show();
                break;
            case R.id.bottom_sheet_button :
                Toast.makeText(SearchActivity.this, "bottom sheet button test",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_category_section11 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section11",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 11));
                break;
            case R.id.bottom_sheet_category_section12 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section12",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 12));
                break;
            case R.id.bottom_sheet_category_section13 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section13",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 13));
                break;
            case R.id.bottom_sheet_category_section21 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section21",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 21));
                break;
            case R.id.bottom_sheet_category_section22 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section22",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 22));
                break;
            case R.id.bottom_sheet_category_section23 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section23",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 23));
                break;
            case R.id.bottom_sheet_category_section31 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section31",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 31));
                break;
            case R.id.bottom_sheet_category_section32 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section32",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 32));
                break;
            case R.id.bottom_sheet_category_section33 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section33",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 33));
                break;
            case R.id.bottom_sheet_category_section41 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section41",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 41));
                break;
            case R.id.bottom_sheet_category_section42 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section42",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 42));
                break;
            case R.id.bottom_sheet_category_section43 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section43",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 43));
                break;
            case R.id.bottom_sheet_category_section51 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section51",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 51));
                break;
            case R.id.bottom_sheet_category_section52 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section52",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 52));
                break;
            case R.id.bottom_sheet_category_section53 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section53",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 53));
                break;
            case R.id.bottom_sheet_category_section61 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section61",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 61));
                break;
            case R.id.bottom_sheet_category_section62 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section62",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 62));
                break;
            case R.id.bottom_sheet_category_section63 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section63",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 63));
                break;
            case R.id.bottom_sheet_category_section71 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section71",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 71));
                break;
            case R.id.bottom_sheet_category_section72 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section72",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 72));
                break;
            case R.id.bottom_sheet_category_section73 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section73",
                        Toast.LENGTH_SHORT).show();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 73));
                break;
        }
    }

    @Override
    public void onStringDataPass(String data) {
        mSearchProductEditText.setText(data);
    }
}
