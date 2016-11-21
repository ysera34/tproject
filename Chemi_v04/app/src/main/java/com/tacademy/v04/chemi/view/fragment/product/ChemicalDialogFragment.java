package com.tacademy.v04.chemi.view.fragment.product;

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

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Chemical;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ChemicalDialogFragment extends DialogFragment {

    private static final String ARG_CHEMICAL = "chemical_id";

    private Chemical mChemical;
    private TextView mTitleTextView;

    public static ChemicalDialogFragment newInstance() {

        ChemicalDialogFragment fragment = new ChemicalDialogFragment();
        return fragment;
    }

    public static ChemicalDialogFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHEMICAL, id);

        ChemicalDialogFragment fragment = new ChemicalDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//        UUID id = (UUID) getArguments().getSerializable(ARG_CHEMICAL);
//        mChemical = ChemicalStorage.get(getActivity()).getChemical(id);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_chemical_dialog, null);

        mTitleTextView = (TextView) view.findViewById(R.id.chemical_dialog_chemical_title_ko);
//        mTitleTextView.setText(mChemical.getNameKo());

        return new AlertDialog.Builder(getActivity())
                .setView(view)
//                .setTitle("composition details")
                .setPositiveButton(android.R.string.ok, null)
                .setNeutralButton("수정 요청", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "수정 요청 하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }
}
