package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.CustomSearch;
import com.tacademy.v04.chemi.model.CustomSearchStorage;
import com.tacademy.v04.chemi.model.User;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 20..
 * flows from Navigation Drawer menu Button
 * Search Log Custom Search, latest Search Log
 */

public class SearchLogFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";

    private RecyclerView mSearchLogRecyclerView;
    private SearchLogAdapter mSearchLogAdapter;
    private ArrayList<CustomSearch> mCustomSearches;

    private User mUser;
    private int mUserId;

    public static SearchLogFragment newInstance() {

        SearchLogFragment fragment = new SearchLogFragment();
        return fragment;
    }

    public static SearchLogFragment newInstance(int userId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);

        SearchLogFragment fragment = new SearchLogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchLogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (mUserId != 0) {
            mUserId = getArguments().getInt(ARG_USER_ID);
        }

        CustomSearchStorage customSearchStorage = CustomSearchStorage.get(getActivity());
        mCustomSearches = customSearchStorage.getCustomSearches();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_log, container, false);

        mSearchLogRecyclerView = (RecyclerView) view.findViewById(R.id.search_log_recycler_view);
        mSearchLogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mSearchLogAdapter == null) {
            mSearchLogAdapter = new SearchLogAdapter(mCustomSearches);
            mSearchLogRecyclerView.setAdapter(mSearchLogAdapter);
        } else {
            mSearchLogAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }

    private class SearchLogAdapter extends RecyclerView.Adapter<SearchLogHolder> {

        private ArrayList<CustomSearch> mCustomSearches;

        public SearchLogAdapter(ArrayList<CustomSearch> customSearches) {
            mCustomSearches = customSearches;
        }

        @Override
        public SearchLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_search_log, parent, false);
            return new SearchLogHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchLogHolder holder, int position) {
            CustomSearch customSearch = mCustomSearches.get(position);
            holder.bindCustomSearch(customSearch);
        }

        @Override
        public int getItemCount() {
            return mCustomSearches.size();
        }
    }

    private class SearchLogHolder extends RecyclerView.ViewHolder {

        private CustomSearch mCustomSearch;

        public SearchLogHolder(View itemView) {
            super(itemView);
        }

        public void bindCustomSearch(CustomSearch customSearch) {
            mCustomSearch = customSearch;
        }
    }
}
