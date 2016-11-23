package com.tacademy.v04.chemi.model;

import android.content.Context;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 23..
 */

public class ContentMainStorage {

    private static ContentMainStorage sContentMainStorage;
    private Context mAppContext;

    private ArrayList<Content> mContents;

    private ContentMainStorage(Context context) {
        mAppContext = context;
        mContents = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Content content = new Content();
            switch (i%3) {
                case 0 :
                    content.setImageId(R.drawable.main_content_sample01);
                    break;
                case 1 :
                    content.setImageId(R.drawable.main_content_sample02);
                    break;
                case 2 :
                    content.setImageId(R.drawable.main_content_sample03);
                    break;
            }
            mContents.add(content);
        }
    }

    public static ContentMainStorage get(Context context) {
        if (sContentMainStorage == null) {
            sContentMainStorage = new ContentMainStorage(context.getApplicationContext());
        }
        return sContentMainStorage;
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
