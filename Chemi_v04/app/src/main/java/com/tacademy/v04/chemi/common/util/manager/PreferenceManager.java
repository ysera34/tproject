package com.tacademy.v04.chemi.common.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 12. 2..
 */

public class PreferenceManager {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    int MODE_PRIVATE = 0;

    private static final String PREFERENCE_NAME = "preference";
    private static final String INTRO_SLIDER_PLAY = "intro_slider_play";
    private static final String SEARCH_LATEST_WORD = "search_latest_word_";

    public PreferenceManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void setIntroSliderPlay(boolean isPlayed) {
        mEditor.putBoolean(INTRO_SLIDER_PLAY, isPlayed);
        mEditor.commit();
    }

    public boolean isIntroSliderPlay() {
        return mSharedPreferences.getBoolean(INTRO_SLIDER_PLAY, true);
    }

    public ArrayList<String> getSearchLatestWordsPreference(int index) {
        ArrayList<String> searchLatestWords = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            String str = mSharedPreferences.getString(SEARCH_LATEST_WORD + i, null);
            if (str != null) {
                searchLatestWords.add(str);
            }
        }
        return searchLatestWords;
    }

    public void setSearchLatestWordsPreference(String word, int index) {
        mEditor.putString(SEARCH_LATEST_WORD + index, word);
        mEditor.commit();
    }

    public void removeSearchLatestWordsPreference(int index) {
        mEditor.remove(SEARCH_LATEST_WORD + index);
        mEditor.commit();
    }

    public void removeAllSearchLatestWordsPreference() {
        for (int i = 0; i < 8; i++) {
            mEditor.remove(SEARCH_LATEST_WORD + i);
            mEditor.commit();
        }
    }

    public int getSearchLatestWordsPreferenceIndex() {

        for (int i = 0; i < 8; i++) {
            String str = mSharedPreferences.getString(SEARCH_LATEST_WORD + i, null);
            if (str == null) {
                break;
            }
            return i;
        }
        return 0;
    }
}
