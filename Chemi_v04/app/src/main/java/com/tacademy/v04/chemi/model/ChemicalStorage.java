package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class ChemicalStorage {

    private static ChemicalStorage sChemicalStorage;
    private Context mAppContext;

    private EffectStorage mEffectStorage;

    private ArrayList<Chemical> mChemicals;

    private ChemicalStorage(Context appContext) {
        mAppContext = appContext;
        mChemicals = new ArrayList<>();

        mEffectStorage = EffectStorage.get(mAppContext);

    }

    public static ChemicalStorage get(Context context) {
        if (sChemicalStorage == null) {
            sChemicalStorage = new ChemicalStorage(context.getApplicationContext());
        }
        return sChemicalStorage;
    }

    public ArrayList<Chemical> getChemicals() {
        return mChemicals;
    }

    public void setChemicals(ArrayList<Chemical> chemicals) {
        mChemicals = chemicals;
    }

    public Chemical getChemical(UUID id) {
        for (Chemical chemical : mChemicals) {
            if (chemical.getId().equals(id)) {
                return chemical;
            }
        }
        return null;
    }

    public void setChemical(Chemical chemical) {

        ArrayList<String> constitutions = chemical.getConstitutions();

        for (int i = 1; i <= 6; i++) {
            mEffectStorage.setEffect(i, constitutions.get(i - 1));
        }
        chemical.setEffects(mEffectStorage.getEffects());

        for (Chemical c : mChemicals) {
            if (c.getId().equals(chemical.getId())) {
                mChemicals.set(mChemicals.indexOf(chemical), chemical);
            }
        }
    }
}
