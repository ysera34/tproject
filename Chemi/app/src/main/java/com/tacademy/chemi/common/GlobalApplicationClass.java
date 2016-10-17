package com.tacademy.chemi.common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by yoon on 2016. 10. 16..
 */
public class GlobalApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumSquareOTFR.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumSquareOTFB.otf"));
    }
}
