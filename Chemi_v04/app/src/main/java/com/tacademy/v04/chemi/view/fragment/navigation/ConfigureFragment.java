package com.tacademy.v04.chemi.view.fragment.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.v04.chemi.R;

/**
 * Created by yoon on 2016. 11. 14..
 * flows from Navigation Drawer menu Button
 * Application Configuration
 */

public class ConfigureFragment extends Fragment implements View.OnClickListener {

    private View mConfigureTermsView;
    private View mConfigurePartnerView;

    public static ConfigureFragment newInstance() {

        ConfigureFragment fragment = new ConfigureFragment();
        return fragment;
    }

    public ConfigureFragment() {
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
        View view = inflater.inflate(R.layout.fragment_configure, container, false);
        mConfigureTermsView = view.findViewById(R.id.configure_terms_layout);
        mConfigureTermsView.setOnClickListener(this);

        mConfigurePartnerView = view.findViewById(R.id.configure_partner_layout);
        mConfigurePartnerView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.configure_terms_layout :
                FragmentTransaction ft1 = getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                TermsFragment termsFragment = TermsFragment.newInstance();
                ft1.replace(R.id.fragment_container, termsFragment).commit();
                break;
            case R.id.configure_partner_layout :
                FragmentTransaction ft2 = getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                PartnerFragment partnerFragment = PartnerFragment.newInstance();
                ft2.replace(R.id.fragment_container, partnerFragment).commit();
                break;
        }
    }
}

