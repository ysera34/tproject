package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by yoon on 2016. 12. 3..
 */

public class CustomSearchStorage {

    private static CustomSearchStorage sCustomSearchStorage;
    private Context mAppContext;

    private ArrayList<CustomSearch> mCustomSearches;

    private HashMap<String, Integer> mCategoryMap;

    public CustomSearchStorage(Context context) {
        mAppContext = context;
        mCustomSearches = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            CustomSearch customSearch = new CustomSearch();
            mCustomSearches.add(customSearch);
        }

        mCategoryMap = new HashMap<>();
        mCategoryMap.put("세탁세제", 52);
        mCategoryMap.put("섬유유연제", 51);
        mCategoryMap.put("표백제", 53);
        mCategoryMap.put("식기세척세제", 11);
        mCategoryMap.put("주방세정제", 12);

        mCategoryMap.put("헤어", 32);
        mCategoryMap.put("바디 / 세안", 31);
        mCategoryMap.put("욕실 세정제", 33);
        mCategoryMap.put("섬유 탈취제", 62);
        mCategoryMap.put("방향제", 61);

        mCategoryMap.put("유아용 기저귀", 21);
        mCategoryMap.put("유아용 물티슈", 22);
        mCategoryMap.put("생리대", 41);
        mCategoryMap.put("여성 청결제", 42);
        mCategoryMap.put("성인용 기저귀", 43);
        mCategoryMap.put("물티슈", 72);
        mCategoryMap.put("렌즈 세척액", 71);
        mCategoryMap.put("콘돔", 73);

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

    public int getCategoryId(String categoryName) {
        if (categoryName != "카테고리를 선택해주세요") {
            return mCategoryMap.get(categoryName);
        }
        return -1;
    }
}
