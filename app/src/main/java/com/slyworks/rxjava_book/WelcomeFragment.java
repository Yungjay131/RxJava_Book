package com.slyworks.rxjava_book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragment extends Fragment {
    //region Vars
    private static WelcomeFragment INSTANCE;
    //endregion
    public WelcomeFragment() { }

    public static WelcomeFragment newInstance() {
        if(INSTANCE == null){
            INSTANCE = new WelcomeFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }
}