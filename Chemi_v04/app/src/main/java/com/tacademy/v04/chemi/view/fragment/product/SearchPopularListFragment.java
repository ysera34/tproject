package com.tacademy.v04.chemi.view.fragment.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.util.listener.OnPassDataListener;
import com.tacademy.v04.chemi.common.util.decorator.SeparatorDecoration;
import com.tacademy.v04.chemi.model.Search;
import com.tacademy.v04.chemi.model.SearchPopularStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class SearchPopularListFragment extends Fragment {

    private static final String SEARCH_WORD = "SearchWord";

    private RecyclerView mSearchPopularRecyclerView;
    private PopularWordAdapter mPopularWordAdapter;
    private ArrayList<Search> mSearches;

    public SearchPopularListFragment() {
    }

    public static SearchPopularListFragment newInstance() {

        SearchPopularListFragment fragment = new SearchPopularListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchPopularStorage searchPopularStorage = SearchPopularStorage.get(getActivity());
        mSearches = searchPopularStorage.getSearches();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_popular_list, container, false);

        mSearchPopularRecyclerView = (RecyclerView) view.findViewById(R.id.product_search_popular_recycler_view);
        mSearchPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SeparatorDecoration decoration =
                new SeparatorDecoration(getActivity(), android.R.color.transparent, 1.5f);
        mSearchPopularRecyclerView.addItemDecoration(decoration);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mPopularWordAdapter == null) {
            mPopularWordAdapter = new PopularWordAdapter(mSearches);
            mSearchPopularRecyclerView.setAdapter(mPopularWordAdapter);
        } else {
            mPopularWordAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class PopularWordAdapter extends RecyclerView.Adapter<PopularWordHolder> {

        private ArrayList<Search> mSearches;

        public PopularWordAdapter(ArrayList<Search> searches) {
            mSearches = searches;
        }

        @Override
        public PopularWordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_search_popular, parent, false);
            return new PopularWordHolder(view);
        }

        @Override
        public void onBindViewHolder(PopularWordHolder holder, int position) {
            Search search = mSearches.get(position);
            holder.bindSearch(search);
        }

        @Override
        public int getItemCount() {
            return mSearches.size();
        }
    }

    private class PopularWordHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Search mSearch;

        private TextView mSearchRatingNumberTextView;
        private TextView mSearchWordTextView;
        private ImageView mSearchVariationImageView;
        private TextView mSearchVariationValueTextView;

        public PopularWordHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mSearchRatingNumberTextView = (TextView)
                    itemView.findViewById(R.id.list_item_search_popular_rating_number);
            mSearchWordTextView = (TextView)
                    itemView.findViewById(R.id.list_item_popular_search_word);
            mSearchVariationImageView = (ImageView)
                    itemView.findViewById(R.id.list_item_search_variation_state_image);
            mSearchVariationValueTextView = (TextView)
                    itemView.findViewById(R.id.list_item_search_popular_variation_value);
        }

        public void bindSearch(Search search) {
            mSearch = search;
            mSearchRatingNumberTextView.setText(String.valueOf(mSearch.getRatingNumber()));
            mSearchWordTextView.setText(mSearch.getSearchWord());
            mSearchVariationImageView.setImageResource(mSearch.getStateImageResId());
            mSearchVariationValueTextView.setText(String.valueOf(mSearch.getVariationValue()));
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mSearch.getSearchWord(), Toast.LENGTH_SHORT).show();
            mOnPassDataListener.onStringDataPass(mSearch.getSearchWord());
        }
    }

    OnPassDataListener mOnPassDataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnPassDataListener = (OnPassDataListener) context;
    }

}
