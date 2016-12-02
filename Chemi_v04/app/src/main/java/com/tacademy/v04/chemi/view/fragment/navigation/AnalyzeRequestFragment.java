package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 11. 14..
 * flows from Navigation Drawer menu Button
 * Request Chemical of Product
 */

public class AnalyzeRequestFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner mParentSpinner;
    private Spinner mChildSpinner;
    private ArrayList<ArrayList<String>> mChildSpinnerDataList;
    private ArrayAdapter<String> mParentDataAdapter;
    private ArrayAdapter<String> mChildDataAdapter;

    public AnalyzeRequestFragment() {
    }

    public static AnalyzeRequestFragment newInstance() {

        AnalyzeRequestFragment fragment = new AnalyzeRequestFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        String[] mParentStrArr = getResources().getStringArray(R.array.analyze_request_parent_category_array);
        ArrayList<String> mParentSpinnerData = new ArrayList<>();
        for (int i = 0; i < mParentStrArr.length; i++) {
            mParentSpinnerData.add(mParentStrArr[i]);
        }
        mParentDataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_analyze_request, mParentSpinnerData);

        mChildSpinnerDataList = new ArrayList<>();

        int[] stringArrayResIds = new int[]{
                R.array.analyze_request_child_category_array1, R.array.analyze_request_child_category_array2,
                R.array.analyze_request_child_category_array3, R.array.analyze_request_child_category_array4,
                R.array.analyze_request_child_category_array5, R.array.analyze_request_child_category_array6,
                R.array.analyze_request_child_category_array7};

        for (int i = 0; i < stringArrayResIds.length; i++) {
            ArrayList<String> childSpinnerData = new ArrayList<>();
            String[] childStrArr = getResources().getStringArray(stringArrayResIds[i]);
            for (int j = 0; j < childStrArr.length; j++) {
                childSpinnerData.add(childStrArr[j]);
            }
            mChildSpinnerDataList.add(childSpinnerData);
        }

//        Log.i("mChildSpinner.size", String.valueOf(mChildSpinnerDataList.size()));
//        for (ArrayList<String> str11 : mChildSpinnerDataList) {
//            for (String str: str11) {
//                Log.i("str", str);
//            }
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_analyze_request, container, false);
        mParentSpinner = (Spinner) view.findViewById(R.id.analyze_parent_category_spinner);
        mParentSpinner.setAdapter(mParentDataAdapter);
        mParentSpinner.setOnItemSelectedListener(this);
        mChildSpinner = (Spinner) view.findViewById(R.id.analyze_child_category_spinner);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), adapterView.getItemAtPosition(i).toString() + " i : " + i, Toast.LENGTH_SHORT).show();
        if (i > 0) {
            mChildDataAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.list_item_analyze_request, mChildSpinnerDataList.get(i-1));
            mChildSpinner.setAdapter(mChildDataAdapter);
            mChildDataAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((menu.findItem(R.id.action_delete)) != null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
    }
}
