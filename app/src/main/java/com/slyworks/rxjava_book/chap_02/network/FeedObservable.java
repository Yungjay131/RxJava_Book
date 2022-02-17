package com.slyworks.rxjava_book.chap_02.network;

import android.util.Log;

import com.google.gson.JsonObject;
import com.slyworks.rxjava_book.Constants;
import com.slyworks.rxjava_book.chap_02.models.FeedSource;
import com.slyworks.rxjava_book.chap_02.models.NewsEntry;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.Response;


/**
 * Created by Joshua Sylvanus, 7:45 PM, 25-Dec-21.
 */
public class FeedObservable {
    //region Vars
    private static final String TAG = FeedObservable.class.getSimpleName();
    private String mUrl;
    //endregion

    public FeedObservable(){}

    public Observable<List<NewsEntry>> getFeedFromUrlAsJSON(final String param){
        mUrl = Constants.URL_3;

        return RawNetworkObservable.create2(param)
                .map(this::parseJSON)
                .map(this::setNewsEntrySource);
    }
    public Observable<List<NewsEntry>> getFeedFromUrl(final String url){
        ///TODO:create an Observable that parses JSON/XML data from server

        mUrl = url;

        return RawNetworkObservable.create(url)
                .map(this::parseXML)
                .map(this::setNewsEntrySource);
    }

    private List<NewsEntry> setNewsEntrySource(List<NewsEntry> list){
        FeedSource source = FeedSource.NOT_SET;

        switch (mUrl){
            case Constants.URL_1:{
                source = FeedSource.GOOGLE_NEWS;
                break;
            }
            case Constants.URL_2:{
                source = FeedSource.THE_REGISTER;
                break;
            }
            case Constants.URL_3:
                source = FeedSource.NEWS_API;
                break;
        }

        for(NewsEntry ne: list){ ne.source = source; }

        return list;
    }
    private List<NewsEntry> parseXML(Response r){
        FeedParserXML parser = new FeedParserXML();

        try{
            List<NewsEntry> newsEntries = parser.parse(r.body().byteStream());
            Log.e(TAG, "parseXML: Number of entries from url: " + newsEntries.size());

            return newsEntries;
        }catch(Exception e){
            Log.e(TAG, "parseXML: error parsing feed", e);
        }

        return new ArrayList<>();
    }

    private List<NewsEntry> parseJSON(Response r){
        FeedParserJSON parser = FeedParserJSON.getInstance();

        try{
            List<NewsEntry> newsEntries = FeedParserJSON.getInstance().parse(r.body().string());
            Log.e(TAG, "parseJSON: Number of entries from url: " + newsEntries.size());

            return newsEntries;
        }catch(Exception e){
            Log.e(TAG, "parseJSON: error parsing feed", e);
        }

        return new ArrayList<>();
    }

}
