package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 23..
 */

public class ContentStorage {

    private static ContentStorage sContentStorage;
    private Context mAppContext;

    private ArrayList<Content> mContents;

    private ContentStorage(Context context) {
        mAppContext = context;
        mContents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Content content = new Content();
            mContents.add(content);
        }
    }

    public static ContentStorage get(Context context) {
        if (sContentStorage == null) {
            sContentStorage = new ContentStorage(context.getApplicationContext());
        }
        return sContentStorage;
    }

    public ArrayList<Content> getContents() {
        return mContents;
    }

    public Content getContent(UUID id) {
        for (Content content : mContents) {
            if (content.getId().equals(id)) {
                return content;
            }
        }
        return null;
    }
}
