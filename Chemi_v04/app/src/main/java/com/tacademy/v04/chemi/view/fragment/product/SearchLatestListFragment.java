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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.util.listener.OnPassDataListener;
import com.tacademy.v04.chemi.common.util.decorator.SeparatorDecoration;
import com.tacademy.v04.chemi.model.Search;
import com.tacademy.v04.chemi.model.SearchLatestStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class SearchLatestListFragment extends Fragment {

    private static final String TAG = SearchLatestListFragment.class.getSimpleName();

    private RecyclerView mSearchLatestRecyclerView;
    private LatestWordAdapter mLatestWordAdapter;
    private ArrayList<Search> mSearches;
    private SearchLatestStorage searchLatestStorage;

    public SearchLatestListFragment() {
    }

    public static SearchLatestListFragment newInstance() {

        SearchLatestListFragment fragment = new SearchLatestListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchLatestStorage = SearchLatestStorage.get(getActivity());
        mSearches = searchLatestStorage.getSearches();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_latest_list, container, false);

        mSearchLatestRecyclerView = (RecyclerView) view.findViewById(R.id.product_search_latest_recycler_view);
        mSearchLatestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SeparatorDecoration decoration =
                new SeparatorDecoration(getActivity(), android.R.color.transparent, 1.5f);
        mSearchLatestRecyclerView.addItemDecoration(decoration);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mLatestWordAdapter == null) {
            mLatestWordAdapter = new LatestWordAdapter(mSearches);
            mSearchLatestRecyclerView.setAdapter(mLatestWordAdapter);
        } else {
            mLatestWordAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class LatestWordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Search> mSearches;

        public LatestWordAdapter(ArrayList<Search> searches) {
            mSearches = searches;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view;
            if (viewType == EMPTY_VIEW) {
                view = layoutInflater.inflate(R.layout.view_empty_latest_search, parent,false);
                return new EmptyViewHolder(view);
            }
            view = layoutInflater.inflate(R.layout.list_item_search_latest, parent, false);
            return new LatestWordHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof LatestWordHolder) {
                Search search = mSearches.get(position);
                ((LatestWordHolder) holder).bindSearch(search);
            }
        }

        @Override
        public int getItemCount() {
            return mSearches.size() > 0 ? mSearches.size() : 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (mSearches.size() == 0) {
                return EMPTY_VIEW;
            }
            return super.getItemViewType(position);
        }
    }

    private static final int EMPTY_VIEW = -1;

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class LatestWordHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Search mSearch;

        private TextView mSearchWordTextView;
        private ImageButton mSearchItemClearButton;

        public LatestWordHolder(View itemView) {
            super(itemView);

            mSearchWordTextView = (TextView)
                    itemView.findViewById(R.id.list_item_latest_search_word);
            mSearchWordTextView.setOnClickListener(this);
            mSearchItemClearButton = (ImageButton)
                    itemView.findViewById(R.id.list_item_clear_image_button);
            mSearchItemClearButton.setOnClickListener(this);
        }

        public void bindSearch(Search search) {
            mSearch = search;
            mSearchWordTextView.setText(mSearch.getSearchWord());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.list_item_latest_search_word :
//                    Toast.makeText(getActivity(), mSearch.getSearchWord(), Toast.LENGTH_SHORT).show();
                    mOnPassDataListener.onStringDataPass(mSearch.getSearchWord());
                    break;
                case R.id.list_item_clear_image_button :
                    Toast.makeText(getActivity(), R.string.remove_search_word_info_message,
                            Toast.LENGTH_SHORT).show();
                    mSearches = searchLatestStorage.removeSearch(mSearch, this.getAdapterPosition());
                    mLatestWordAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    OnPassDataListener mOnPassDataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnPassDataListener = (OnPassDataListener) context;
    }
}
