package com.slyworks.rxjava_book.chap_04.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joshua Sylvanus, 7:39 AM, 14-Jan-22.
 */
public class FlickrApiClient {
    //region Vars
    private final String URL = "https://api.flickr.com";
    private Retrofit INSTANCE = null;
    //endregion

    public Retrofit getRetrofit(){
        if(INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                               .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                               .addConverterFactory(GsonConverterFactory.create())
                               .baseUrl(URL)
                               .build();
        }

        return INSTANCE;
    }

    public FlickrApi getApiInterface(){
        return getRetrofit().create(FlickrApi.class);
    }

}
