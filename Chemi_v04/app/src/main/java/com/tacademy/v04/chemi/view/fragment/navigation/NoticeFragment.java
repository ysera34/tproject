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
import com.tacademy.v04.chemi.common.util.adapter.NotifyExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoon on 2016. 11. 14..
 * flows from Navigation Drawer menu Button
 * Notification
 */

public class NoticeFragment extends Fragment {

    private NotifyExpandableListAdapter mNotifyExpandableListAdapter;
    private ExpandableListView mNotifyExpandableListView;
    private ArrayList<String> mNotifyHeaderDataList;
    private HashMap<String, ArrayList<String>> mNotifyBodyDataMap;

    public NoticeFragment() {
    }

    public static NoticeFragment newInstance() {

        NoticeFragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mNotifyHeaderDataList = new ArrayList<>();
        mNotifyBodyDataMap = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        mNotifyExpandableListView = (ExpandableListView) view.findViewById(R.id.notify_expandable_list_view);

        setupNotifyData();

        mNotifyExpandableListAdapter = new NotifyExpandableListAdapter(getActivity(), mNotifyHeaderDataList, mNotifyBodyDataMap);
        mNotifyExpandableListView.setAdapter(mNotifyExpandableListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupNotifyData() {
        String[] headerTextArr = getResources().getStringArray(R.array.notify_header_list);
        String[] bodyTextArr = getResources().getStringArray(R.array.notify_body_list);

        for (int i = 0; i < headerTextArr.length; i++) {
            mNotifyHeaderDataList.add(headerTextArr[i]);
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

        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(0), body1);
        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(1), body2);
        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(2), body3);
        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(3), body4);
        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(4), body5);
        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(5), body6);
        mNotifyBodyDataMap.put(mNotifyHeaderDataList.get(6), body7);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }

}
