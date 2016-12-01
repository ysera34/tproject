package com.tacademy.v04.chemi.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.util.manager.PreferenceManager;

/**
 * Created by yoon on 2016. 12. 2..
 */

public class IntroActivity extends AppBaseActivity implements View.OnClickListener {

    private ViewPager mIntroViewPager;
    private IntroViewPagerAdapter mIntroViewPagerAdapter;
    private LinearLayout mIntroDotsLayout;
    private TextView[] mTextViews;
    private int[] mLayouts;
    private Button mSkipButton;
    private Button mNextButton;
    private PreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferenceManager = new PreferenceManager(IntroActivity.this);
        if (!mPreferenceManager.isIntroSliderPlay()) {
            mPreferenceManager.setIntroSliderPlay(false);
            Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_intro);
        mIntroViewPager = (ViewPager) findViewById(R.id.intro_view_pager);
        mIntroDotsLayout = (LinearLayout) findViewById(R.id.intro_dots_layout);
        mSkipButton = (Button) findViewById(R.id.intro_skip_button);
        mNextButton = (Button) findViewById(R.id.intro_next_button);
        mSkipButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);

        mLayouts = new int[]{
                R.layout.view_intro_slider1, R.layout.view_intro_slider2, R.layout.view_intro_slider3,};

        indicateViewPager(0);
        changeStatusBarColor();

        mIntroViewPagerAdapter = new IntroViewPagerAdapter();
        mIntroViewPager.setAdapter(mIntroViewPagerAdapter);
        mIntroViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intro_skip_button :
                mPreferenceManager.setIntroSliderPlay(false);
                startActivity(new Intent(IntroActivity.this, SplashActivity.class));
                finish();
                break;
            case R.id.intro_next_button :
                int current = getItem(+1);
                if (current < mLayouts.length) {
                    mIntroViewPager.setCurrentItem(current);
                } else {
                    mPreferenceManager.setIntroSliderPlay(false);
                    startActivity(new Intent(IntroActivity.this, SplashActivity.class));
                    finish();
                }
                break;
        }
    }

    private void indicateViewPager(int currentPage) {
        mTextViews = new TextView[mLayouts.length];
        int inActiveColorId = getResources().getColor(R.color.intro_view_pager_indicator_inactive_color);
        int activeColorId = getResources().getColor(R.color.intro_view_pager_indicator_active_color);

        mIntroDotsLayout.removeAllViews();
        for (int i = 0; i < mTextViews.length; i++) {
            mTextViews[i] = new TextView(IntroActivity.this);
            mTextViews[i].setText(Html.fromHtml("&#8226;"));
            mTextViews[i].setTextSize(35);
            mTextViews[i].setTextColor(inActiveColorId);
            mIntroDotsLayout.addView(mTextViews[i]);
        }
        if (mTextViews.length > 0) {
            mTextViews[currentPage].setTextColor(activeColorId);
        }
    }

    private int getItem(int i) {
        return mIntroViewPager.getCurrentItem() + i;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            indicateViewPager(position);

            if (position == mLayouts.length - 1) {
                mNextButton.setText(getString(R.string.slide_start));
                mSkipButton.setVisibility(View.GONE);
            } else {
                mNextButton.setText(getString(R.string.slide_next));
                mSkipButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class IntroViewPagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflater;

        public IntroViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            mLayoutInflater = LayoutInflater.from(getApplicationContext());
            View view = mLayoutInflater.inflate(mLayouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mLayouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            View view = (View) object;
            container.removeView(view);
        }
    }
}
