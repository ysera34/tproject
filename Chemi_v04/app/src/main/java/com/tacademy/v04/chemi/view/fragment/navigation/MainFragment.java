package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.v04.chemi.R;

import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class MainFragment extends Fragment {

    public MainFragment() {
    }

    public static MainFragment newInstance() {

        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public static MainFragment newInstance(UUID memberId) {
        Bundle args = new Bundle();
//        args.putSerializable(ARG_MEMBER_ID, memberId);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
