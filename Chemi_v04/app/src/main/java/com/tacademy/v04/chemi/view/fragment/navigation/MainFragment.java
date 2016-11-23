package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.SeparatorDecoration;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private NestedScrollView mMainNestedScrollView;
    private ViewPager mMainContentViewPager;
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
        ContentStorage contentStorage = ContentStorage.get(getActivity());
        mContents = contentStorage.getContents();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMainNestedScrollView = (NestedScrollView) view.findViewById(R.id.main_nested_scroller_view);
//        mMainNestedScrollView.setFillViewport(true);
        mMainContentViewPager = (ViewPager) view.findViewById(R.id.main_view_pager);
        mMainContentViewPager.setAdapter(new ImagePagerAdapter());
//        mMainContentViewPager.setPageMargin(-156);
//        mMainContentViewPager.setOffscreenPageLimit(3);
        mMainContentViewPager.setOnPageChangeListener(this);

        mMainContentRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mMainContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainContentRecyclerView.setNestedScrollingEnabled(false);

        SeparatorDecoration decoration =
                new SeparatorDecoration(getActivity(), android.R.color.transparent, 2.5f);
        mMainContentRecyclerView.addItemDecoration(decoration);

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
        private TextView mContentTitleTextView;
        private TextView mContentDescriptionTextView;

        public ContentHolder(View itemView) {
            super(itemView);

            mContentImageView = (ImageView) itemView.findViewById(R.id.list_item_content_main_content_image);
            mContentTitleTextView = (TextView) itemView.findViewById(R.id.list_item_content_main_content_title);
            mContentDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_content_main_content_description);
        }

        public void bindContent(Content content) {
            mContent = content;

        }
    }

    private float MIN_SCALE = 1f - 1f / 7f;
    private float MAX_SCALE = 1f;

    private class ImagePagerAdapter extends PagerAdapter {

        private boolean mIsDefaultItemSelected = false;

        private int[] mCards = {
                R.drawable.banner_sample01,
                R.drawable.banner_sample02,
                R.drawable.banner_sample03,
                R.drawable.banner_sample04,
                R.drawable.banner_sample05,};

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView contentImageView = (ImageView)
                    View.inflate(container.getContext(), R.layout.view_pager_item_main, null);
            contentImageView.setImageDrawable(getResources().getDrawable(mCards[position]));
            contentImageView.setTag(position);

            if (!mIsDefaultItemSelected) {
                contentImageView.setScaleX(MAX_SCALE);
                contentImageView.setScaleY(MAX_SCALE);
                mIsDefaultItemSelected = true;
            } else {
                contentImageView.setScaleX(MIN_SCALE);
                contentImageView.setScaleY(MIN_SCALE);
            }

            container.addView(contentImageView);
            return contentImageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mCards.length;
        }
cd ..
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        for (int i = 0; i < mMainContentViewPager.getChildCount(); i++) {
            View cardView = mMainContentViewPager.getChildAt(i);
            int itemPosition = (Integer) cardView.getTag();

            if (itemPosition == position) {
                cardView.setScaleX(MAX_SCALE - positionOffset / 7f);
                cardView.setScaleY(MAX_SCALE - positionOffset / 7f);
            }

            if (itemPosition == (position + 1)) {
                cardView.setScaleX(MIN_SCALE + positionOffset / 7f);
                cardView.setScaleY(MIN_SCALE + positionOffset / 7f);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
