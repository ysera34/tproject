package com.tacademy.chemi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.chemi.R;
import com.tacademy.chemi.model.Composition;
import com.tacademy.chemi.model.CompositionStorage;

import java.util.List;

/**
 * Created by yoon on 2016. 10. 19..
 */
public class CompositionListFragment extends Fragment {

    private RecyclerView mCompositionRecyclerView;
    private CompositionAdapter mCompositionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_composition_list, container, false);

        mCompositionRecyclerView = (RecyclerView) view.findViewById(R.id.composition_recycler_view);
        mCompositionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CompositionStorage compositionStorage = CompositionStorage.get(getActivity());
        List<Composition> compositions = compositionStorage.getCompositions();

        if (mCompositionAdapter == null) {
            mCompositionAdapter = new CompositionAdapter(compositions);
            mCompositionRecyclerView.setAdapter(mCompositionAdapter);
        } else {
            mCompositionAdapter.notifyDataSetChanged();
        }
    }

    private class CompositionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Composition mComposition;
        private TextView mCompositionTitleTextView;

        public CompositionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mCompositionTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_composition_title);
        }

        public void bindComposition(com.tacademy.chemi.model.Composition composition) {
            mComposition = composition;
            mCompositionTitleTextView.setText(mComposition.getTitle());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mComposition.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private class CompositionAdapter extends RecyclerView.Adapter<CompositionHolder> {

        private List<Composition> mCompositions;

        public CompositionAdapter(List<Composition> compositions) {
            mCompositions = compositions;
        }

        @Override
        public CompositionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_composition, parent, false);
            return new CompositionHolder(view);
        }

        @Override
        public void onBindViewHolder(CompositionHolder holder, int position) {
            Composition composition = mCompositions.get(position);
            holder.bindComposition(composition);
        }

        @Override
        public int getItemCount() {
            return mCompositions.size();
        }
    }
}
