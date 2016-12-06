package com.tacademy.v04.chemi.model;

import java.util.UUID;

/**
 * Created by yoon on 2016. 12. 3..
 */

public class User {

    private UUID mId;
    private int mUserId;
    private String mEmail;
    private String nickname;
    private String mPassword;
    private String mPlatform;
    private String mAccessToken;
    private String mPushToken;
    private boolean mEnablePush;
    private int mBirthYear;
    private String mGender;
    private byte mChild;
    private byte mChildBirthYear;
    private String mImagePath;

    public User() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getPushToken() {
        return mPushToken;
    }

    public void setPushToken(String pushToken) {
        mPushToken = pushToken;
    }

    public boolean isEnablePush() {
        return mEnablePush;
    }

    public void setEnablePush(boolean enablePush) {
        mEnablePush = enablePush;
    }

    public int getBirthYear() {
        return mBirthYear;
    }

    public void setBirthYear(int birthYear) {
        mBirthYear = birthYear;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public byte getChild() {
        return mChild;
    }

    public void setChild(byte child) {
        mChild = child;
    }

    public byte getChildBirthYear() {
        return mChildBirthYear;
    }

    public void setChildBirthYear(byte childBirthYear) {
        mChildBirthYear = childBirthYear;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }
}
