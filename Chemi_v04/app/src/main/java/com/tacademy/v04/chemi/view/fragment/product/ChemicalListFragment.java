package com.tacademy.v04.chemi.view.fragment.product;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.network.Parser;
import com.tacademy.v04.chemi.model.Chemical;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.URL_HOST;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ChemicalListFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ChemicalListFragment.class.getSimpleName();

    private static final String CHEMICAL_DETAILS = "ChemicalDetails";

    private static final String ARG_PRODUCT_ID = "product_id";

    private ProductStorage mProductStorage;
    private ArrayList<Product> mProducts;
    private Product mProduct;
    private UUID mProductId;

    private RecyclerView mChemicalRecyclerView;
    private ChemicalAdapter mChemicalAdapter;
    private ArrayList<Chemical> mChemicals;

    private View mChemicalStateExpandLayout;
    private View mChemicalDangerousGradeLayout;
    private TextView mStateExpandTextView;
    private ImageButton mStateExpandImageButton;
    private boolean mChemicalDangerousGradeLayoutState = true;

    private TextView mChemicalTotalTextView;
    private View mChemicalSortView;
    private BottomSheetDialog mChemicalSortBottomSheetDialog;
    private Button mChemicalSortDangerButton;
    private Button mChemicalSortMarkButton;

    public ChemicalListFragment() {
//        mProductStorage = ProductStorage.get(getActivity());
//        mProducts = mProductStorage.getProducts();
    }

    public static ChemicalListFragment newInstance() {

        ChemicalListFragment fragment = new ChemicalListFragment();
        return fragment;
    }

    public static ChemicalListFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, id);

        ChemicalListFragment fragment = new ChemicalListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        mProductStorage = ProductStorage.get(getActivity());
        mProduct = mProductStorage.getProduct(mProductId);

//        ChemicalStorage chemicalStorage = ChemicalStorage.get(getActivity());
//        mChemicals = chemicalStorage.getChemicals();

        Log.i(TAG, "onCreate " + mProduct.toString());

        mChemicals = mProduct.getChemicals();
        Log.i(TAG, "mChemicals.size() " + mChemicals.size());
        for (Chemical chemical : mChemicals) {
            Log.i(TAG, chemical.toStringId());
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chemical_list, container, false);

        mChemicalStateExpandLayout = view.findViewById(R.id.list_chemical_state_expand_layout);
        mChemicalStateExpandLayout.setOnClickListener(this);
        mChemicalDangerousGradeLayout = view.findViewById(R.id.chemical_dangerous_grade_layout);
        mStateExpandTextView = (TextView) view.findViewById(R.id.list_chemical_state_expand_text);
        mStateExpandImageButton = (ImageButton) view.findViewById(R.id.list_chemical_state_expand_button);

        mChemicalTotalTextView = (TextView) view.findViewById(R.id.chemical_list_total);

        mChemicalSortView = view.findViewById(R.id.chemical_list_sort_button_view);
        mChemicalSortView.setOnClickListener(this);

        mChemicalRecyclerView = (RecyclerView) view.findViewById(R.id.product_detail_chemical_recycler_view);
        mChemicalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestProductJsonObject();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chemical_list_sort_button_view :
                mChemicalSortBottomSheetDialog = new BottomSheetDialog(getActivity());
                View mSortBottomSheetView = getLayoutInflater(getArguments())
                        .inflate(R.layout.bottom_sheet_chemical_sort, null);
                mChemicalSortBottomSheetDialog.setContentView(mSortBottomSheetView);

                mChemicalSortDangerButton = (Button) mSortBottomSheetView
                        .findViewById(R.id.bottom_sheet_chemical_filter_section1);
                mChemicalSortDangerButton.setOnClickListener(this);
                mChemicalSortMarkButton = (Button) mSortBottomSheetView
                        .findViewById(R.id.bottom_sheet_chemical_filter_section2);
                mChemicalSortMarkButton.setOnClickListener(this);
                mChemicalSortBottomSheetDialog.show();
                break;
            case R.id.bottom_sheet_chemical_filter_section1:
                Toast.makeText(getActivity(), "bottom_sheet_chemical_filter_section1",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_chemical_filter_section2:
                Toast.makeText(getActivity(), "bottom_sheet_chemical_filter_section2",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.list_chemical_state_expand_layout :
                if (mChemicalDangerousGradeLayoutState) {
                    mChemicalDangerousGradeLayout.setVisibility(View.GONE);
                    mStateExpandTextView.setText("통계보기");
                    mStateExpandImageButton.setImageResource(R.drawable.ic_keyboard_arrow_down_48dp);
                    mChemicalDangerousGradeLayoutState = false;
                } else {
                    mChemicalDangerousGradeLayout.setVisibility(View.VISIBLE);
                    mStateExpandTextView.setText("접기");
                    mStateExpandImageButton.setImageResource(R.drawable.ic_keyboard_arrow_up_48dp);
                    mChemicalDangerousGradeLayoutState = true;
                }
                break;
        }
    }

    private void setupAdapter() {
        if(isAdded()) {
            if (mChemicalAdapter == null) {
                mChemicalAdapter = new ChemicalAdapter(mChemicals);
                mChemicalRecyclerView.setAdapter(mChemicalAdapter);
            } else {
                mChemicals = mProduct.getChemicals();
                mChemicalAdapter.addItems(mChemicals);
                mChemicalAdapter.notifyDataSetChanged();
            }
        }
    }

    private void requestProductJsonObject() {

        final ProgressDialog pDialog =
                ProgressDialog.show(getActivity(), getString(R.string.request_loading_data),
                        getString(R.string.load_please_wait), false, false);

        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(URL_HOST + PATH
                + File.separator + mProduct.getProductId(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        mProductStorage.setProduct(Parser.parseProduct(response, mProduct));

                        mProduct = mProductStorage.getProduct(mProductId);
                        setupAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w(TAG, "onErrorResponse : " + error.toString());
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

    private class ChemicalAdapter extends RecyclerView.Adapter<ChemicalHolder> {

        private ArrayList<Chemical> mChemicals;

        public ChemicalAdapter(ArrayList<Chemical> chemicals) {
            mChemicals = chemicals;
        }

        public void addItems(ArrayList<Chemical> chemicals) {
            mChemicals = chemicals;
        }

        @Override
        public ChemicalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_chemical, parent, false);
            return new ChemicalHolder(view);
        }

        @Override
        public void onBindViewHolder(ChemicalHolder holder, int position) {
            Chemical chemical = mChemicals.get(position);
            holder.bindChemical(chemical);
        }

        @Override
        public int getItemCount() {
            return mChemicals.size();
        }
    }

    private class ChemicalHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Chemical mChemical;
        private TextView mChemicalTitleKoTextView;

        public ChemicalHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mChemicalTitleKoTextView = (TextView) itemView.findViewById(R.id.list_item_chemical_title_ko);
            // setIsRecyclable(false);
        }

        public void bindChemical(Chemical chemical) {
            mChemical = chemical;
            mChemicalTitleKoTextView.setText(mChemical.getNameKo());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mChemical.getNameKo(), Toast.LENGTH_SHORT).show();
            // view.setBackgroundColor(getResources().getColor(R.color.chemical_card_view_clicked_color));

            FragmentManager manager = getFragmentManager();
            ChemicalDialogFragment dialogFragment =
                    ChemicalDialogFragment.newInstance(mChemical.getId());
            dialogFragment.show(manager, CHEMICAL_DETAILS);
        }
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause mChemicals.size() " + mChemicals.size());
//        for (Chemical chemical : mChemicals) {
//            Log.i(TAG, chemical.toStringId());
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop mChemicals.size() " + mChemicals.size());
//        for (Chemical chemical : mChemicals) {
//            Log.i(TAG, chemical.toStringId());
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i(TAG, "onDestroyView mChemicals.size() " + mChemicals.size());
//        for (Chemical chemical : mChemicals) {
//            Log.i(TAG, chemical.toStringId());
//        }
//    }
}
