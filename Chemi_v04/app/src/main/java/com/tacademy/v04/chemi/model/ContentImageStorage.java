package com.tacademy.v04.chemi.model;

import android.content.Context;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoon on 2016. 12. 9..
 */

public class ContentImageStorage {

    private static ContentImageStorage sContentImageStorage;
    private Context mAppContext;

    private HashMap<Integer, ArrayList<Integer>> mContentImageResIdMap;

    private ContentImageStorage(Context context) {
        mAppContext = context;
        mContentImageResIdMap = new HashMap<>();

        setupContentImageStorage();
    }

    public static ContentImageStorage get(Context context) {
        if (sContentImageStorage == null) {
            sContentImageStorage = new ContentImageStorage(context.getApplicationContext());
        }
        return sContentImageStorage;
    }

    public HashMap<Integer, ArrayList<Integer>> getContentImageResIdMap() {
        return mContentImageResIdMap;
    }

    public ArrayList<Integer> getContentImageResIds(int contentType) {
        return mContentImageResIdMap.get(contentType);
    }

    private void setupContentImageStorage() {

        ArrayList<Integer> mContentImageResIds11 = new ArrayList<>();
        mContentImageResIds11.add(R.drawable.content_image_1101);
        mContentImageResIds11.add(R.drawable.content_image_1102);
        mContentImageResIds11.add(R.drawable.content_image_1103);
        mContentImageResIds11.add(R.drawable.content_image_1104);
        mContentImageResIds11.add(R.drawable.content_image_1105);
        mContentImageResIds11.add(R.drawable.content_image_1106);
        mContentImageResIdMap.put(11, mContentImageResIds11);

        ArrayList<Integer> mContentImageResIds12 = new ArrayList<>();
        mContentImageResIds12.add(R.drawable.content_image_1201);
        mContentImageResIds12.add(R.drawable.content_image_1202);
        mContentImageResIds12.add(R.drawable.content_image_1203);
        mContentImageResIds12.add(R.drawable.content_image_1204);
        mContentImageResIds12.add(R.drawable.content_image_1205);
        mContentImageResIds12.add(R.drawable.content_image_1206);
        mContentImageResIds12.add(R.drawable.content_image_1207);
        mContentImageResIds12.add(R.drawable.content_image_1208);
        mContentImageResIds12.add(R.drawable.content_image_1209);
        mContentImageResIds12.add(R.drawable.content_image_1210);
        mContentImageResIds12.add(R.drawable.content_image_1211);
        mContentImageResIds12.add(R.drawable.content_image_1212);
        mContentImageResIds12.add(R.drawable.content_image_1213);
        mContentImageResIds12.add(R.drawable.content_image_1214);
        mContentImageResIds12.add(R.drawable.content_image_1215);
        mContentImageResIdMap.put(12, mContentImageResIds12);

        ArrayList<Integer> mContentImageResIds13 = new ArrayList<>();
        mContentImageResIds13.add(R.drawable.content_image_1301);
        mContentImageResIds13.add(R.drawable.content_image_1302);
        mContentImageResIds13.add(R.drawable.content_image_1303);
        mContentImageResIds13.add(R.drawable.content_image_1304);
        mContentImageResIds13.add(R.drawable.content_image_1305);
        mContentImageResIds13.add(R.drawable.content_image_1306);
        mContentImageResIds13.add(R.drawable.content_image_1307);
        mContentImageResIds13.add(R.drawable.content_image_1308);
        mContentImageResIdMap.put(13, mContentImageResIds13);

        ArrayList<Integer> mContentImageResIds21 = new ArrayList<>();
        mContentImageResIds21.add(R.drawable.content_image_2101);
        mContentImageResIds21.add(R.drawable.content_image_2102);
        mContentImageResIds21.add(R.drawable.content_image_2103);
        mContentImageResIds21.add(R.drawable.content_image_2104);
        mContentImageResIds21.add(R.drawable.content_image_2105);
        mContentImageResIds21.add(R.drawable.content_image_2106);
        mContentImageResIds21.add(R.drawable.content_image_2107);
        mContentImageResIds21.add(R.drawable.content_image_2108);
        mContentImageResIds21.add(R.drawable.content_image_2109);
        mContentImageResIds21.add(R.drawable.content_image_2110);
        mContentImageResIds21.add(R.drawable.content_image_2111);
        mContentImageResIds21.add(R.drawable.content_image_2112);
        mContentImageResIds21.add(R.drawable.content_image_2113);
        mContentImageResIds21.add(R.drawable.content_image_2114);
        mContentImageResIdMap.put(21, mContentImageResIds21);

        ArrayList<Integer> mContentImageResIds22 = new ArrayList<>();
        mContentImageResIds22.add(R.drawable.content_image_2201);
        mContentImageResIds22.add(R.drawable.content_image_2202);
        mContentImageResIds22.add(R.drawable.content_image_2203);
        mContentImageResIds22.add(R.drawable.content_image_2204);
        mContentImageResIds22.add(R.drawable.content_image_2205);
        mContentImageResIds22.add(R.drawable.content_image_2206);
        mContentImageResIds22.add(R.drawable.content_image_2207);
        mContentImageResIdMap.put(22, mContentImageResIds22);

        ArrayList<Integer> mContentImageResIds23 = new ArrayList<>();
        mContentImageResIds23.add(R.drawable.content_image_2301);
        mContentImageResIds23.add(R.drawable.content_image_2302);
        mContentImageResIds23.add(R.drawable.content_image_2303);
        mContentImageResIds23.add(R.drawable.content_image_2304);
        mContentImageResIds23.add(R.drawable.content_image_2305);
        mContentImageResIds23.add(R.drawable.content_image_2306);
        mContentImageResIds23.add(R.drawable.content_image_2307);
        mContentImageResIds23.add(R.drawable.content_image_2308);
        mContentImageResIds23.add(R.drawable.content_image_2309);
        mContentImageResIds23.add(R.drawable.content_image_2310);
        mContentImageResIds23.add(R.drawable.content_image_2311);
        mContentImageResIds23.add(R.drawable.content_image_2312);
        mContentImageResIds23.add(R.drawable.content_image_2313);
        mContentImageResIdMap.put(23, mContentImageResIds23);

        ArrayList<Integer> mContentImageResIds31 = new ArrayList<>();
        mContentImageResIds31.add(R.drawable.content_image_3101);
        mContentImageResIds31.add(R.drawable.content_image_3102);
        mContentImageResIds31.add(R.drawable.content_image_3103);
        mContentImageResIds31.add(R.drawable.content_image_3104);
        mContentImageResIds31.add(R.drawable.content_image_3105);
        mContentImageResIds31.add(R.drawable.content_image_3106);
        mContentImageResIdMap.put(31, mContentImageResIds31);

        ArrayList<Integer> mContentImageResIds32 = new ArrayList<>();
        mContentImageResIds32.add(R.drawable.content_image_3201);
        mContentImageResIds32.add(R.drawable.content_image_3202);
        mContentImageResIds32.add(R.drawable.content_image_3203);
        mContentImageResIds32.add(R.drawable.content_image_3204);
        mContentImageResIds32.add(R.drawable.content_image_3205);
        mContentImageResIds32.add(R.drawable.content_image_3206);
        mContentImageResIds32.add(R.drawable.content_image_3207);
        mContentImageResIds32.add(R.drawable.content_image_3208);
        mContentImageResIds32.add(R.drawable.content_image_3209);
        mContentImageResIdMap.put(32, mContentImageResIds32);

        ArrayList<Integer> mContentImageResIds33 = new ArrayList<>();
        mContentImageResIds33.add(R.drawable.content_image_3301);
        mContentImageResIds33.add(R.drawable.content_image_3302);
        mContentImageResIds33.add(R.drawable.content_image_3303);
        mContentImageResIds33.add(R.drawable.content_image_3304);
        mContentImageResIds33.add(R.drawable.content_image_3305);
        mContentImageResIds33.add(R.drawable.content_image_3306);
        mContentImageResIds33.add(R.drawable.content_image_3307);
        mContentImageResIds33.add(R.drawable.content_image_3308);
        mContentImageResIdMap.put(33, mContentImageResIds33);

    }
}
