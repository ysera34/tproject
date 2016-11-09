package com.tacademy.v01.chemi.common;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by yoon on 2016. 11. 9..
 */

public class GlobalApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NotoSansCJKsc-Regular.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NotoSansCJKtc-Bold.otf"))
                .add("medium", Typekit.createFromAsset(this, "fonts/NotoSansCJKsc-Medium.otf"));
    }
}
