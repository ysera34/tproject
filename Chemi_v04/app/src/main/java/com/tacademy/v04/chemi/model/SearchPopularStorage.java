package com.tacademy.v04.chemi.model;

import android.content.Context;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 18..
 */

public class SearchPopularStorage {

    private static SearchPopularStorage sSearchStorage;
    private Context mAppContext;

    private ArrayList<Search> mSearches;

    private SearchPopularStorage(Context appContext) {
        mAppContext = appContext;
        mSearches = new ArrayList<>();

        /*
        sample data
         */
        String[] searchWordArr = mAppContext.getResources().getStringArray(R.array.search_popular_word_array);

        for (int i = 0; i < 8; i++) {
            Search search = new Search();
            search.setRatingNumber(i);
            search.setSearchWord(searchWordArr[i]);
            search.setVariationValue(new Random().nextInt(9) + 1);
            search.setVariationState(i%2 == 0);
            mSearches.add(search);
        }

    }

    public static SearchPopularStorage get(Context context) {
        if (sSearchStorage == null) {
            sSearchStorage = new SearchPopularStorage(context.getApplicationContext());
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
