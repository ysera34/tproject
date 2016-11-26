package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentMainStorage;
import com.tacademy.v04.chemi.view.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment
        implements View.OnClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private NestedScrollView mMainNestedScrollView;
    private ImageSwitcher mMainImageSwitch;
//    private ImageView mMainImageView;
    private LinearLayout mMainImageSwitcherIndicatorLayout;
    private TextView[] mIndicatorTextViews;
    private MainActivity mMainActivity;

    private int activeColor;
    private int inactiveColor;

    private int mBannerIndex;
    private int mBannerImageArray[];

    private float x1,x2;
    private float y1, y2;

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
        ContentMainStorage contentMainStorage = ContentMainStorage.get(getActivity());
        mContents = contentMainStorage.getContents();

        mBannerImageArray = new int[]{R.drawable.banner_sample01, R.drawable.banner_sample02,
                R.drawable.banner_sample03, R.drawable.banner_sample04, R.drawable.banner_sample05,};
        mMainActivity = (MainActivity) getActivity();

        activeColor = getResources().getColor(R.color.main_image_switcher_indicator_active_color);
        inactiveColor = getResources().getColor(R.color.main_image_switcher_indicator_inactive_color);

        mImageAnimationLeftIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        mImageAnimationRightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        mImageAnimationRightIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        mImageAnimationLeftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMainNestedScrollView = (NestedScrollView) view.findViewById(R.id.main_nested_scroller_view);
        mMainImageSwitch = (ImageSwitcher) view.findViewById(R.id.main_image_switcher);

        mMainImageSwitch.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final ImageView mMainImageView = new ImageView(getActivity());
                mMainImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mMainImageView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                mMainImageView.setImageResource(mBannerImageArray[mBannerIndex]);
//                mMainImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getActivity(), "해당 컨텐츠로 이동합니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
                mMainImageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            // when user first touches the screen we get x and y coordinate
                            case MotionEvent.ACTION_DOWN :
                                x1 = motionEvent.getX();
                                y1 = motionEvent.getY();
                                break;
                            case MotionEvent.ACTION_UP :
                                x2 = motionEvent.getX();
                                y2 = motionEvent.getY();

                                //if left to right sweep event on screen
                                if (x1 < x2) {
                                    // Toast.makeText(getActivity(), "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();
                                    sweepLeftToRight();
//                                    mBannerIndex--;
//                                    if (mBannerIndex == -1) {
//                                        mBannerIndex = mBannerImageArray.length - 1;
//                                    }
//                                    mMainImageSwitch.setInAnimation(mImageAnimationLeftIn);
//                                    mMainImageSwitch.setOutAnimation(mImageAnimationRightOut);
//                                    mMainImageSwitch.setImageResource(mBannerImageArray[mBannerIndex]);
                                    indicateSweepImage(mBannerIndex);
                                }

                                // if right to left sweep event on screen
                                if (x1 > x2) {
                                    // Toast.makeText(getActivity(), "Right to Left Swap Performed", Toast.LENGTH_SHORT).show();
                                    sweepRightToLeft();
//                                    mBannerIndex++;
//                                    if (mBannerIndex == mBannerImageArray.length) {
//                                        mBannerIndex = 0;
//                                    }
//                                    mMainImageSwitch.setInAnimation(mImageAnimationRightIn);
//                                    mMainImageSwitch.setOutAnimation(mImageAnimationLeftOut);
//                                    mMainImageSwitch.setImageResource(mBannerImageArray[mBannerIndex]);
                                    indicateSweepImage(mBannerIndex);
                                }

                                // if UP to Down sweep event on screen
                                if (y1 < y2) {
                                     // Toast.makeText(getActivity(), "UP to Down Swap Performed", Toast.LENGTH_SHORT).show();
                                }

                                //if Down to UP sweep event on screen
                                if (y1 > y2) {
                                     // Toast.makeText(getActivity(), "Down to UP Swap Performed", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        return true;
                        }
                    });
                    return mMainImageView;
                }
            });

//        mMainImageView = (ImageView) view.findViewById(R.id.main_image_view);
        mMainImageSwitcherIndicatorLayout =
                (LinearLayout) view.findViewById(R.id.main_image_switcher_indicator_layout);
        mIndicatorTextViews = new TextView[mBannerImageArray.length];
        indicateSweepImage(0);

        mMainContentRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mMainContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainContentRecyclerView.setNestedScrollingEnabled(false);

//        (view.findViewById(R.id.prev_button)).setOnClickListener(this);
//        (view.findViewById(R.id.next_button)).setOnClickListener(this);

        return view;
    }

//    private Handler mPostHandler;
//    private Thread mTimerThread;
    private Thread mSwipeThread;

    @Override
    public void onResume() {
        super.onResume();
        updateUI();

        mSwipeThread = new Thread(mRunnable);
        mSwipeThread.start();
//        mTimerThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    Log.w("main fragment", "mTimerThread InterruptedException. i`m died, Thank you");
//                }
//                mSwipeThread.start();
//            }
//        });
//        mTimerThread.start();

//        mPostHandler = new Handler();
//        mPostHandler.postDelayed(mSwipeThread ,4000);
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
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Log.w(TAG, "runnable InterruptedException : " + e.toString());
            }
            mHandler.sendEmptyMessage(0);
        }
    };

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            sweepRightToLeft();
            indicateSweepImage(mBannerIndex);
            Log.w(TAG, "handler alive..., please kill me.....");
            mHandler.sendEmptyMessageDelayed(0, 4000);
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
//        if (mTimerThread != null) {
//            mTimerThread.interrupt();
//            Log.w("main fragment", "mTimerThread interrupt. i`m died, Thank you");
//        }
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
//        if (mTimerThread != null) {
//            mTimerThread.interrupt();
//            Log.w("fragment onDestroyView", "mTimerThread interrupt. i`m died, Thank you");
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.prev_button :
//                mMainImageSwitch.setImageResource(R.drawable.main_content_sample01);
//                break;
//
//            case R.id.next_button:
//                mMainImageSwitch.setImageResource(R.drawable.main_content_sample02);
//                break;
        }
    }

    //if left to right sweep event on screen
    private void sweepLeftToRight() {
        mBannerIndex--;
        if (mBannerIndex == -1) {
            mBannerIndex = mBannerImageArray.length - 1;
        }
        mMainImageSwitch.setInAnimation(mImageAnimationLeftIn);
        mMainImageSwitch.setOutAnimation(mImageAnimationRightOut);
        mMainImageSwitch.setImageResource(mBannerImageArray[mBannerIndex]);
    }

    // if right to left sweep event on screen
    private void sweepRightToLeft() {
        mBannerIndex++;
        if (mBannerIndex == mBannerImageArray.length) {
            mBannerIndex = 0;
        }
        mMainImageSwitch.setInAnimation(mImageAnimationRightIn);
        mMainImageSwitch.setOutAnimation(mImageAnimationLeftOut);
        mMainImageSwitch.setImageResource(mBannerImageArray[mBannerIndex]);
    }

    private void indicateSweepImage(int currentImage) {

        mMainImageSwitcherIndicatorLayout.removeAllViews();
        for (int i = 0; i < mBannerImageArray.length; i++) {
            mIndicatorTextViews[i] = new TextView(getActivity());
            mIndicatorTextViews[i].setText(Html.fromHtml("&#8226;"));
            mIndicatorTextViews[i].setPadding(16, 0, 16, 0);
            mIndicatorTextViews[i].setTextSize(35);
            mIndicatorTextViews[i].setTextColor(inactiveColor);
            mMainImageSwitcherIndicatorLayout.addView(mIndicatorTextViews[i]);
        }

        if (mIndicatorTextViews.length > 0)
            mIndicatorTextViews[currentImage].setTextColor(activeColor);
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
