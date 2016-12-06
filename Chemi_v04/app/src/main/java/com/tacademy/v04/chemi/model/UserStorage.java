package com.tacademy.v04.chemi.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 12. 6..
 */

public class UserStorage {

    private static UserStorage sUserStorage;
    private Context mAppContext;

    private ArrayList<User> mUsers;

    public UserStorage(Context appContext) {
        mAppContext = appContext;
        mUsers = new ArrayList<>();
    }

    public static UserStorage get(Context context) {
        if (sUserStorage == null) {
            sUserStorage = new UserStorage(context.getApplicationContext());
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return mUsers;
    }

    public void setUsers(ArrayList<User> users) {
        mUsers = users;
    }

    public User getUser(UUID id) {
        for (User user : mUsers) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

}
