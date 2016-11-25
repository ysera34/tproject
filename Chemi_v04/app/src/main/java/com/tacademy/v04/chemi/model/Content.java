package com.tacademy.v04.chemi.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 23..
 */

public class Content {

    private UUID mId;
    private int mImageId;
    private String mTitle;
    private String mDescription;
    private Date mPublishedDate;

    public Content() {
        mId = UUID.randomUUID();
        mPublishedDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        mPublishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "Content{" +
                "mId=" + mId +
                ", mImageId=" + mImageId +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mPublishedDate=" + mPublishedDate +
                '}';
    }
}
