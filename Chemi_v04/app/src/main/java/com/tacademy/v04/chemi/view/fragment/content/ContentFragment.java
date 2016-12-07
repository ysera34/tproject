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

    private ImageView mContentDetailImageView1;
    private ImageView mContentDetailImageView2;
    private ImageView mContentDetailImageView3;
    private ImageView mContentDetailImageView4;
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
        mContentDetailImageView1 = (ImageView) view.findViewById(R.id.content_detail_image_view1);
        mContentDetailImageView2 = (ImageView) view.findViewById(R.id.content_detail_image_view2);
        mContentDetailImageView3 = (ImageView) view.findViewById(R.id.content_detail_image_view3);
        mContentDetailImageView4 = (ImageView) view.findViewById(R.id.content_detail_image_view4);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mContents.
        switch (mContentType) {
            case 1 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView1.setImageResource(R.drawable.content1001);
                break;
            case 2 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView1.setImageResource(R.drawable.content2001_1);
                mContentDetailImageView2.setImageResource(R.drawable.content2001_2);
                mContentDetailImageView3.setImageResource(R.drawable.content2001_3);
                mContentDetailImageView4.setImageResource(R.drawable.content2001_4);
                break;
            case 3 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView1.setImageResource(R.drawable.content3001);
                break;
            case 4 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView1.setImageResource(R.drawable.content1003);
                break;
            case 5 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView1.setImageResource(R.drawable.content2002);
                break;
            case 6 :
                Log.i(TAG, "mContentType : " + mContentType);
                mContentDetailImageView1.setImageResource(R.drawable.content3001);
                break;
        }
    }
}
