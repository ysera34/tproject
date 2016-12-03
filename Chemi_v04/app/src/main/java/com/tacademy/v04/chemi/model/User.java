package com.tacademy.v04.chemi.model;

import java.util.UUID;

/**
 * Created by yoon on 2016. 12. 3..
 */

public class User {

    private UUID mId;
    private int mUserId;
    private String nickname;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
