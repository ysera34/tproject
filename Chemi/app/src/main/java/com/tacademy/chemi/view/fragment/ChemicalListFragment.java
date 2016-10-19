package com.tacademy.chemi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.chemi.R;

/**
 * Created by yoon on 2016. 10. 19..
 */
public class ChemicalListFragment extends Fragment {

    private RecyclerView mChemicalRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_chemical_list, container, false);

//        mChemicalRecyclerView = (RecyclerView) view.findViewById(R.id.chemical_recycler_view);
//        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
