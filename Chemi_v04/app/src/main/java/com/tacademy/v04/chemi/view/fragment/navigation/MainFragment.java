package com.tacademy.v04.chemi.view.fragment.navigation;

import android.content.Intent;
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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentMainStorage;
import com.tacademy.v04.chemi.view.activity.MainActivity;
import com.tacademy.v04.chemi.view.activity.content.ContentActivity;

import java.util.ArrayList;

import static com.tacademy.v04.chemi.common.Common.REQUEST_NAVIGATION_ANALYZE_REQUEST;
import static com.tacademy.v04.chemi.common.Common.REQUEST_NAVIGATION_FAQ;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private Thread mSwipeThread;

    private NestedScrollView mMainNestedScrollView;
    private ImageSwitcher mMainImageSwitch;
//    private ImageView mMainImageView;
    private LinearLayout mMainImageSwitcherIndicatorLayout;
    private TextView[] mIndicatorTextViews;

    private int activeColor;
    private int inactiveColor;

    private ImageSwitcherGestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
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
        setHasOptionsMenu(true);
        ContentMainStorage contentMainStorage = ContentMainStorage.get(getActivity());
        mContents = contentMainStorage.getContents();

        mBannerImageArray = new int[]{R.drawable.main_banner01,
                R.drawable.main_banner02, R.drawable.main_banner03,};

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

        mMainImageSwitch.setInAnimation(mImageAnimationLeftIn);
        mMainImageSwitch.setOutAnimation(mImageAnimationRightOut);

        mMainImageSwitch.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final ImageView mMainImageView = new ImageView(getActivity());
                mMainImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                mMainImageView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                mMainImageView.setImageResource(mBannerImageArray[mBannerIndex]);
                mMainImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mBannerIndex == 0) {
                            Intent intent = MainActivity.newIntent(getActivity(), REQUEST_NAVIGATION_FAQ);
                            startActivity(intent);
                        } else if (mBannerIndex == 1) {
                            Toast.makeText(getActivity(), "업데이트 예정입니다", Toast.LENGTH_SHORT).show();
                        } else if (mBannerIndex == 2) {
                            Intent intent = MainActivity.newIntent(getActivity(), REQUEST_NAVIGATION_ANALYZE_REQUEST);
                            startActivity(intent);
                        }

                    }
                });

                /*
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

                    });  */
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
            sweepRightToLeft();
            indicateSweepImage(mBannerIndex);
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
//            mIndicatorTextViews[i] = new TextView(getActivity());
            mIndicatorTextViews[i] = new TextView(getActivity());
            mIndicatorTextViews[i].setText(Html.fromHtml("&#8226;"));
            mIndicatorTextViews[i].setPadding(10, 0, 10, 0);
            mIndicatorTextViews[i].setTextSize(35);
            mIndicatorTextViews[i].setTextColor(inactiveColor);
            mMainImageSwitcherIndicatorLayout.addView(mIndicatorTextViews[i]);
        }

        if (mIndicatorTextViews.length > 0)
            mIndicatorTextViews[currentImage].setTextColor(activeColor);
    }

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 400;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    private class ImageSwitcherGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
//            return super.onDown(e);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onHorizonTouch(true);
//                    return false;
                }
                if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onHorizonTouch(false);
                }
            } catch (Exception e) {

            }
            return false;
//            return super.onFling(e1, e2, velocityX, velocityY);
        }

        private void onHorizonTouch(Boolean toLeft) {
            if (!toLeft && mBannerIndex > 0) {
                mMainImageSwitch.setInAnimation(mImageAnimationLeftIn);
                mMainImageSwitch.setOutAnimation(mImageAnimationRightOut);
                mMainImageSwitch.setImageResource(mBannerImageArray[mBannerIndex]);
                mBannerIndex--;
                if (mBannerIndex == -1) {
                    mBannerIndex = mBannerImageArray.length - 1;
                }
                indicateSweepImage(mBannerIndex);
            }
            if (toLeft && mBannerIndex < 1) {
                mMainImageSwitch.setInAnimation(mImageAnimationRightIn);
                mMainImageSwitch.setOutAnimation(mImageAnimationLeftOut);
                mMainImageSwitch.setImageResource(mBannerImageArray[mBannerIndex]);
                mBannerIndex++;
                if (mBannerIndex == mBannerImageArray.length) {
                    mBannerIndex = 0;
                }
                indicateSweepImage(mBannerIndex);

            }
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
