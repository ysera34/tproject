package com.tacademy.v04.chemi.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.tacademy.v04.chemi.R;

/**
 * Created by yoon on 2016. 12. 6..
 */

public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_custom_dialog);
    }
}
