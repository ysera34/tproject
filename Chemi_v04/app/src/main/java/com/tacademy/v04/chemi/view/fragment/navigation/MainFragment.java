package com.tacademy.v04.chemi.view.fragment.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentMainStorage;
import com.tacademy.v04.chemi.view.activity.content.ContentActivity;
import com.tacademy.v04.chemi.view.custom.ProgressDialog;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private Thread mSwipeThread;

    private NestedScrollView mMainNestedScrollView;
    private ViewPager mImageBannerViewPager;
    private ImageBannerViewPagerAdapter mViewPagerAdapter;
    private PageIndicator mPageIndicator;
    private CirclePageIndicator mCircleIndicator;

    private int mBannerIndex;
    private int mImageBannerArray[];

    Animation mImageAnimationLeftIn;
    Animation mImageAnimationRightOut;
    Animation mImageAnimationRightIn;
    Animation mImageAnimationLeftOut;

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
        setHasOptionsMenu(true);
        ContentMainStorage contentMainStorage = ContentMainStorage.get(getActivity());
        mContents = contentMainStorage.getContents();

        mImageBannerArray = new int[]{0, R.drawable.main_banner01,
                R.drawable.main_banner02, R.drawable.main_banner03, 0,};
//        mImageAnimationLeftIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
//        mImageAnimationRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
//        mImageAnimationRightIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
//        mImageAnimationLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMainNestedScrollView = (NestedScrollView) view.findViewById(R.id.main_nested_scroller_view);

        mImageBannerViewPager = (ViewPager) view.findViewById(R.id.main_image_banner_view_pager);
        mViewPagerAdapter = new ImageBannerViewPagerAdapter();
        mImageBannerViewPager.setAdapter(mViewPagerAdapter);
        mImageBannerViewPager.setCurrentItem(1);
        mImageBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                try {
                    mPagerHandler.add(Integer.valueOf(positionOffsetPixels));
                    if (mPagerHandler.size() > 0) {
                        if (position == mImageBannerArray.length - 2
                                & (mPagerHandler.get(mPagerHandler.size() - 1) - mPagerHandler.get(0) < 100)
                                & mScrollState == 2
                                & !isFirstVisitEnd) {
                            mImageBannerViewPager.setCurrentItem(1, false);
                        }

                        if (position == 0 & (mPagerHandler.get(mPagerHandler.size() - 1) - mPagerHandler.get(0) > -100)
                                & mScrollState == 2
                                & isFirstVisitBegin) {
                            mImageBannerViewPager.setCurrentItem(mImageBannerArray.length - 2, false);
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }

            @Override
            public void onPageSelected(int position) {
                mPagerHandler.clear();
                if (position == mImageBannerArray.length - 1) {
                    isFirstVisitEnd = false;
                } else {
                    isFirstVisitEnd = true;
                }
                if (position == 0) {
                    isFirstVisitBegin = false;
                } else {
                    isFirstVisitBegin = true;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mScrollState = state;
            }
        });

        mMainContentRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mMainContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainContentRecyclerView.setNestedScrollingEnabled(false);

        mCircleIndicator = (CirclePageIndicator) view.findViewById(R.id.main_view_pager_indicator);
        mPageIndicator = mCircleIndicator;
        mCircleIndicator.setViewPager(mImageBannerViewPager);
        mCircleIndicator.setSnap(true);

        (view.findViewById(R.id.prev_button)).setOnClickListener(this);
        (view.findViewById(R.id.next_button)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();

        mSwipeThread = new Thread(mRunnable);
        mSwipeThread.start();
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

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.w(TAG, "runnable InterruptedException : " + e.toString());
            }
            mHandler.sendEmptyMessage(0);
        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
//            sweepRightToLeft();
//            indicateSweepImage(mBannerIndex);
            Log.w(TAG, "handler alive..., please kill me.....");
            mHandler.sendEmptyMessageDelayed(0, 5000);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeMessages(0);
        if (mSwipeThread != null) {
            mSwipeThread.interrupt();
            Log.w(TAG + " onPause", "mSwipeThread interrupt. i`m died, Thank you");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeMessages(0);
        if (mSwipeThread != null) {
            mSwipeThread.interrupt();
            Log.w(TAG + " onStop", "mSwipeThread interrupt. i`m died, Thank you");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeMessages(0);
        if (mSwipeThread != null) {
            mSwipeThread.interrupt();
            Log.w(TAG + " onDestroyView", "mSwipeThread interrupt. i`m died, Thank you");
        }
    }

    @Override
    public void onClick(View view) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        switch (view.getId()) {
            case R.id.prev_button :
                progressDialog.show();
                break;
            case R.id.next_button:
                progressDialog.dismiss();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        if ((menu.findItem(R.id.action_home)) != null) {
            menu.findItem(R.id.action_home).setVisible(false);
        }
//        menu.findItem(R.id.action_search).setVisible(false);
//        menu.findItem(R.id.action_home).setVisible(false);
//        getActivity().getMenuInflater().inflate(R.menu.menu_toolbar, menu);
    }

    private int mScrollState;
    private boolean isFirstVisitBegin = true;
    private boolean isFirstVisitEnd = true;
    private ArrayList<Integer> mPagerHandler = new ArrayList<>();

    private class ImageBannerViewPagerAdapter extends PagerAdapter {

        public ImageBannerViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            LayoutInflater mLayoutInflater = LayoutInflater.from(container.getContext());
            View view = mLayoutInflater.inflate(R.layout.view_pager_item_main, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.view_pager_item_main_image_view);
            imageView.setImageResource(mImageBannerArray[position]);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mImageBannerArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
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

            Intent intent = ContentActivity.newIntent(getActivity(), mContent.getContentType());
            startActivity(intent);

        }
    }
}
