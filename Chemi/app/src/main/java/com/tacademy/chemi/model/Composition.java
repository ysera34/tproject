package com.tacademy.chemi.model;

import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 20..
 */
public class Composition {

    private UUID mId;
    private String mTitle;

    public Composition() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
