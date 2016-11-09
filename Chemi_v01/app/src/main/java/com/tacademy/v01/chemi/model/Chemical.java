package com.tacademy.v01.chemi.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 6..
 */
public class Chemical {

    private UUID mId;
    private int mChemicalId;
    private String mNameKo;
    private String mNameEn;
    private String mAbbr;
    private String mMix;
    private int mHazard;
    private ArrayList<Symptom> mSymptoms;

    public Chemical() {
        mId = UUID.randomUUID();
        mSymptoms = new ArrayList<>();
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

    public ArrayList<Symptom> getSymptoms() {
        return mSymptoms;
    }

    public void setSymptoms(ArrayList<Symptom> symptoms) {
        mSymptoms = symptoms;
    }

    class Symptom {

        private int mType;
        private int mImageId;
        private String mContent;

        public int getType() {
            return mType;
        }

        public void setType(int type) {
            mType = type;
        }

        public int getImageId() {
            return mImageId;
        }

        public void setImageId(int imageId) {
            mImageId = imageId;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }
    }

}
