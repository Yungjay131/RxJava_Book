package com.slyworks.rxjava_book.chap_04;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.slyworks.rxjava_book.R;

public class ChapterFourFragment_Host extends Fragment {
    //region Vars
    private TabLayout tlMain;
    private ViewPager2 vpMain;
    private VPAdapter_ChapterFour mAdapter;
    //endregion
    public ChapterFourFragment_Host() { }

    public static ChapterFourFragment_Host newInstance(){
       return new ChapterFourFragment_Host();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chapter_four_host, container, false);
        initViews_1(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews_2(view);
    }

    private void initViews_1(View view){

    }
    private void initViews_2(View view){

    }
}