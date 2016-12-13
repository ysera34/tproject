package com.tacademy.v04.chemi.view.fragment.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.NetworkConfig;
import com.tacademy.v04.chemi.common.network.NetworkConfig.User;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductArchiveStorage;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.MainActivity;
import com.tacademy.v04.chemi.view.activity.product.ProductActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.IMAGE_URL_HOST;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.SOCKET_TIMEOUT_GET_REQ;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 * ChildFragment of ArchiveFragment
 */

public class ArchiveProductListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ArchiveProductListFragment.class.getSimpleName();
    private static final String ARCHIVE_PRODUCT = "ArchiveProduct";

    private RecyclerView mArchiveProductRecyclerView;
    private ArchiveProductAdapter mArchiveProductAdapter;
    private ProductArchiveStorage mProductArchiveStorage;
    private ArrayList<Product> mArchiveProducts;
    private ArrayList<Product> mCheckedArchiveProducts;

    private boolean mActionModeActive = false;

    public ArchiveProductListFragment() {
    }

    public static ArchiveProductListFragment newInstance() {

        ArchiveProductListFragment fragment = new ArchiveProductListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mProductArchiveStorage = ProductArchiveStorage.get(getActivity());
        mArchiveProducts = mProductArchiveStorage.getArchiveProducts();
        mCheckedArchiveProducts = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_archive_product_list, container, false);

        mArchiveProductRecyclerView = (RecyclerView) view.findViewById(R.id.archive_product_recycler_view);
        mArchiveProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestGetProductFavorite();
    }

    private void setupAdapter() {

        if (mArchiveProductAdapter == null) {
            mArchiveProductAdapter = new ArchiveProductAdapter(mArchiveProducts);
            mArchiveProductRecyclerView.setAdapter(mArchiveProductAdapter);
        } else {
            mArchiveProducts = mProductArchiveStorage.getArchiveProducts();
            mArchiveProductAdapter.addItems(mArchiveProducts);
            mArchiveProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_home).setVisible(false);
        getActivity().getMenuInflater().inflate(R.menu.menu_archive_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getActivity(), "Action Mode Button", Toast.LENGTH_SHORT).show();
            if (!mActionModeActive) {
                getActivity().startActionMode(mActionModeCallback);
                mActionModeActive = true;
            }
            return true;
        } else if (id == R.id.action_home) {
            startActivity(MainActivity.newIntent(getActivity()));
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        int statusBarColor;

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.menu_archive_action_mode, menu);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = getActivity().getWindow().getStatusBarColor();
                getActivity().getWindow().setStatusBarColor(0xFF6c9805);
            }
            mArchiveProductAdapter.notifyItemRangeChanged(0, mArchiveProducts.size());
            mArchiveProductAdapter.notifyDataSetChanged();
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
//                    for (Product product : mCheckedArchiveProducts) {
//                        if (product.isArchiveSelect()) {
//                            Log.w("action_delete", product.getName());
//                        }
//                    }

                    mProductArchiveStorage.removeArchiveProducts(mCheckedArchiveProducts);
                    for (Product product : mCheckedArchiveProducts) {
                        requestPutProductFavorite(product);
                    }

                    Toast.makeText(getActivity(), "선택하신 항목이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(statusBarColor);
            }
            mActionModeActive = false;
            mArchiveProductAdapter.notifyItemRangeChanged(0, mArchiveProducts.size());
            mArchiveProductAdapter.notifyDataSetChanged();
        }
    };

    private class ArchiveProductAdapter extends RecyclerView.Adapter<ArchiveProductHolder> {

        ArrayList<Product> mArchiveProducts;

        public ArchiveProductAdapter(ArrayList<Product> archiveProducts) {
            mArchiveProducts = archiveProducts;
        }

        public void addItems(ArrayList<Product> archiveProducts) {
            mArchiveProducts = archiveProducts;
        }

        @Override
        public ArchiveProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_archive_product, parent, false);
            return new ArchiveProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ArchiveProductHolder holder, int position) {
            Product product = mArchiveProducts.get(position);
            holder.setIsRecyclable(false);
            holder.bindArchiveProduct(product);
        }

        @Override
        public int getItemCount() {
            return mArchiveProducts.size();
        }
    }

    private class ArchiveProductHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private Product mProduct;

        private CardView mArchiveProductCardView;
        private View mArchiveProductSelectImageViewLayout;
        private ImageView mArchiveProductSelectImageView;
        private ImageView mArchiveProductImageView;
        private TextView mArchiveProductNameTextView;
        private RatingBar mArchiveProductRatingBar;
        private TextView mArchiveProductDueDateTextView;

        public ArchiveProductHolder(View itemView) {
            super(itemView);

            mArchiveProductCardView = (CardView)
                    itemView.findViewById(R.id.list_item_archive_product_card_view);
            mArchiveProductCardView.setOnClickListener(this);
            mArchiveProductSelectImageViewLayout =
                    itemView.findViewById(R.id.list_item_archive_product_select_layout);
            mArchiveProductSelectImageView = (ImageView)
                    itemView.findViewById(R.id.list_item_archive_product_select_image_view);
            mArchiveProductImageView = (ImageView)
                    itemView.findViewById(R.id.list_item_archive_product_image_view);
            mArchiveProductNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_archive_product_name_text_view);
            mArchiveProductRatingBar = (RatingBar)
                    itemView.findViewById(R.id.list_item_archive_product_ratingBar);
            mArchiveProductDueDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_due_date_text_view);

            if (mActionModeActive) {
                mArchiveProductCardView.setOnClickListener(this);
                mArchiveProductSelectImageViewLayout.setVisibility(View.VISIBLE);
            }
        }

        public void bindArchiveProduct(Product product) {
            mProduct = product;
            Glide.with(getActivity())
                    .load(IMAGE_URL_HOST + product.getImagePath())
//                .placeholder(R.drawable.unloaded_image_holder)
                    .error(R.drawable.unloaded_image_holder)
                    .crossFade()
                    .override(330, 220)
                    .centerCrop()
                    .into(mArchiveProductImageView);

            mArchiveProductNameTextView.setText(mProduct.getName());
            mArchiveProductNameTextView.setSelected(true);

            mArchiveProductRatingBar.setRating(mProduct.getRatingAvg());

            if (mActionModeActive && mProduct.isArchiveSelect()) {
                mArchiveProductSelectImageView.setImageResource(R.drawable.ic_circle_orange_small);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_item_archive_product_card_view :
                    if (!mActionModeActive) {
                        Toast.makeText(getActivity(), "no action mode click listener", Toast.LENGTH_SHORT).show();
                        requestProductJsonObject(mProduct);
                    }
                    if (mActionModeActive && !mProduct.isArchiveSelect()) {
                        mArchiveProductSelectImageView.setImageResource(R.drawable.ic_circle_orange_small);
                        mProduct.setArchiveSelect(true);
                        mCheckedArchiveProducts.add(mProduct);
                    } else if (mActionModeActive && mProduct.isArchiveSelect()) {
                        mArchiveProductSelectImageView.setImageResource(R.drawable.ic_circle_gray_small);
                        mProduct.setArchiveSelect(false);
                        mCheckedArchiveProducts.remove(mProduct);
                    }

                    break;
                case R.id.list_item_archive_product_select_image_view :

                    break;
            }
        }
    }

    private void requestGetProductFavorite() {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET,
                URL_HOST + User.PATH + File.separator + "3" + NetworkConfig.Product.PATH,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        Log.i(TAG, "onResponse : " + response.toString());
                        mProductArchiveStorage.setArchiveProducts(Parser.parseArchiveProductList(response));
                        setupAdapter();
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

    private void requestPutProductFavorite(Product product) {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        Map<String, String> params = new HashMap<>();
        params.put("userid", "3");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                URL_HOST + NetworkConfig.Product.PATH + File.separator + product.getProductId() + User.PATH,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        Log.i(TAG, "onResponse : " + response.toString());
                        Toast.makeText(getActivity(), "보관함에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
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
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("userid", "3");
//                        return params;
//        //                return super.getParams();
//                }
//
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("content-type","application/json");
//                        return params;
////                        return super.getHeaders();
//                    }
//                };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_GET_REQ,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    private void requestProductJsonObject(final Product product) {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_HOST + PATH
                + File.separator + product.getProductId(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        // have to save storage product
//                        mProductStorage.getProduct(mProductId);
                        Log.i(TAG,  product.toStringId());
                        ProductStorage.get(getActivity()).setProduct(Parser.parseProduct(response, product));

                        Intent intent = ProductActivity.newIntent(getActivity(),
                                ProductStorage.get(getActivity()).getProduct(product.getProductId()).getId());
                        startActivity(intent);
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
