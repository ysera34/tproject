package com.tacademy.v04.chemi.view.fragment.content;

import android.content.Intent;
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

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentArchiveStorage;
import com.tacademy.v04.chemi.view.activity.content.ContentActivity;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 12. 2..
 */
public class ContentT3ListFragment extends Fragment {

    private static final String ARG_CONTENT_TYPE = "content_type";

    private RecyclerView mContentT3RecyclerView;
    private ContentAdapter mContentAdapter;
    private ArrayList<Content> mContents;
    int mContentType;


    public static ContentT3ListFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTENT_TYPE, type);

        ContentT3ListFragment fragment = new ContentT3ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ContentT3ListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentType = getArguments().getInt(ARG_CONTENT_TYPE);

        ContentArchiveStorage contentArchiveStorage = ContentArchiveStorage.get(getActivity());
        mContents = contentArchiveStorage.getTypeContents(mContentType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_content_t3, container, false);

        mContentT3RecyclerView = (RecyclerView) view.findViewById(R.id.content_type3_recycler_view);
        mContentT3RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

        if (mContentAdapter == null) {
            mContentAdapter = new ContentAdapter(mContents);
            mContentT3RecyclerView.setAdapter(mContentAdapter);
        } else {
            mContentAdapter.notifyDataSetChanged();
        }
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

    private class ContentHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Content mContent;

        private ImageView mContentImageView;
        private ImageView mContentCategoryImageView;
        private TextView mContentTitleTextView;
        private TextView mContentDescriptionTextView;

        public ContentHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mContentImageView =
                    (ImageView) itemView.findViewById(R.id.list_item_content_main_image_view);
            mContentCategoryImageView =
                    (ImageView) itemView.findViewById(R.id.list_item_content_main_content_type_image);
            mContentTitleTextView =
                    (TextView) itemView.findViewById(R.id.list_item_content_main_content_title);
            mContentDescriptionTextView =
                    (TextView) itemView.findViewById(R.id.list_item_content_main_content_description);
        }

        public void bindContent(Content content) {
            mContent = content;
            mContentImageView.setImageResource(mContent.getContentImageId());
            mContentCategoryImageView.setImageResource(mContent.getContentTypeImageId());
            mContentTitleTextView.setText(mContent.getTitle());
            mContentDescriptionTextView.setText(mContent.getDescription());
        }

        @Override
        public void onClick(View view) {

            Intent intent = ContentActivity.newIntent(getActivity(),
                    mContent.getContentType(), mContent.getContentNumber());
            startActivity(intent);

        }
    }
}

