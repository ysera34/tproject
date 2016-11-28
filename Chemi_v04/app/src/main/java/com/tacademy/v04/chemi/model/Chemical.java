package com.tacademy.v04.chemi.model;

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

    public int getHazard() {
        return mHazard;
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
