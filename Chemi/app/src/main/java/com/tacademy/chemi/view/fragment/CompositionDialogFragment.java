package com.tacademy.chemi.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.chemi.R;
import com.tacademy.chemi.model.Composition;
import com.tacademy.chemi.model.CompositionStorage;

import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 20..
 */
public class CompositionDialogFragment extends DialogFragment {

    private static final String ARG_COMPOSITION = "composition";

    private Composition mComposition;
    private TextView mTitleTextView;

    public static CompositionDialogFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COMPOSITION, id);

        CompositionDialogFragment fragment = new CompositionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        UUID id = (UUID) getArguments().getSerializable(ARG_COMPOSITION);
        mComposition = CompositionStorage.get(getActivity()).getComposition(id);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_composition_dialog, null);

        mTitleTextView = (TextView) view.findViewById(R.id.composition_dialog_title);
        mTitleTextView.setText(mComposition.getTitle());

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("composition details")
                .setPositiveButton(android.R.string.ok, null)
                .setNeutralButton("수정 요청", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "수정 요청 하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
    }
}
