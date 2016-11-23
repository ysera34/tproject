package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Content;
import com.tacademy.v04.chemi.model.ContentMainStorage;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment
        implements View.OnClickListener, View.OnTouchListener {

    private NestedScrollView mMainNestedScrollView;
    private ImageSwitcher mMainImageSwitch;
//    private ImageView mMainImageView;

    private float x1,x2;
    private float y1, y2;

    private int mBannerIndex;
    private int mBannerImageArray[];

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        mMainNestedScrollView = (NestedScrollView) view.findViewById(R.id.main_nested_scroller_view);
//        mMainNestedScrollView.setFillViewport(true);
        mMainImageSwitch = (ImageSwitcher) view.findViewById(R.id.main_image_switcher);
        mMainImageSwitch.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getActivity());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                myView.setImageResource(mBannerImageArray[mBannerIndex]);
                myView.setOnTouchListener(new View.OnTouchListener() {
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
                                    Toast.makeText(getActivity(), "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                                }

                                // if right to left sweep event on screen
                                if (x1 > x2) {
                                    Toast.makeText(getActivity(), "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                                }

                                // if UP to Down sweep event on screen
                                if (y1 < y2) {
                                    Toast.makeText(getActivity(), "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                                }

                                //if Down to UP sweep event on screen
                                if (y1 > y2) {
                                    Toast.makeText(getActivity(), "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                        return true;
                        }
                    });
                    return myView;
                }
            });

        // Declare the animations and initialize them
        Animation in = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        // set the animation type to imageSwitcher
        mMainImageSwitch.setInAnimation(in);
        mMainImageSwitch.setOutAnimation(out);

//        mMainImageView = (ImageView) view.findViewById(R.id.main_image_view);

        mMainContentRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mMainContentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainContentRecyclerView.setNestedScrollingEnabled(false);

        (view.findViewById(R.id.prev_button)).setOnClickListener(this);
        (view.findViewById(R.id.next_button)).setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev_button :
                mMainImageSwitch.setImageResource(R.drawable.main_content_sample01);
                break;

            case R.id.next_button:
                mMainImageSwitch.setImageResource(R.drawable.main_content_sample02);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.main_image_switcher) {
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
                    if (x1 < x2)
                    {
                        Toast.makeText(getActivity(), "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                    }

                    // if right to left sweep event on screen
                    if (x1 > x2)
                    {
                        Toast.makeText(getActivity(), "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                    }

                    // if UP to Down sweep event on screen
                    if (y1 < y2)
                    {
                        Toast.makeText(getActivity(), "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                    }

                    //if Down to UP sweep event on screen
                    if (y1 > y2)
                    {
                        Toast.makeText(getActivity(), "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        return false;
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
