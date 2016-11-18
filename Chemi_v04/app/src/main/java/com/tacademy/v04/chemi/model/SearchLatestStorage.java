package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class SearchLatestStorage {

    private static SearchLatestStorage sSearchStorage;
    private Context mAppContext;

    private ArrayList<Search> mSearches;

    private SearchLatestStorage(Context appContext) {
        mAppContext = appContext;
        mSearches = new ArrayList<>();

        /*
        sample data
         */
        for (int i = 0; i < 8; i++) {
            Search search = new Search();
            search.setRatingNumber(i);
            search.setSearchWord("한글 English " + i);
            search.setVariationValue(new Random().nextInt(10));
            search.setVariationState(i%2 == 0);
            mSearches.add(search);
        }
    }

    public static SearchLatestStorage get(Context context) {
        if (sSearchStorage == null) {
            sSearchStorage = new SearchLatestStorage(context.getApplicationContext());
        }
        return sSearchStorage;
    }

    public ArrayList<Search> getSearches() {
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
}
