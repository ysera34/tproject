package com.tacademy.v04.chemi.view.fragment.product;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.OnDataPass;
import com.tacademy.v04.chemi.model.Search;
import com.tacademy.v04.chemi.model.SearchStorage;

import java.util.List;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class SearchPopularListFragment extends Fragment {

    private static final String SEARCH_WORD = "SearchWord";

    private RecyclerView mSearchPopularRecyclerView;
    private PopularWordAdapter mPopularWordAdapter;
    private List<Search> mSearches;

    public SearchPopularListFragment() {
    }

    public static SearchPopularListFragment newInstance() {

        SearchPopularListFragment fragment = new SearchPopularListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchStorage searchStorage = SearchStorage.get(getActivity());
        mSearches = searchStorage.getSearches();
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

        private List<Search> mSearches;

        public PopularWordAdapter(List<Search> searches) {
            mSearches = searches;
        }

        @Override
        public PopularWordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
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
                    itemView.findViewById(R.id.list_item_search_word);
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
            mOnDataPasser.onStringDataPass(mSearch.getSearchWord());

        }
    }

    OnDataPass mOnDataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnDataPasser = (OnDataPass) context;
    }

    public class SeparatorDecoration extends RecyclerView.ItemDecoration {

        private final Paint mPaint;

        public SeparatorDecoration(Context context, int color, float heightDp) {
            mPaint = new Paint();
            mPaint.setColor(color);
            final float thickness = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    heightDp, context.getResources().getDisplayMetrics());
            mPaint.setStrokeWidth(thickness);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            // we want to retrieve the position in the list
            final int position = params.getViewAdapterPosition();

            // and add a separator to any view but the last one
            if (position < state.getItemCount()) {
                outRect.set(0, 0, 0, (int) mPaint.getStrokeWidth()); // left, top, right, bottom
            } else {
                outRect.setEmpty(); // 0, 0, 0, 0
            }
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            // we set the stroke width before, so as to correctly draw the line we have to offset by width / 2
            final int offset = (int) (mPaint.getStrokeWidth() / 2);

            // this will iterate over every visible view
            for (int i = 0; i < parent.getChildCount(); i++) {
                // get the view
                final View view = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

                // get the position
                final int position = params.getViewAdapterPosition();

                // and finally draw the separator
                if (position < state.getItemCount()) {
                    c.drawLine(view.getLeft(), view.getBottom() + offset, view.getRight(), view.getBottom() + offset, mPaint);
                }
            }
        }
    }
}
