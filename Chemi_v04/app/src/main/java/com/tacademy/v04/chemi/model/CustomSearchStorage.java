package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 12. 3..
 */

public class CustomSearchStorage {

    private static CustomSearchStorage sCustomSearchStorage;
    private Context mAppContext;

    private ArrayList<CustomSearch> mCustomSearches;

    public CustomSearchStorage(Context context) {
        mAppContext = context;
        mCustomSearches = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            CustomSearch customSearch = new CustomSearch();
            mCustomSearches.add(customSearch);
        }
    }

    public static CustomSearchStorage get(Context context) {
        if (sCustomSearchStorage == null) {
            sCustomSearchStorage = new CustomSearchStorage(context.getApplicationContext());
        }
        return sCustomSearchStorage;
    }

    public ArrayList<CustomSearch> getCustomSearches() {
        return mCustomSearches;
    }

    public void setCustomSearches(ArrayList<CustomSearch> customSearches) {
        mCustomSearches = customSearches;
    }

    public CustomSearch getCustomSearch(UUID id) {
        for (CustomSearch customSearch : mCustomSearches) {
            if (customSearch.getId().equals(id)) {
                return customSearch;
            }
        }
        return null;
    }
}
