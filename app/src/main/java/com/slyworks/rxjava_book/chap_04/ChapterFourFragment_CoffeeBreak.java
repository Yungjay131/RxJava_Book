package com.slyworks.rxjava_book.chap_04;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.jakewharton.rxbinding4.view.RxView;
import com.slyworks.rxjava_book.R;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.disposables.SerialDisposable;

/**
 * Created by Joshua Sylvanus 07/02/2022, 4:47 PM*/
public class ChapterFourFragment_CoffeeBreak extends Fragment {
    //region Vars
    private TextView tvDisplay_1;
    private TextView tvDisplay_2;
    private TextView tvDisplay_3;
    private MaterialButton btnReset;
    //endregion

    public static ChapterFourFragment_CoffeeBreak newInstance() {
        return new ChapterFourFragment_CoffeeBreak();
    }

    public ChapterFourFragment_CoffeeBreak() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chapter_four__coffee_break, container, false);
        initViews_1(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews_2(view);
    }

    private void initViews_1(View view){
        tvDisplay_1 = view.findViewById(R.id.tvDisplay_1_chap_four_coffee_break);
        tvDisplay_2 = view.findViewById(R.id.tvDisplay_2_chap_four_coffee_break);
        tvDisplay_3 = view.findViewById(R.id.tvDisplay_3_chap_four_coffee_break);
        btnReset = view.findViewById(R.id.btnReset_chap_four_coffee_break);
    }

    private void initViews_2(View view){
        /*by default*/
        initRx_SerialDisposable();
        initRx_CompositeDisposable();
    }

    private void initRx_SerialDisposable(){
        /*observable that emits a number every 1 second*/
        Observable<Long> numberEmitterObservable =
                Observable.interval(1, TimeUnit.SECONDS);

        /*map to string to be used directly in TextView*/
        Observable<String> numberEmitterToStringObservable =
                numberEmitterObservable.map(Object::toString)
                        .observeOn(AndroidSchedulers.mainThread());

        AtomicReference<SerialDisposable> serialDisposable = new AtomicReference<>(new SerialDisposable());

        /*on click of any of the textViews reset the serialDisposable to the current subscription
        * that is, dispose the old one and re-subscribe*/
        RxView.clicks(tvDisplay_1)
                .subscribe(event ->{
                    Disposable d = numberEmitterToStringObservable.subscribe(text -> tvDisplay_1.setText(text));
                    serialDisposable.get().set(d);
                });

        RxView.clicks(tvDisplay_1)
                .subscribe(event ->{
                    Disposable d = numberEmitterToStringObservable.subscribe(text -> tvDisplay_2.setText(text));
                    serialDisposable.get().set(d);
                });

        RxView.clicks(tvDisplay_1)
                .subscribe(event ->{
                    Disposable d = numberEmitterToStringObservable.subscribe(text -> tvDisplay_3.setText(text));
                    serialDisposable.get().set(d);
                });

        RxView.clicks(btnReset)
                .subscribe(event ->{
                    /*releasing whatever is the current subscription*/
                    serialDisposable.get().dispose();
                    serialDisposable.set(new SerialDisposable());
                });
    }

    private void initRx_CompositeDisposable(){
        /*observable that emits a number every 1 second*/
        Observable<Long> numberEmitterObservable =
                Observable.interval(1, TimeUnit.SECONDS);

        /*map to string to be used directly in TextView*/
        Observable<String> numberEmitterToStringObservable =
                numberEmitterObservable.map(Object::toString)
                        .observeOn(AndroidSchedulers.mainThread());

        AtomicReference<CompositeDisposable> compositeDisposable = new AtomicReference<>(new CompositeDisposable());

        RxView.clicks(tvDisplay_1)
                .subscribe(event ->{
                    Disposable d = numberEmitterToStringObservable.subscribe(text -> tvDisplay_1.setText(text));
                    compositeDisposable.get().add(d);
                });

        RxView.clicks(tvDisplay_1)
                .subscribe(event ->{
                    Disposable d = numberEmitterToStringObservable.subscribe(text -> tvDisplay_2.setText(text));
                    compositeDisposable.get().add(d);
                });

        RxView.clicks(tvDisplay_1)
                .subscribe(event ->{
                    Disposable d = numberEmitterToStringObservable.subscribe(text -> tvDisplay_3.setText(text));
                    compositeDisposable.get().add(d);
                });

        RxView.clicks(btnReset)
                .subscribe(event ->{
                    compositeDisposable.get().dispose();
                    compositeDisposable.set(new CompositeDisposable());
                });
    }
}