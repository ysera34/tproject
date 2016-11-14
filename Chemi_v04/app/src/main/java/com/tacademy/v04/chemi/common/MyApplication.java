package com.tacademy.v04.chemi.common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.ttf"));
    }
}
