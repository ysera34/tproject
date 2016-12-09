package com.tacademy.v04.chemi.view.fragment.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.ContentImageStorage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ContentFragment extends Fragment {

    private static final String TAG = ContentFragment.class.getSimpleName();

    private static final String ARG_CONTENT_TYPE = "content_type";
    private static final String ARG_CONTENT_NUMBER = "content_number";

    private RecyclerView mContentRecyclerView;
    private ContentAdapter mContentAdapter;

    private ContentImageStorage mContentImageStorage;
    private HashMap<Integer, ArrayList<Integer>> mContentImageResIdMap;
    private ArrayList<Integer> mContentImageRedIds;
//    private Content mContent;

    private int mContentType;
    private int mContentNumber;

    public static ContentFragment newInstance(int contentType) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTENT_TYPE, contentType);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ContentFragment newInstance(int contentType, int contentNumber) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTENT_TYPE, contentType);
        args.putSerializable(ARG_CONTENT_NUMBER, contentNumber);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentType = getArguments().getInt(ARG_CONTENT_TYPE, 0);
            mContentNumber = getArguments().getInt(ARG_CONTENT_NUMBER, 0);

        }

        mContentImageStorage = ContentImageStorage.get(getContext());
        mContentImageResIdMap = mContentImageStorage.getContentImageResIdMap();

        mContentImageRedIds = mContentImageResIdMap.get(mContentNumber);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        mContentRecyclerView = (RecyclerView) view.findViewById(R.id.content_recycler_view);
        mContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
            mContentAdapter = new ContentAdapter(mContentImageRedIds);
            mContentRecyclerView.setAdapter(mContentAdapter);
        } else {
            mContentAdapter.notifyDataSetChanged();
        }
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentHolder> {

        private ArrayList<Integer> mContentImageIds;

        public ContentAdapter(ArrayList<Integer> contentImageIds) {
            mContentImageIds = contentImageIds;
        }

        @Override
        public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_item_content, parent, false);
            return new ContentHolder(view);
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, int position) {
            int imageId = mContentImageIds.get(position);
            holder.bindContent(imageId);
        }

        @Override
        public int getItemCount() {
            return mContentImageIds.size();
        }
    }

    private class ContentHolder extends RecyclerView.ViewHolder {

        private int mImageId;
        private ImageView mContentImageView;

        public ContentHolder(View itemView) {
            super(itemView);
            mContentImageView = (ImageView) itemView.findViewById(R.id.list_item_content_image);
        }

        public void bindContent(int imageId) {
            mImageId = imageId;
            mContentImageView.setImageResource(mImageId);
        }
    }

}
