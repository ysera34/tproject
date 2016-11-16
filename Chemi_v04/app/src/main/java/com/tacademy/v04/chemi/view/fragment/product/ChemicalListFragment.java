package com.tacademy.v04.chemi.view.fragment.product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Chemical;
import com.tacademy.v04.chemi.model.ChemicalStorage;

import java.util.List;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ChemicalListFragment extends Fragment {

    private static final String CHEMICAL_DETAILS = "ChemicalDetails";

    private RecyclerView mChemicalRecyclerView;
    private ChemicalAdapter mChemicalAdapter;
    private List<Chemical> mChemicals;

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

    private class ChemicalAdapter extends RecyclerView.Adapter<ChemicalHolder> {

        private List<Chemical> mChemicals;

        public ChemicalAdapter(List<Chemical> chemicals) {
            mChemicals = chemicals;
        }

        @Override
        public ChemicalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_chemical, parent, false);
            return new ChemicalHolder(view);
        }

        @Override
        public void onBindViewHolder(ChemicalHolder holder, int position) {
            Chemical chemical = mChemicals.get(position);
            holder.bindChemicals(chemical);
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
        }

        public void bindChemicals(Chemical chemical) {
            mChemical = chemical;
            mChemicalTitleKoTextView.setText(mChemical.getNameKo());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mChemical.getNameKo(),
                    Toast.LENGTH_SHORT).show();

            FragmentManager manager = getFragmentManager();
            ChemicalDialogFragment dialogFragment =
                    ChemicalDialogFragment.newInstance(mChemical.getId());
            dialogFragment.show(manager, CHEMICAL_DETAILS);
        }
    }
}
