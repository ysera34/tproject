package com.tacademy.v04.chemi.view.fragment.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentMainStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ContentFragment extends Fragment {

    private static final String TAG = ContentFragment.class.getSimpleName();

    private static final String ARG_CONTENT_TYPE = "content_type";

    private ContentMainStorage mContentMainStorage;
    private ArrayList<Content> mContents;
    private Content mContent;

    private ImageView mContentDetailImageView;
    int mContentType;

    public static ContentFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTENT_TYPE, type);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {
        mContentMainStorage = ContentMainStorage.get(getActivity());
        mContents = mContentMainStorage.getContents();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentType = getArguments().getInt(ARG_CONTENT_TYPE);
//        mContent = mContents.get(mContentType-1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mContentDetailImageView = (ImageView) view.findViewById(R.id.content_detail_image_view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (mContentType) {
            case 1 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView.setImageResource(R.drawable.content01);
                break;
            case 2 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView.setImageResource(R.drawable.content02);
                break;
            case 3 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView.setImageResource(R.drawable.content03);
                break;
        }
    }
}
