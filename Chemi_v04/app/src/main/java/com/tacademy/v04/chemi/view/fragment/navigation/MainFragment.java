package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentMainStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment {

    private NestedScrollView mMainNestedScrollView;
    private RecyclerView mMainContentRecyclerView;
    private ContentAdapter mContentAdapter;
    private ArrayList<Content> mContents;

    public MainFragment() {
    }

    public static MainFragment newInstance() {

        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentMainStorage contentMainStorage = ContentMainStorage.get(getActivity());
        mContents = contentMainStorage.getContents();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        mMainNestedScrollView = (NestedScrollView) view.findViewById(R.id.main_nested_scroller_view);
//        mMainNestedScrollView.setFillViewport(true);

        mMainContentRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mMainContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainContentRecyclerView.setNestedScrollingEnabled(false);

//        SeparatorDecoration decoration =
//                new SeparatorDecoration(getActivity(), android.R.color.transparent, 2.5f);
//        mMainContentRecyclerView.addItemDecoration(decoration);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {

        if (mContentAdapter == null) {
            mContentAdapter = new ContentAdapter(mContents);
            mMainContentRecyclerView.setAdapter(mContentAdapter);
        } else {
            mContentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentHolder> {

        private ArrayList<Content> mContents;

        public ContentAdapter(ArrayList<Content> contents) {
            mContents = contents;
        }

        @Override
        public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater
                    .inflate(R.layout.list_item_content_main, parent, false);
            return new ContentHolder(view);
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, int position) {
            Content content = mContents.get(position);
            holder.bindContent(content);
        }

        @Override
        public int getItemCount() {
            return mContents.size();
        }
    }

    private class ContentHolder extends RecyclerView.ViewHolder {

        private Content mContent;

        private ImageView mContentImageView;
        private ImageView mContentCategoryImageView;
        private TextView mContentTitleTextView;
        private TextView mContentDescriptionTextView;

        public ContentHolder(View itemView) {
            super(itemView);

            mContentImageView =
                    (ImageView) itemView.findViewById(R.id.list_item_content_main_image_view);
            mContentCategoryImageView =
                    (ImageView) itemView.findViewById(R.id.list_item_content_main_content_category_image);
            mContentTitleTextView =
                    (TextView) itemView.findViewById(R.id.list_item_content_main_content_title);
            mContentDescriptionTextView =
                    (TextView) itemView.findViewById(R.id.list_item_content_main_content_description);
        }

        public void bindContent(Content content) {
            mContent = content;
            mContentImageView.setImageResource(mContent.getImageId());
        }
    }
}
