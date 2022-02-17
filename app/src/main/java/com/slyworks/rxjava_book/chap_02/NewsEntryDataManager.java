package com.slyworks.rxjava_book.chap_02;

import com.slyworks.rxjava_book.Constants;
import com.slyworks.rxjava_book.chap_02.models.NewsEntry;
import com.slyworks.rxjava_book.chap_02.network.FeedObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by Joshua Sylvanus, 6:46 PM, 31-Dec-21.
 */
public class NewsEntryDataManager {
    //region Vars
    private static final String TAG = NewsEntryDataManager.class.getSimpleName();
    //endregion
    public static Observable<List<NewsEntry>> getData(){
        List<String> feedUrlsList = Arrays.asList(
                Constants.URL_1,
                Constants.URL_2
        );

        List<Observable<List<NewsEntry>>> feedsObservableList = new ArrayList<>();
        Observable<List<NewsEntry>> o =
                new FeedObservable()
                             .getFeedFromUrlAsJSON("ng")
                             .retry(3)
                             .onErrorReturn(error -> new ArrayList<>())
                             .subscribeOn(Schedulers.io());
        feedsObservableList.add(o);

        for(String url: feedUrlsList){
            Observable<List<NewsEntry>> _o =
                    new FeedObservable()
                            .getFeedFromUrl(url)
                            .retry(3)
                            .onErrorReturn(error -> new ArrayList<>())
                            .subscribeOn(Schedulers.io());

            feedsObservableList.add(_o);
        }

        /*combining the 3 feeds Observables into 1*/
        Observable<List<NewsEntry>> feedsObservable =
                Observable.combineLatest(
                        feedsObservableList,
                        (feedNewsEntryList) -> {
                            final List<NewsEntry> list = new ArrayList<>();

                            for(Object l: feedNewsEntryList){
                                list.addAll((List<NewsEntry>) l);
                            }

                            return list;
                        }
                )
                .subscribeOn(Schedulers.io());

        /*getting the result from the combined Observable which in turn triggers its
         * component Observables*/
        Observable<List<NewsEntry>> sortedFeedsObservable =
                feedsObservable
                        .map(combinedList ->{
                            List<NewsEntry> list = new ArrayList<>(combinedList);

                            Collections.sort(list);
                            return list;
                        })
                        .subscribeOn(Schedulers.io());

        /*sortedFeedsObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        error -> Log.e(TAG, "_initRX: ", error)
                );*/

        return sortedFeedsObservable;
    }












}
