package com.tacademy.v01.chemi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.v01.chemi.R;
import com.tacademy.v01.chemi.model.Chemical;
import com.tacademy.v01.chemi.model.ChemicalStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 9..
 */
public class ChemicalListFragment extends Fragment {

    private RecyclerView mChemicalRecyclerView;
    private ChemicalListAdapter mChemicalListAdapter;
    private ArrayList<Chemical> mChemicals;

    public ChemicalListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChemicals = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product_chemical_list, container, false);
        mChemicalRecyclerView = (RecyclerView) view.findViewById(R.id.product_detail_chemical_recycler_view);
        mChemicalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChemicalStorage chemicalStorage = ChemicalStorage.get(getContext());
        ArrayList<Chemical> chemicals = chemicalStorage.getChemicals();
        mChemicals = chemicals;
        updateUI();
    }

    private void updateUI() {
        mChemicalListAdapter = new ChemicalListAdapter(mChemicals);
        mChemicalRecyclerView.setAdapter(mChemicalListAdapter);
    }

    private class ChemicalListAdapter extends RecyclerView.Adapter<ChemicalHolder> {

        private ArrayList<Chemical> mChemicals;

        public ChemicalListAdapter(ArrayList<Chemical> chemicals) {
            mChemicals = chemicals;
        }

        @Override
        public ChemicalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_chemical, parent, false);
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

    private class ChemicalHolder extends RecyclerView.ViewHolder {

        private Chemical mChemical;

        public ChemicalHolder(View itemView) {
            super(itemView);
        }

        public void bindChemical(Chemical chemical) {
            mChemical = chemical;

        }
    }
}
