package com.slyworks.rxjava_book.chap_02.network;



import android.util.Log;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Joshua Sylvanus, 7:22 PM, 25-Dec-21.
 */
public class RawNetworkObservable {
    //region Vars
    private static final String TAG = RawNetworkObservable.class.getSimpleName();

    private static AtomApi mDelegate =  new NewsEntryApiClient().getApiInterface();;
    //endregion

    private RawNetworkObservable(){ }

    public static Observable<Response> create2(final String param){
        Observable<Response> o = Observable.create(
                new ObservableOnSubscribe<Response>() {
                    final OkHttpClient client = new OkHttpClient();
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Response> emitter) throws Throwable {
                        try{
                           // Response response = mDelegate.getFeedFromNewsApi(param).execute().raw();
                            Response response = client.newCall(new Request.Builder()
                                                                       .url("https://newsapi.org/v2/top-headlines?country="+param+"&apiKey=bc106c618b2d4e37ac261b15e5545a65")
                                                                       .build())
                                                                       .execute();

                            emitter.onNext(response);
                            emitter.onComplete();
                        }catch(IOException ioe){
                            emitter.onError(ioe);
                        }
                    }
                }
        )
        .subscribeOn(Schedulers.io());

        return o;
    }
    public static Observable<Response> create(final String url){
        Observable<Response> o = Observable.create(
                new ObservableOnSubscribe<Response>() {
                    final OkHttpClient client = new OkHttpClient();

                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Response> emitter) throws Throwable {
                        try{
                            //books way
                             Request req = new Request.Builder().url(url).build();
                             Response response = client.newCall(req).execute();

                             //Response response = mDelegate.getFeed(url).execute().raw();

                             emitter.onNext(response);
                             emitter.onComplete();
                         }catch(IOException ioe){
                             emitter.onError(ioe);
                         }

                    }
                }
        )
        .subscribeOn(Schedulers.io());

        return o;
    }

    public static Observable<String> getString(String url){
        Observable<String> o =
                create(url)
                   .map(response -> {
                      try{
                          return response.body().toString();
                      }catch (Exception e){
                          Log.e(TAG, "getString: "+ url);
                      }

                      //reaching here means there was an error
                      return null;
                   });

        return o;
    }
}
