package com.tacademy.v04.chemi.model;

import android.content.Context;
import android.util.Log;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 12. 8..
 */

public class EffectStorage {

    private static EffectStorage sEffectStorage;
    private Context mAppContext;

    private ArrayList<Effect> mEffects;
    private String[] mConstitutionNameArr;
    private int[] mPrecautionImageIdArr;
    private String mConstitutionNullDescription;

    private EffectStorage(Context context) {
        mAppContext = context;
        mEffects = new ArrayList<>();

        mConstitutionNameArr = mAppContext.getResources().getStringArray(R.array.conid_name_array);
        mPrecautionImageIdArr = new int[]{
            R.drawable.ic_chemical_dialog_precaution_1_off, R.drawable.ic_chemical_dialog_precaution_1_on,
            R.drawable.ic_chemical_dialog_precaution_2_off, R.drawable.ic_chemical_dialog_precaution_2_on,
            R.drawable.ic_chemical_dialog_precaution_3_off, R.drawable.ic_chemical_dialog_precaution_3_on,
            R.drawable.ic_chemical_dialog_precaution_4_off, R.drawable.ic_chemical_dialog_precaution_4_on,
            R.drawable.ic_chemical_dialog_precaution_5_off, R.drawable.ic_chemical_dialog_precaution_5_on,
            R.drawable.ic_chemical_dialog_precaution_6_off, R.drawable.ic_chemical_dialog_precaution_6_on,
        };
        mConstitutionNullDescription = mAppContext.getResources().getString(R.string.null_effect_desc);
    }

    public static EffectStorage get(Context context) {
        if(sEffectStorage == null) {
            sEffectStorage = new EffectStorage(context.getApplicationContext());
        }
        return sEffectStorage;
    }

    public ArrayList<Effect> getEffects() {
        return mEffects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        mEffects = effects;
    }

    // index, constitutionId from 1
    public void setEffect(int constitutionId, String description) {

        Effect effect = new Effect();
        effect.setConstitutionId(constitutionId);
        effect.setConstitutionName(mConstitutionNameArr[constitutionId - 1]);
        if (description.equals("0")) {
            effect.setDescription(effect.getConstitutionName() + " " + mConstitutionNullDescription);
            effect.setImageId(mPrecautionImageIdArr[(constitutionId * 2) - 2]);
            effect.setFontColorId(R.color.chemical_dialog_precaution_off_name_font_color);
        } else {
            effect.setDescription(description);
            effect.setImageId(mPrecautionImageIdArr[(constitutionId * 2) - 1]);
            effect.setFontColorId(R.color.chemical_dialog_precaution_on_name_font_color);
//            switch (constitutionId) {
//                case 1 :
//                    break;
//                case 2 :
//                    break;
//                case 3 :
//                    break;
//                case 4 :
//                    break;
//                case 5 :
//                    break;
//                case 6 :
//                    break;
//            }
        }
//        mEffects.set(constitutionId-1, effect);
        mEffects.add(effect);
        Log.i("setEffects", effect.toString());
//        mEffects.indexOf()
    }

}
