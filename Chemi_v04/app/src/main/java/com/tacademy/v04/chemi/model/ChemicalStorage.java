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

    private ArrayList<Chemical> mChemicals;

    private ChemicalStorage(Context appContext) {
        mAppContext = appContext;
        mChemicals = new ArrayList<>();

        /*
        sample data
         */
        for (int i = 0; i < 30; i++) {
            Chemical chemical = new Chemical();
            chemical.setNameKo("** chemical name **" + i);
            mChemicals.add(chemical);
        }
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

    public Chemical getChemical(UUID id) {
        for (Chemical chemical : mChemicals) {
            if (chemical.getId().equals(id)) {
                return chemical;
            }
        }
        return null;
    }
}
