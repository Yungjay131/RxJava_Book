package com.slyworks.rxjava_book.chap_05;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slyworks.rxjava_book.R;

public class ChapterFiveFragment extends Fragment {
    //region Vars
    //endregion
    public ChapterFiveFragment() { }

    public static ChapterFiveFragment newInstance() {
       return new ChapterFiveFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chapter_five, container, false);
        initViews_1(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews_2(view);
    }
}
/*Subjects
* is a fusion of an Observable and a Subscriber
*
* there is currently no default Observable wrapper for FileSystem operations
*
* creating Observables
public interface Emitter<T>{
    void onNext(T value);
    void onError(Throwable error);
    void onComplete();
}
*
* Observable o  = Observable.create(emitter ->{});
void observableOnSubscribeFunction(Emitter<T> emitter);
*
*
* observeOn() and sunscribeOn()
* all code after observeOn() is run on the thread specified (usually MainThread)
*
* all code before sunscribeOn() is run on the specified thread, can only be defined once
* all other calls to subscribeOn() are ignored
* this specifies the thread on which the preceeding code will run
*
*
* */

