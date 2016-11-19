package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.OnPassDataListener;
import com.tacademy.v04.chemi.common.SeparatorDecoration;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class SearchActivity extends AppBaseActivity
        implements View.OnClickListener, OnPassDataListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private EditText mSearchProductEditText;
    private BottomSheetDialog mCategoryBottomSheetDialog;

    private RecyclerView mSearchedResultRecyclerView;
    private SearchedResultAdapter mResultAdapter;
    private ArrayList<Product> mProducts;
    private ProductStorage mProductStorage;
    private String queryString;

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
//                Log.d(TAG, charSequence.toString());
//                mProducts = mProductStorage.getSearchProducts(charSequence.toString());
//                updateUI(mProducts);
                queryString = charSequence.toString();
                new SearchTask().execute(queryString);
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

        // search edit_text auto completed
        mSearchedResultRecyclerView = (RecyclerView) findViewById(R.id.searched_result_recycler_view);
        mSearchedResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SeparatorDecoration decoration =
                new SeparatorDecoration(getApplicationContext(), android.R.color.transparent, 1.5f);
        mSearchedResultRecyclerView.addItemDecoration(decoration);
        mProducts = new ArrayList<>();
        mProductStorage = ProductStorage.get(getApplicationContext());
//        mProducts = mProductStorage.getSearchProducts("query");
//        mResultAdapter = new SearchedResultAdapter(mProducts);
//        mSearchedResultRecyclerView.setAdapter(mResultAdapter);

        // search fragment
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment mSearchFragmentContainer = fm.findFragmentById(R.id.fragment_search_container);
//
//        if (mSearchFragmentContainer == null) {
//            mSearchFragmentContainer = SearchFragment.newInstance();
//            fm.beginTransaction()
//                    .add(R.id.fragment_search_container, mSearchFragmentContainer)
//                    .commit();
//        }
    }

    private void updateUI(ArrayList<Product> products) {

        if (mResultAdapter == null) {
            mResultAdapter = new SearchedResultAdapter(products);
            mSearchedResultRecyclerView.setAdapter(mResultAdapter);
        } else {
            mResultAdapter.notifyDataSetChanged();
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

    private class SearchedResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Product> mProducts;

        public SearchedResultAdapter(ArrayList<Product> products) {
            mProducts = products;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view;
            if (viewType == NO_RESULT_VIEW) {
                view = layoutInflater.inflate(R.layout.view_no_result_search_framelayout, parent, false);
                return new NoResultHolder(view);
            }
            view = layoutInflater.inflate(R.layout.list_item_searched_result, parent, false);
            return new ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ResultHolder) {
                Product product = mProducts.get(position);
                ((ResultHolder) holder).bindProduct(product);
            }
        }

        @Override
        public int getItemCount() {
            return mProducts.size() > 0 ? mProducts.size() : 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (mProducts.size() == 0) {
                return NO_RESULT_VIEW;
            }
            return super.getItemViewType(position);
        }
    }

    private static final int NO_RESULT_VIEW = -1;

    private class NoResultHolder extends RecyclerView.ViewHolder {

        public NoResultHolder(View itemView) {
            super(itemView);

//            FragmentManager fm = getSupportFragmentManager();
//            Fragment mSearchFragmentContainer = fm.findFragmentById(R.id.fragment_search_container);
//
//            if (mSearchFragmentContainer == null) {
//                mSearchFragmentContainer = SearchFragment.newInstance();
//                fm.beginTransaction()
//                        .add(R.id.fragment_search_container, mSearchFragmentContainer)
//                        .commit();
//            }
        }
    }

    private class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Product mProduct;
        private TextView mEquivalentTextView;
        private TextView mRestTextView;

        public ResultHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mEquivalentTextView = (TextView) itemView.findViewById(R.id.list_item_searched_equivalent_word);
            mRestTextView = (TextView) itemView.findViewById(R.id.list_item_searched_rest_word);
        }

        public void bindProduct(Product product) {
            mProduct = product;
            String equivalentStr = queryString;
            String restStr = mProduct.getName().substring(queryString.length());

            mEquivalentTextView.setText(equivalentStr);
            mRestTextView.setText(restStr);

        }

        @Override
        public void onClick(View view) {

        }
    }

    private class SearchTask extends AsyncTask<String, ArrayList<Product>, ArrayList<Product>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProducts.clear();
        }

        @Override
        protected ArrayList<Product> doInBackground(String... strings) {
            return mProductStorage.getSearchProducts(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {
            super.onPostExecute(products);
            if (products != null) {
                mResultAdapter = new SearchedResultAdapter(products);
                mSearchedResultRecyclerView.setAdapter(mResultAdapter);
            }
        }
    }
}
