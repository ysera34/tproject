package com.tacademy.v04.chemi.view.fragment.product;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.v04.chemi.R;

/**
 * Created by yoon on 2016. 11. 19..
 */

public class NoProductDialogFragment extends DialogFragment {

    public static NoProductDialogFragment newInstance() {

        NoProductDialogFragment fragment = new NoProductDialogFragment();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_no_product_dialog, null);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.product_list_no_exist)
                .setPositiveButton(R.string.return_to_search_activity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }).create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(android.content.DialogInterface dialog,
                                 int keyCode,android.view.KeyEvent event) {
                if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
