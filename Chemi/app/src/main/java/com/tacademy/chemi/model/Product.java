package com.tacademy.chemi.model;

import java.util.UUID;

/**
 * Created by yoon on 2016. 10. 16..
 */
public class Product {

    private UUID mId;
    private String mTitle;

    public Product() {
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
