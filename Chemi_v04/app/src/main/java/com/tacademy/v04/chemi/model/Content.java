package com.tacademy.v04.chemi.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 23..
 */

public class Content {

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Date mPublishedDate;
    private int mContentImageId;
    private int mContentTypeImageId;
    private int mContentType;

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

    public int getContentImageId() {
        return mContentImageId;
    }

    public void setContentImageId(int contentImageId) {
        mContentImageId = contentImageId;
    }

    public int getContentTypeImageId() {
        return mContentTypeImageId;
    }

    public void setContentTypeImageId(int contentTypeImageId) {
        mContentTypeImageId = contentTypeImageId;
    }

    public int getContentType() {
        return mContentType;
    }

    public void setContentType(int contentType) {
        mContentType = contentType;
    }

    @Override
    public String toString() {
        return "Content{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mPublishedDate=" + mPublishedDate +
                ", mContentImageId=" + mContentImageId +
                ", mContentTypeImageId=" + mContentTypeImageId +
                ", mContentType=" + mContentType +
                '}';
    }
}
