package com.tacademy.v04.chemi.model;

import java.util.UUID;

/**
 * Created by yoon on 2016. 12. 3..
 */

public class CustomSearch {

    private UUID mId;
    private int mCustomSearchId;

    public CustomSearch() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getCustomSearchId() {
        return mCustomSearchId;
    }

    public void setCustomSearchId(int customSearchId) {
        mCustomSearchId = customSearchId;
    }
}
