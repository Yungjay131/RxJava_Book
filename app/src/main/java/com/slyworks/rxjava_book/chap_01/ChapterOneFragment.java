package com.slyworks.rxjava_book.chap_01;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.widget.RxSearchView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.slyworks.rxjava_book.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

public class ChapterOneFragment extends Fragment {
    //region Vars
    private static ChapterOneFragment INSTANCE = null;
    private EditText etText_1;
    private EditText etText_2;
    private TextView tvDisplay;

    private List<Disposable> mDisposablesList = new ArrayList<>();
    private Button btnFilter;
    private Button btnCombineLatest;
    private SwitchCompat switchFilter;
    private SwitchCompat switchCombineLatest;

    private AtomicBoolean mIsEventBeingHandled = new AtomicBoolean(false);
    //endregion

    public ChapterOneFragment() { }

    public static ChapterOneFragment NewInstance() {
        if(INSTANCE == null){
            INSTANCE  = new ChapterOneFragment();
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
        return inflater.inflate(R.layout.fragment_chapter_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View v){
        etText_1 = v.findViewById(R.id.etText_chap_one);
        etText_2 = v.findViewById(R.id.etText_2_chap_one);
        tvDisplay = v.findViewById(R.id.tvMessage_chap_one);

        btnFilter = v.findViewById(R.id.btnFilter_chap_one);
        btnCombineLatest = v.findViewById(R.id.btnCombineLatest_chap_one);

        switchFilter = v.findViewById(R.id.switchFilter_chap_one);
        switchCombineLatest = v.findViewById(R.id.switchCombineLatest_chap_one);

        btnFilter.setOnLongClickListener(this::toggleBetweenSwitchesAndButtons);
        btnCombineLatest.setOnLongClickListener(this::toggleBetweenSwitchesAndButtons);
        switchFilter.setOnLongClickListener(this::toggleBetweenSwitchesAndButtons);
        switchCombineLatest.setOnLongClickListener(this::toggleBetweenSwitchesAndButtons);

        initRxThroughSwitches();
    }

    private boolean toggleBetweenSwitchesAndButtons(View view){
        //first disposing of any disposable from previous setting(button or switch)
        for (Disposable d: mDisposablesList){
            d.dispose();
        }

        if(btnFilter.getVisibility() == View.GONE){
            switchFilter.setVisibility(View.GONE);
            switchCombineLatest.setVisibility(View.GONE);

            btnFilter.setVisibility(View.VISIBLE);
            btnCombineLatest.setVisibility(View.VISIBLE);

            initRxThroughButtons();
        }else{
            btnFilter.setVisibility(View.GONE);
            btnCombineLatest.setVisibility(View.GONE);

            switchFilter.setVisibility(View.VISIBLE);
            switchCombineLatest.setVisibility(View.VISIBLE);

            initRxThroughSwitches();
        }

        return true;
    }

    private void initRxThroughSwitches(){
        resetState(false);

        Observable<Boolean> sFilterEventObservable = Observable.create(
                new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Boolean> emitter) throws Throwable {
                        switchFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                              emitter.onNext(b);
                              //emitter.onComplete();
                            }
                        });

                    }
                })
        .observeOn(AndroidSchedulers.mainThread());

        Observable<Boolean> sCombineLatestEventObservable = Observable.create(
                new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Boolean> emitter) throws Throwable {
                        switchCombineLatest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                              emitter.onNext(b);
                              //emitter.onComplete();
                            }
                        });

                    }
                })
        .observeOn(AndroidSchedulers.mainThread());

        Disposable d = sFilterEventObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bool ->{
                    if(!bool && switchCombineLatest.isChecked()) return;

                    if(!bool && !switchCombineLatest.isChecked()){
                        resetState(false);
                        switchCombineLatest.setChecked(true);
                        return;
                    }

                    resetState(true);
                    switchCombineLatest.setChecked(false);

                    for(Disposable di:mDisposablesList){ di.dispose(); }
                    mDisposablesList = new ArrayList<>();

                    Disposable d1 = _initRXFilter();
                    mDisposablesList.add(d1);
                });


        Disposable d2 = sCombineLatestEventObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bool ->{
                    if(!bool && switchFilter.isChecked()) return;

                    if(!bool && !switchFilter.isChecked()){
                        resetState(true);
                        switchFilter.setChecked(true);
                        return;
                    }

                    resetState(false);
                    switchFilter.setChecked(false);

                    for(Disposable di:mDisposablesList){ di.dispose(); }
                    mDisposablesList = new ArrayList<>();

                    Disposable d2_1 = _initRXCombineLatest();
                    mDisposablesList.add(d2_1);
                });

    }

    private void initRxThroughButtons(){
        resetState(true);

        AtomicReference<Disposable> dFilter = new AtomicReference<>(null);
        AtomicReference<Disposable> dCombineLatest = new AtomicReference<>(null);

        btnFilter.setOnClickListener(view ->{
           for(Disposable d: mDisposablesList){ d.dispose(); }

           resetState(true);

            dFilter.set(_initRXFilter());

            mDisposablesList = new ArrayList<>();
            mDisposablesList.add(dFilter.get());
        });

        btnCombineLatest.setOnClickListener(view ->{
            for(Disposable d: mDisposablesList){ d.dispose(); }

            resetState(false);

            dCombineLatest.set(_initRXCombineLatest());

            mDisposablesList = new ArrayList<>();
            mDisposablesList.add(dCombineLatest.get());
        });
    }
    private Disposable _initRXFilter(){
        InitialValueObservable<CharSequence> textObservable = RxTextView.textChanges(etText_1);
        Disposable d = textObservable.filter(text -> {
            return text.length() >= 7;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(text -> tvDisplay.setText("Text entered is too long"));

        return d;
    }

    private Disposable _initRXCombineLatest(){
        InitialValueObservable<CharSequence> etObservable_1 = RxTextView.textChanges(etText_1);
        InitialValueObservable<CharSequence> etObservable_2 = RxTextView.textChanges(etText_2);

        Observable<String> etObservables =
                Observable
                        .combineLatest(
                                etObservable_1,
                                etObservable_2,
                                (eo1, eo2) -> {
                                    final String s = eo1.toString() + eo2.toString();

                                    return s;
                                })
                        .filter(text -> { return text.length() >= 10; });

        Disposable d = etObservables
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> { tvDisplay.setText(text); });

        return d;
    }
    private void resetState(boolean isFilter){
        etText_1.setText("");
        etText_2.setText("");
        tvDisplay.setText("");

        if(isFilter)
            etText_2.setVisibility(View.GONE);

        if(!isFilter && etText_2.getVisibility() == View.GONE)
            etText_2.setVisibility(View.VISIBLE);
    }
    @Override
    public void onStop() {
        super.onStop();
        //TODO:remove AppController sub subscriptions here
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        INSTANCE = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDisposablesList.forEach(Disposable::dispose);
        }else{
            for(Disposable disposable : mDisposablesList){ disposable.dispose(); }
        }
    }

    //region FaultyCode
    public void initRxSearchView(SearchView s){
        InitialValueObservable<CharSequence> textObservable = RxSearchView.queryTextChanges(null);
        textObservable.filter(query -> {
            return query.length() >= 3;
        })
                .debounce(150, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
    //endregion
}