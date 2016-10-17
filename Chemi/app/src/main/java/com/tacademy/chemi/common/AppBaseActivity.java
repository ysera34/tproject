package com.tacademy.chemi.common;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by yoon on 2016. 10. 16..
 */
public class AppBaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
        super.attachBaseContext(TypekitContextWrapper.wrap(base));
    }
}
