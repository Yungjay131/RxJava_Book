package com.slyworks.rxjava_book.chap_02.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Joshua Sylvanus, 2:12 PM, 24-Dec-21.
 */
public interface AtomApi {
    final String NEWSAPI_API_KEY = "bc106c618b2d4e37ac261b15e5545a65";
    //"https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY"

    @GET
    Call<ResponseBody> getFeed(@Url String url);

    @Headers({"X-Api-Key:bc106c618b2d4e37ac261b15e5545a65"})
    @GET("https://newsapi.org/v2/top-headlines")
    Call<ResponseBody> getFeedFromNewsApi(@Query("country") String country);
}
