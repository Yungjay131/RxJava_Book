package com.slyworks.rxjava_book.chap_02.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joshua Sylvanus, 6:52 PM, 25-Dec-21.
 */
public class NewsEntryApiClient {
    //region Vars
    private final String URL = "https://newsapi.org";
    private Retrofit instance = null;
    //endregion

    public void getRetrofit(){
        if(instance == null){
            instance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL)
                    .build();
        }
    }

    public AtomApi getApiInterface(){
        getRetrofit();
        return instance.create(AtomApi.class);
    }
}
