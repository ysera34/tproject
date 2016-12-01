package com.tacademy.v04.chemi.common.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yoon on 2016. 12. 2..
 */

public class PreferenceManager {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    int PRIVATE_MODE = 0;

    private static final String PREFERENCE_NAME = "preference";
    private static final String INTRO_SLIDER_PLAY = "introsliderplay";

    public PreferenceManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void setIntroSliderPlay(boolean isPlayed) {
        mEditor.putBoolean(INTRO_SLIDER_PLAY, isPlayed);
        mEditor.commit();
    }

    public boolean isIntroSliderPlay() {
        return mSharedPreferences.getBoolean(INTRO_SLIDER_PLAY, true);
    }
}
