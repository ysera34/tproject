package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.v04.chemi.R;

/**
 * Created by yoon on 2016. 11. 20..
 * flows from Navigation Drawer menu Button
 * Search Log Custom Search, latest Search Log
 */

public class SearchLogFragment extends Fragment {

    public SearchLogFragment() {
    }

    public static SearchLogFragment newInstance() {

        SearchLogFragment fragment = new SearchLogFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_log, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }

}
