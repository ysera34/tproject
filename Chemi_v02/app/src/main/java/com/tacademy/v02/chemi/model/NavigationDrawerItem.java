package com.tacademy.v02.chemi.model;

/**
 * Created by yoon on 2016. 11. 12..
 */

public class NavigationDrawerItem {

    private boolean showNotify;
    private String title;

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
