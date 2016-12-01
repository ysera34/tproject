package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.common.util.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoon on 2016. 11. 15..
 * flows from Navigation Drawer menu Button
 */

public class FAQFragment extends Fragment {

    private ExpandableListAdapter mExpandableListAdapter;
    private ExpandableListView mExpandableListView;
    private ArrayList<String> mHeaderDataList;
    private HashMap<String, ArrayList<String>> mBodyDataMap;

    public FAQFragment() {
    }

    public static FAQFragment newInstance() {

        FAQFragment fragment = new FAQFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderDataList = new ArrayList<>();
        mBodyDataMap = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.faq_expandable_list_view);

        setupFAQData();

        mExpandableListAdapter = new ExpandableListAdapter(getActivity(), mHeaderDataList, mBodyDataMap);
        mExpandableListView.setAdapter(mExpandableListAdapter);

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
            mHeaderDataList.add(headerTextArr[i]);
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

        mBodyDataMap.put(mHeaderDataList.get(0), body1);
        mBodyDataMap.put(mHeaderDataList.get(1), body2);
        mBodyDataMap.put(mHeaderDataList.get(2), body3);
        mBodyDataMap.put(mHeaderDataList.get(3), body4);
        mBodyDataMap.put(mHeaderDataList.get(4), body5);
        mBodyDataMap.put(mHeaderDataList.get(5), body6);
        mBodyDataMap.put(mHeaderDataList.get(6), body7);
    }
}