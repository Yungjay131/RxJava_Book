package com.slyworks.rxjava_book.chap_04;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.slyworks.rxjava_book.R;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhoto;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoInfoResponse;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoSearchResponse;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoSizeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

public class ChapterFourFragment extends Fragment {
    //region Vars
    private static final String TAG = ChapterFourFragment.class.getSimpleName();

    private Button btnSearch;
    private TextView tvSearch;
    private RecyclerView rvMain;

    private FlickrPhotoAdapter mAdapter;
    private FlickrViewModel mViewModel;
    //endregion

    public static ChapterFourFragment getInstance() {
        return new ChapterFourFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chapter_four, container, false);
        initData();
        initViews_1(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews_2(view);
    }

    private void initData(){
       //TODO:init ViewModel here
    }
    private void initViews_1(View view){}
    private void initViews_2(View view){
        /*seems Jake Wharton re-wrote his library in Kotlin, hence Observable<Unit> instead of
        * former Observable<Object>*/
        final Observable<Unit> buttonClicksObservable = RxView.clicks(btnSearch);
        final Observable<String> searchQueryTextObservable = RxTextView.textChanges(tvSearch)
                                                                       .map(CharSequence::toString);

        /*ensuring the length is > 3 before commencing a search*/
        searchQueryTextObservable.map(searchQuery -> searchQuery.length() >= 3)
                .subscribe(btnSearch::setEnabled);

        buttonClicksObservable
                .doOnNext(event -> Log.e(TAG, "initViews_2: search button clicked"))
                .withLatestFrom(searchQueryTextObservable, (event, searchQuery) -> searchQuery)
                .doOnNext(searchQuery -> Log.e(TAG, "initViews_2: start search with "+ "'" + searchQuery + "'"))
                .flatMap(searchQuery -> mViewModel.getData(searchQuery)
                                                  .subscribeOn(Schedulers.io()))
                .map(FlickrPhotoSearchResponse::getPhotos)
                .doOnNext(photos -> Log.e(TAG, "Found " + photos.size() + " photos to process"))
                .flatMap((Function<List<FlickrPhotoSearchResponse.Photo>, Observable<List<FlickrPhoto>>>) photos -> {
                   if(photos.size() > 0){
                       return Observable.fromIterable(photos)
                               .doOnNext(photo -> Log.e(TAG, "initViews_2: processing photo "+ photo.getId()))
                               .concatMap(photo -> {
                                   Observable<FlickrPhotoInfoResponse> _o1 = mViewModel.getPhotoInfo(photo.getId())
                                                                .subscribeOn(Schedulers.io());

                                   Observable<FlickrPhotoInfoResponse.PhotoInfo> __o1 = _o1.map(FlickrPhotoInfoResponse::getPhotoInfo);


                                   Observable<FlickrPhotoSizeResponse> _o2 = mViewModel.getSizes(photo.getId())
                                                           .subscribeOn(Schedulers.io());
                                   Observable<List<FlickrPhotoSizeResponse.Size>> __o2 = _o2.map(FlickrPhotoSizeResponse::getSizes);


                                   return Observable.combineLatest(__o1, __o2, FlickrPhoto::createPhoto);
                               })
                               .doOnNext(photo -> Log.e(TAG, "initViews_2: finished processing photo "+ photo.getId()))
                               .toList()
                               .doOnSuccess(photo -> Log.e(TAG, "initViews_2: finished processing all photos"))
                               .toObservable();
                   }else{
                       return Observable.just(new ArrayList<>());
                   }
                })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(listOfPhotos ->{
                    mAdapter.setData(listOfPhotos);
                },
                error -> Log.e(TAG, "initViews_2: error getting photos", error));

    }

    /*apis used
    * flickr.photos.search
    * flickr.photos.getInfo
    * flickr.photos.getSizes
    * */

    /*as a rule of thumb you should call subscribe() to get the last result
    * multiple subscribe() calls usually means you are doing something wrong*/

    /*SerialDisposable and CompositeDisposable
    * SerialDisposable
    * replace an existing subscription with a new one (dispose of the previous one)
    * its like the manual version of SwitchMap???
    * has ...set(Disposable d) that automatically disposes of the previously set subscription
    *
    * CompositeDisposable
    * multiple related subscriptions(Disposables) can be bundled together
    * and treated as one, that is 1 call to dispos() for the CompositeDisposable*/


    /*SwitchMap
    * allows you to return a new Observable based on an item emitted from the first one
    * goo for handling intermittent subscriptions or cancelling previous subscriptions
    * when a new one is started*/

    /*Observable.fromIterable()
    * create an Observable that emits items from a List in the same order in which
    * they were added*/

    /*flatMap
    * merges(flattens) all the observables that it has started
    * allows for multiple operations to run simultaneously
    * and outputs everything*/

    /*Observable.merge
    * takes source observables either as an iterable List or an Observable
    * kind of like an Observable of observables*/

    /**/

   }
