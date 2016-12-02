package com.tacademy.v04.chemi.common.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoon on 2016. 12. 2..
 */

public class NotifyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<String> mHeaderDataList;
    private HashMap<String, ArrayList<String>> mBodyDataMap;

    public NotifyExpandableListAdapter(Context context, ArrayList<String> headerDataList,
                                       HashMap<String, ArrayList<String>> bodyDataMap) {
        mContext = context;
        mHeaderDataList = headerDataList;
        mBodyDataMap = bodyDataMap;
    }

    @Override
    public int getGroupCount() {
        return this.mHeaderDataList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.mBodyDataMap.get(this.mHeaderDataList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.mHeaderDataList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.mBodyDataMap.get(this.mHeaderDataList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerText = (String) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.list_item_notify_header, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.list_item_notify_header_text_view);
        textView.setText(headerText);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String bodyText  = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.list_item_notify_body, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.list_item_notify_body_text_view);
        textView.setText(bodyText);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
