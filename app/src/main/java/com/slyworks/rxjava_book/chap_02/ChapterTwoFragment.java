package com.slyworks.rxjava_book.chap_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slyworks.rxjava_book.App;
import com.slyworks.rxjava_book.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class ChapterTwoFragment extends Fragment {
    //region Vars
    private static final String TAG = ChapterTwoFragment.class.getSimpleName();
    private ListView mList;
    private static ChapterTwoFragment INSTANCE;
    private List<Disposable> mDisposableList = new ArrayList<>();

    private NewsEntryViewModel mViewModel;
    private NewsEntryPagedListAdapter mAdapter;
    private NewsEntryAdapter mAdapter2;
    private RecyclerView rvChap_02;
    //endregion

    public ChapterTwoFragment() {}

    public static ChapterTwoFragment newInstance() {
      if(INSTANCE == null){
          INSTANCE = new ChapterTwoFragment();
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
        View v = inflater.inflate(R.layout.fragment_chapter_two, container, false);

        initViews(v);
        initData();
        return v;
    }

    private void initData(){
        _init();
        _initRX();
    }
    private void _init(){
        mViewModel = new ViewModelProvider(this).get(NewsEntryViewModel.class);
        mViewModel.newsEntryLiveData.observe(getViewLifecycleOwner(), data ->{
            mAdapter2.addItems(data);
        });

        mViewModel.getData();
    }
    private void _init2(){
        mViewModel = new ViewModelProvider(this).get(NewsEntryViewModel.class);
        mViewModel.newsEntryPagingLiveData.observe(getViewLifecycleOwner(), pagingData -> {
            mAdapter.submitData(getViewLifecycleOwner().getLifecycle(), pagingData);
        });

    }
    private void _initRX(){ }
    private void initViews(View v){
        mAdapter = new NewsEntryPagedListAdapter(new NewsEntryDiffUtil());
        mAdapter2 = new NewsEntryAdapter();

        rvChap_02 = v.findViewById(R.id.rvDisplay_chap_two);
        rvChap_02.setLayoutManager(new LinearLayoutManager(App.getContext(),LinearLayoutManager.VERTICAL, false));
        rvChap_02.addItemDecoration(new DividerItemDecoration(rvChap_02.getContext(), LinearLayoutManager.VERTICAL));

        rvChap_02.setAdapter(mAdapter2);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(Disposable d:mDisposableList){ d.dispose(); }
    }
}