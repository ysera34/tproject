package com.tacademy.v04.chemi.view.activity.product;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.util.listener.OnPassDataListener;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.model.SearchLatestStorage;
import com.tacademy.v04.chemi.model.Word;
import com.tacademy.v04.chemi.view.activity.AppBaseActivity;
import com.tacademy.v04.chemi.view.fragment.product.SearchFragment;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.android.volley.Request.Method.GET;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.SOCKET_TIMEOUT_GET_REQ;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_KEYWORD_QUERY;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_TARGET_PRODUCT;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.SEARCH_WORDPART_QUERY;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;
import static com.tacademy.v04.chemi.common.network.Parser.parseSearchKeywordProductList;
import static com.tacademy.v04.chemi.common.network.Parser.parseStringSearchWordPartList;

/**
 * Created by yoon on 2016. 11. 15..
 * should update fragment transaction
 */

public class SearchActivity extends AppBaseActivity
        implements View.OnClickListener, OnPassDataListener {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final String WORDPART_TAG = "wordpart";
    private static final String KEYWORD_TAG = "keyword";

    private Toolbar mToolbar;
    private EditText mSearchProductEditText;
    private AutoCompleteTextView mSearchProductAutoCompleteTextView;
    private RelativeLayout mSearchProductSearchButtonLayout;
    private ArrayList<String> mSearchResults;
    private ArrayAdapter<String> mSearchResultsAdapter;
    private BottomSheetDialog mCategoryBottomSheetDialog;

    private RecyclerView mSearchedResultRecyclerView;
    private SearchedResultAdapter mResultAdapter;
    private ArrayList<Word> mWords;
    private ArrayList<Product> mProducts;
    private ProductStorage mProductStorage;
    private String queryString;

    private SearchLatestStorage searchLatestStorage;

    private Fragment mSearchFragmentContainer;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchLatestStorage = SearchLatestStorage.get(getApplicationContext());

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
                Toast.makeText(getApplicationContext(), charSequence.toString(), Toast.LENGTH_SHORT).show();

//                mProducts = mProductStorage.getSearchProducts(charSequence.toString());
//                updateUI(mProducts);
//                queryString = charSequence.toString();
//                new SearchTask().execute(queryString);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchResults = new ArrayList<>();
        mSearchProductAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(R.id.search_product_auto_complete_text_view);
        mSearchProductAutoCompleteTextView.setThreshold(1);
        mSearchProductAutoCompleteTextView.setSelection(mSearchProductAutoCompleteTextView.getText().length());
        mSearchProductAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Log.d(TAG, charSequence.toString());
//                Toast.makeText(getApplicationContext(), charSequence.length() + "", Toast.LENGTH_SHORT).show();
                requestWordsJsonObject(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchProductAutoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH :
//                        return true;
//                    case keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER :
                        Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                        requestKeywordJsonObject(textView.getText().toString());
                        searchLatestStorage.setSearchLatestWordsPreference(textView.getText().toString(),
                                searchLatestStorage.getSearchLatestWordsPreferenceIndex());
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "기본", Toast.LENGTH_LONG).show();
                        return false;
                }
            }
        });

//        mSearchProductSearchButtonLayout = (RelativeLayout) findViewById(R.id.search_product_search_button_layout);
//        mSearchProductSearchButtonLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), mSearchProductAutoCompleteTextView.getText().toString(),
//                        Toast.LENGTH_SHORT).show();
//                requestKeywordJsonObject(mSearchProductAutoCompleteTextView.getText().toString());
//                searchLatestStorage.setSearchLatestWordsPreference(mSearchProductAutoCompleteTextView.getText().toString(),
//                        searchLatestStorage.getSearchLatestWordsPreferenceIndex());
//            }
//        });

//        mSearchProductSearchButtonLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//                    Toast.makeText(getApplicationContext(), mSearchProductAutoCompleteTextView.getText().toString(),
//                            Toast.LENGTH_SHORT).show();
//                    requestKeywordJsonObject(mSearchProductAutoCompleteTextView.getText().toString());
//                    searchLatestStorage.setSearchLatestWordsPreference(mSearchProductAutoCompleteTextView.getText().toString(),
//                            searchLatestStorage.getSearchLatestWordsPreferenceIndex());
//                    return true;
//                }
//                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    return false;
//                }
//                return false;
//            }
//        });

        // category_fab
        (findViewById(R.id.category_fab)).setOnClickListener(this);

        // bottom_sheet
//        mCategoryBottomSheetDialog = new BottomSheetDialog(SearchActivity.this);
//        mBottomSheetView = getLayoutInflater()
//                .inflate(R.layout.bottom_sheet_category, null);
//        mCategoryBottomSheetDialog.setContentView(mBottomSheetView);
//        (mBottomSheetView.findViewById(R.id.bottom_sheet_button)).setOnClickListener(this);

        // search edit_text auto completed
//        mSearchedResultRecyclerView = (RecyclerView) findViewById(R.id.searched_result_recycler_view);
//        mSearchedResultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        SeparatorDecoration decoration =
//                new SeparatorDecoration(getApplicationContext(), android.R.color.transparent, 1.5f);
//        mSearchedResultRecyclerView.addItemDecoration(decoration);
        mProducts = new ArrayList<>();
        mProductStorage = ProductStorage.get(getApplicationContext());
//        mProducts = mProductStorage.getSearchProducts("query");
//        mResultAdapter = new SearchedResultAdapter(mProducts);
//        mSearchedResultRecyclerView.setAdapter(mResultAdapter);

        // search fragment
        FragmentManager fm = getSupportFragmentManager();
        mSearchFragmentContainer = fm.findFragmentById(R.id.fragment_search_container);

        if (mSearchFragmentContainer == null) {
            mSearchFragmentContainer = SearchFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_search_container, mSearchFragmentContainer)
                    .commit();
        }
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
        View mBottomSheetView = null;
        switch (view.getId()) {
            case R.id.category_fab :
                mCategoryBottomSheetDialog = new BottomSheetDialog(SearchActivity.this);
                mBottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_category, null);
                mCategoryBottomSheetDialog.setContentView(mBottomSheetView);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetView.getParent());
                bottomSheetBehavior.setPeekHeight(R.dimen.custom_peek_height);
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
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 52));
                break;
            case R.id.bottom_sheet_category_section12 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section12",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 51));
                break;
            case R.id.bottom_sheet_category_section13 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section13",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 53));
                break;
            case R.id.bottom_sheet_category_section21 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section21",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 11));
                break;
            case R.id.bottom_sheet_category_section22 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section22",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 12));
                break;
            case R.id.bottom_sheet_category_section23 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section23",
                        Toast.LENGTH_SHORT).show();
//                startActivity(ProductListActivity.newIntent(getApplicationContext(), 13));
                break;
            case R.id.bottom_sheet_category_section31 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section31",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 32));
                break;
            case R.id.bottom_sheet_category_section32 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section32",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 31));
                break;
            case R.id.bottom_sheet_category_section33 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section33",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 33));
                break;
            case R.id.bottom_sheet_category_section41 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section41",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 62));
                break;
            case R.id.bottom_sheet_category_section42 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section42",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 61));
                break;
            case R.id.bottom_sheet_category_section43 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section43",
                        Toast.LENGTH_SHORT).show();
//                startActivity(ProductListActivity.newIntent(getApplicationContext(), 63));
                break;
            case R.id.bottom_sheet_category_section51 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section51",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 21));
                break;
            case R.id.bottom_sheet_category_section52 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section52",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 22));
                break;
            case R.id.bottom_sheet_category_section53 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section53",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext()));
//                startActivity(ProductListActivity.newIntent(getApplicationContext(), 53));
                Log.w(TAG, "onClick bottom_sheet_category_section53");
                break;
            case R.id.bottom_sheet_category_section61 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section61",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 41));
                break;
            case R.id.bottom_sheet_category_section62 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section62",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 42));
                break;
            case R.id.bottom_sheet_category_section63 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section63",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 43));
                break;
            case R.id.bottom_sheet_category_section71 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section71",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 72));
                break;
            case R.id.bottom_sheet_category_section72 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section72",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 71));
                break;
            case R.id.bottom_sheet_category_section73 :
                Toast.makeText(SearchActivity.this, "bottom_sheet_category_section73",
                        Toast.LENGTH_SHORT).show();
                mCategoryBottomSheetDialog.dismiss();
                startActivity(ProductListActivity.newIntent(getApplicationContext(), 73));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            requestKeywordJsonObject(mSearchProductAutoCompleteTextView.getText().toString());
            searchLatestStorage.setSearchLatestWordsPreference(
                    mSearchProductAutoCompleteTextView.getText().toString(),
            searchLatestStorage.getSearchLatestWordsPreferenceIndex());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
    public void onStringDataPass(String data) {
        mSearchProductEditText.setText(data);
        mSearchProductEditText.setSelection(mSearchProductEditText.getText().length());
        mSearchProductAutoCompleteTextView.setText(data);
        mSearchProductAutoCompleteTextView.setSelection(mSearchProductAutoCompleteTextView.getText().length());
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
                mSearchedResultRecyclerView.setVisibility(View.GONE);
                return new NoResultHolder(view);
            }
            view = layoutInflater.inflate(R.layout.list_item_searched_result, parent, false);

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_search_container);
            if(fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();

            mSearchedResultRecyclerView.setVisibility(View.VISIBLE);
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

            FragmentManager fm = getSupportFragmentManager();
            mSearchFragmentContainer = fm.findFragmentById(R.id.fragment_search_container);

            if (mSearchFragmentContainer == null) {
                mSearchFragmentContainer = SearchFragment.newInstance();
                fm.beginTransaction()
                        .remove(mSearchFragmentContainer)
                        .add(R.id.fragment_search_container, mSearchFragmentContainer)
                        .addToBackStack(null)
                        .commit();
            }
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
                Log.w(TAG, String.valueOf(products.size()));
                int productSize = products.size();
                if (productSize > 0) {
                    mResultAdapter = new SearchedResultAdapter(products);
                    mSearchedResultRecyclerView.setAdapter(mResultAdapter);
                } else {
                    FragmentManager fm = getSupportFragmentManager();
                    mSearchFragmentContainer = fm.findFragmentById(R.id.fragment_search_container);

                    if (mSearchFragmentContainer != null) {
                        mSearchFragmentContainer = SearchFragment.newInstance();
                        fm.beginTransaction()
                                .remove(mSearchFragmentContainer)
                                .replace(R.id.fragment_search_container, mSearchFragmentContainer)
//                                .add(R.id.fragment_search_container, mSearchFragmentContainer)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        mSearchFragmentContainer = SearchFragment.newInstance();
                        fm.beginTransaction()
                                .remove(mSearchFragmentContainer)
                                .replace(R.id.fragment_search_container, mSearchFragmentContainer)
//                                .add(R.id.fragment_search_container, mSearchFragmentContainer)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed(){

//        updateUI():

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.beginTransaction().remove(mSearchFragmentContainer)
                    .addToBackStack(null)
                    .commit();
        } else {
            super.onBackPressed();
            mSearchFragmentContainer = SearchFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_search_container, mSearchFragmentContainer)
                    .commit();
        }
    }

    private class AutoCompleteSearchProductAdapter extends ArrayAdapter<String> {

        private ArrayList<String> mSearchedResults;

        public AutoCompleteSearchProductAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            mSearchResults = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }

    private void requestWordsJsonObject(String query) {

        String utf8Query = null;
        try {
            utf8Query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "UnsupportedEncodingException : " + e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET,
                URL_HOST + SEARCH_TARGET_PRODUCT + SEARCH_PATH + SEARCH_WORDPART_QUERY + utf8Query,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mSearchProductAutoCompleteTextView =
                                (AutoCompleteTextView) findViewById(R.id.search_product_auto_complete_text_view);
                        mSearchResultsAdapter = new ArrayAdapter<>(getApplicationContext(),
                                R.layout.list_item_searched_wordpart, parseStringSearchWordPartList(response));
                        mSearchProductAutoCompleteTextView.setAdapter(mSearchResultsAdapter);
                        mSearchResultsAdapter.notifyDataSetChanged();
//                        mSearchProductAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                String value = (String) adapterView.getItemAtPosition(i);
//
//                                requestKeywordJsonObject(value);
//                            }
//                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        Toast.makeText(getApplicationContext(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setTag(WORDPART_TAG);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    private void requestKeywordJsonObject(String query) {

        String utf8Query = null;
        try {
            utf8Query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.w(TAG, "UnsupportedEncodingException : " + e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET,
                URL_HOST + SEARCH_TARGET_PRODUCT + SEARCH_KEYWORD_QUERY + utf8Query,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        ArrayList<Product> products = parseSearchKeywordProductList(response);
                        Log.i(TAG, "products.size() " + products.size());
                        ArrayList<Integer> productIds = new ArrayList<>();
                        if (products.size() > 1) {
                            for (int i = 0; i < products.size(); i++) {
                                productIds.add(products.get(i).getProductId());
                            }
                            Intent intent = ProductListActivity.newIntent(getApplicationContext(), productIds);
                            startActivity(intent);
                            Log.i(TAG, "productIds.size() " + productIds.size());
                        } else if (products.size() == 1) {
                            long productId = Long.parseLong(String.valueOf(products.get(0).getProductId()));
                            Intent intent = ProductListActivity.newIntent(getApplicationContext(), productId);
                            startActivity(intent);
                        } else {
                            startActivity(ProductListActivity.newIntent(getApplicationContext(), 63));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                        Toast.makeText(getApplicationContext(), "데이터 수신 중, 서버에서 문제가 발생하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }
}
