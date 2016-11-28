package com.tacademy.v04.chemi.model;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 15..
 */

public class Chemical {

    private UUID mId;
    private int mChemicalId;
    private String mNameKo;
    private String mNameEn;
    private String mKeyword;
    private String mAbbr;
    private String mMix;
    private int mHazard;
    private ArrayList<Effect> mEffects;

    public Chemical() {
        mId = UUID.randomUUID();
        mEffects = new ArrayList<>();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getChemicalId() {
        return mChemicalId;
    }

    public void setChemicalId(int chemicalId) {
        mChemicalId = chemicalId;
    }

    public String getNameKo() {
        return mNameKo;
    }

    public void setNameKo(String nameKo) {
        mNameKo = nameKo;
    }

    public String getNameEn() {
        return mNameEn;
    }

    public void setNameEn(String nameEn) {
        mNameEn = nameEn;
    }

    public String getKeyword() {
        return mKeyword;
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    public int getKeywordFontColorResId() {
        int fontColorResId = 0;
        if (mHazard == 0) {
            fontColorResId = R.color.chemical_composition_dangerous_status4_color;
        } else if (mHazard >= 1 && mHazard <= 2) {
            fontColorResId = R.color.chemical_composition_dangerous_status1_color;
        } else if (mHazard >= 3 && mHazard <= 6) {
            fontColorResId = R.color.chemical_composition_dangerous_status2_color;
        } else if (mHazard >= 7) {
            fontColorResId = R.color.chemical_composition_dangerous_status3_color;
        }
        return fontColorResId;
    }

    public int getKeywordFontColorResId(int hazard) {
        int fontColorResId = 0;
        if (hazard == 0) {
            fontColorResId = R.color.chemical_composition_dangerous_status4_color;
        } else if (hazard >= 1 && hazard <= 2) {
            fontColorResId = R.color.chemical_composition_dangerous_status1_color;
        } else if (hazard >= 3 && hazard <= 6) {
            fontColorResId = R.color.chemical_composition_dangerous_status2_color;
        } else if (hazard >= 7) {
            fontColorResId = R.color.chemical_composition_dangerous_status3_color;
        }
        return fontColorResId;
    }

    public String getAbbr() {
        return mAbbr;
    }

    public void setAbbr(String abbr) {
        mAbbr = abbr;
    }

    public String getMix() {
        return mMix;
    }

    public void setMix(String mix) {
        mMix = mix;
    }

    public int[] getHazard() {
        int[] hazards = new int[2];
        hazards[0] = mHazard;
        if (mHazard == 0) {
            hazards[1] = R.drawable.ic_chemical_dangerous_indicator4;
        } else if (mHazard >= 1 && mHazard <= 2) {
            hazards[1] = R.drawable.ic_chemical_dangerous_indicator1;
        } else if (mHazard >= 3 && mHazard <= 6) {
            hazards[1] = R.drawable.ic_chemical_dangerous_indicator2;
        } else if (mHazard >= 7) {
            hazards[1] = R.drawable.ic_chemical_dangerous_indicator3;
        }
        return hazards;
    }

    public void setHazard(int hazard) {
        mHazard = hazard;
    }

    public ArrayList<Effect> getEffects() {
        return mEffects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        mEffects = effects;
    }

    public String toStringId() {
        return "Chemical{" +
                "mId=" + mId +
                ", mChemicalId=" + mChemicalId +
                '}';
    }

    @Override
    public String toString() {
        return "Chemical{" +
                "mId=" + mId +
                ", mChemicalId=" + mChemicalId +
                ", mNameKo='" + mNameKo + '\'' +
                ", mNameEn='" + mNameEn + '\'' +
                ", mKeyword='" + mKeyword + '\'' +
                ", mAbbr='" + mAbbr + '\'' +
                ", mMix='" + mMix + '\'' +
                ", mHazard=" + mHazard +
                ", mEffects=" + mEffects.toString() +
                '}';
    }
}
