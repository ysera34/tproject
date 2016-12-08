package com.tacademy.v04.chemi.view.fragment.product;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Chemical;
import com.tacademy.v04.chemi.model.ChemicalStorage;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ChemicalDialogFragment extends DialogFragment {

    private static final String TAG = ChemicalDialogFragment.class.getSimpleName();

    private static final String ARG_CHEMICAL = "chemical_id";

    private AlertDialog.Builder mBuilder;

    private Chemical mChemical;
    private TextView mChemicalDialogTitleKOTextView;
    private TextView mChemicalDialogTitleENTextView;
    private ImageView mChemicalDialogDangerousGradeImageView;
    private TextView mChemicalDialogDangerousGradeTextView;
    private TextView mChemicalDialogMixPurposeTextView;

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
        UUID id = (UUID) getArguments().getSerializable(ARG_CHEMICAL);
        mChemical = ChemicalStorage.get(getActivity()).getChemical(id);

        Log.i(TAG, "onCreateDialog" + mChemical.toString());

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_chemical_dialog, null);

        mChemicalDialogTitleKOTextView = (TextView) view.findViewById(R.id.chemical_dialog_chemical_title_ko);
        mChemicalDialogTitleKOTextView.setSelected(true);
        mChemicalDialogTitleENTextView = (TextView) view.findViewById(R.id.chemical_dialog_chemical_title_eng);
        mChemicalDialogTitleENTextView.setSelected(true);
        mChemicalDialogDangerousGradeImageView = (ImageView) view.findViewById(R.id.chemical_dialog_dangerous_grade_image);
        mChemicalDialogDangerousGradeTextView = (TextView) view.findViewById(R.id.chemical_dialog_dangerous_grade_number_text);
        mChemicalDialogMixPurposeTextView = (TextView) view.findViewById(R.id.chemical_dialog_chemical_mix_purpose);

        mChemicalDialogTitleKOTextView.setText(mChemical.getNameKo());
        mChemicalDialogTitleENTextView.setText(mChemical.getNameEn());
        mChemicalDialogDangerousGradeImageView.setBackgroundResource(mChemical.getHazard()[1]);
        mChemicalDialogDangerousGradeTextView.setText(String.valueOf(mChemical.getHazard()[0]));
        mChemicalDialogMixPurposeTextView.setText(mChemical.getMix());


        mBuilder = new AlertDialog.Builder(getActivity())
                .setView(view)
//                .setTitle("composition details")
                .setPositiveButton(android.R.string.ok, null)
                .setNeutralButton("수정 요청", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "수정 요청 하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_SEND, Uri.fromParts(
                                "mailto", "chemi.helper@gmail.com", null));
//                        intent.setData(Uri.parse("mailto:chemi.helper@gmail.com"));
                        intent.setType("text/plain");
//                        intent.putExtra(Intent.EXTRA_EMAIL, "chemi.helper@gmail.com");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"chemi.helper@gmail.com"});
                        intent.putExtra(Intent.EXTRA_TEXT, getChemicalReport());
                        intent.putExtra(Intent.EXTRA_SUBJECT,
                                getString(R.string.chemical_report_subject));
                        intent = Intent.createChooser(intent, getString(R.string.send_report));
                        startActivity(intent);
                    }
                });

        return mBuilder.create();
    }

    private String getChemicalReport() {

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, new Date()).toString();

        return getString(R.string.chemical_report, mChemical.getNameKo(), dateString);
    }



}
