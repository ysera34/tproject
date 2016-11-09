package com.tacademy.v01.chemi.common;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by yoon on 2016. 11. 9..
 */

public class AppBaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
