package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Chemical;
import com.tacademy.v04.chemi.model.ChemicalStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ChemicalListFragment extends Fragment implements View.OnClickListener {

    private static final String CHEMICAL_DETAILS = "ChemicalDetails";

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
    }

    public static ChemicalListFragment newInstance() {

        ChemicalListFragment fragment = new ChemicalListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChemicalStorage chemicalStorage = ChemicalStorage.get(getActivity());
        mChemicals = chemicalStorage.getChemicals();
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

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mChemicalAdapter == null) {
            mChemicalAdapter = new ChemicalAdapter(mChemicals);
            mChemicalRecyclerView.setAdapter(mChemicalAdapter);
        } else {
            mChemicalAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private class ChemicalAdapter extends RecyclerView.Adapter<ChemicalHolder> {

        private ArrayList<Chemical> mChemicals;

        public ChemicalAdapter(ArrayList<Chemical> chemicals) {
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
}
