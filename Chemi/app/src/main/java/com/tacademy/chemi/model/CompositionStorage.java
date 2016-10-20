package com.tacademy.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 20..
 */
public class CompositionStorage {

    private static CompositionStorage sCompositionStorage;

    private List<Composition> mCompositions;

    public static CompositionStorage get(Context context) {
        if (sCompositionStorage == null) {
            sCompositionStorage = new CompositionStorage(context);
        }
        return sCompositionStorage;
    }

    public CompositionStorage(Context context) {
        mCompositions = new  ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Composition composition = new Composition();
            composition.setTitle("composition title #" + i);
            mCompositions.add(composition);
        }
    }

    public List<Composition> getCompositions() {
        return mCompositions;
    }

    public Composition getComposition(UUID id) {
        for (Composition composition : mCompositions) {
            if (composition.getId().equals(id)) {
                return composition;
            }
        }
        return null;
    }
}
