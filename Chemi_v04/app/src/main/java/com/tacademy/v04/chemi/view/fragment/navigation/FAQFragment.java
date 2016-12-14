package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.util.adapter.FAQExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoon on 2016. 11. 15..
 * flows from Navigation Drawer menu Button
 */

public class FAQFragment extends Fragment {

    private FAQExpandableListAdapter mFAQExpandableListAdapter;
    private ExpandableListView mFAQExpandableListView;
    private ArrayList<String> mFAQHeaderDataList;
    private HashMap<String, ArrayList<String>> mFAQBodyDataMap;

    public FAQFragment() {
    }

    public static FAQFragment newInstance() {

        FAQFragment fragment = new FAQFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFAQHeaderDataList = new ArrayList<>();
        mFAQBodyDataMap = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        mFAQExpandableListView = (ExpandableListView) view.findViewById(R.id.faq_expandable_list_view);

        setupFAQData();

        mFAQExpandableListAdapter = new FAQExpandableListAdapter(getActivity(), mFAQHeaderDataList, mFAQBodyDataMap);
        mFAQExpandableListView.setAdapter(mFAQExpandableListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupFAQData() {
        String[] headerTextArr = getResources().getStringArray(R.array.faq_header_list);
        String[] bodyTextArr = getResources().getStringArray(R.array.faq_body_list);

        for (int i = 0; i < headerTextArr.length; i++) {
            mFAQHeaderDataList.add(headerTextArr[i]);
        }

        ArrayList<String> body1 = new ArrayList<>();
        body1.add(bodyTextArr[0]);
        ArrayList<String> body2 = new ArrayList<>();
        body2.add(bodyTextArr[1]);
        ArrayList<String> body3 = new ArrayList<>();
        body3.add(bodyTextArr[2]);
        ArrayList<String> body4 = new ArrayList<>();
        body4.add(bodyTextArr[3]);
        ArrayList<String> body5 = new ArrayList<>();
        body5.add(bodyTextArr[4]);
        ArrayList<String> body6 = new ArrayList<>();
        body6.add(bodyTextArr[5]);
        ArrayList<String> body7 = new ArrayList<>();
        body7.add(bodyTextArr[6]);
        ArrayList<String> body8 = new ArrayList<>();
        body8.add(bodyTextArr[7]);

        mFAQBodyDataMap.put(mFAQHeaderDataList.get(0), body1);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(1), body2);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(2), body3);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(3), body4);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(4), body5);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(5), body6);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(6), body7);
        mFAQBodyDataMap.put(mFAQHeaderDataList.get(7), body8);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }
}