package com.tacademy.v04.chemi.model;

import android.content.Context;
import android.util.Log;

import com.tacademy.v04.chemi.common.util.manager.PreferenceManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class SearchLatestStorage {

    private static final int MAX_NUMBER_OF_SEARCH_WORDS = 8;

    private static SearchLatestStorage sSearchStorage;
    private Context mAppContext;

    private PreferenceManager mPreferenceManager;

    private ArrayList<Search> mSearches;

    private SearchLatestStorage(Context appContext) {
        mAppContext = appContext;
        mSearches = new ArrayList<>();
        mPreferenceManager = new PreferenceManager(mAppContext.getApplicationContext());
    }

    public static SearchLatestStorage get(Context context) {
        if (sSearchStorage == null) {
            sSearchStorage = new SearchLatestStorage(context.getApplicationContext());
        }
        return sSearchStorage;
    }

    public ArrayList<Search> getSearches() {

        ArrayList<String> searchLatestWords = getSearchLatestWordsPreference();
        Log.w("searchLatestWords", "size" + searchLatestWords.size());
        if (searchLatestWords != null) {
            for (int i = 0; i < searchLatestWords.size(); i++) {
                Search search = new Search();
                search.setSearchWord(searchLatestWords.get(i));
                mSearches.add(search);
            }
        }

        return mSearches;
    }

    public Search getSearch(UUID id) {
        for (Search search : mSearches) {
            if (search.getId().equals(id)) {
                return search;
            }
        }
        return null;
    }

    public ArrayList<Search> removeSearch(Search search) {
        for (Search s : mSearches) {
            if (s.getId().equals(search.getId())) {
                mSearches.remove(search);
                break;
            }
        }
        return mSearches;
    }

    public ArrayList<Search> removeSearch(Search search, int position) {
        for (Search s : mSearches) {
            if (s.getId().equals(search.getId())) {
                mSearches.remove(search);
                removeSearchLatestWordsPreference(position);
                break;
            }
        }
        return mSearches;
    }

    public ArrayList<String> getSearchLatestWordsPreference() {
        return mPreferenceManager.getSearchLatestWordsPreference(MAX_NUMBER_OF_SEARCH_WORDS);
    }

    public void setSearchLatestWordsPreference(String word, int index) {
        mPreferenceManager.setSearchLatestWordsPreference(word, index);
    }

    public void removeSearchLatestWordsPreference(int index) {
        mPreferenceManager.removeSearchLatestWordsPreference(index);
    }

    public int getSearchLatestWordsPreferenceIndex() {
        return mPreferenceManager.getSearchLatestWordsPreferenceIndex();
    }



}
