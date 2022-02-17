package com.slyworks.rxjava_book.chap_02.network;

import android.text.format.Time;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slyworks.rxjava_book.chap_02.models.IntermediateNewsEntry;
import com.slyworks.rxjava_book.chap_02.models.NewsEntry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by Joshua Sylvanus, 9:17 PM, 25-Dec-21.
 */
class FeedParserJSON {
    //region Vars
    private static final String TAG = FeedParserJSON.class.getSimpleName();

    private static FeedParserJSON INSTANCE = null;
    //endregion

    public static FeedParserJSON getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FeedParserJSON();
        }

        return INSTANCE;
    }

    private FeedParserJSON(){}

    public List<NewsEntry> parse(String response){
        List<NewsEntry> list = new ArrayList<>();

       return _parse(response);
    }
    private List<NewsEntry> _parse(String data){
        Type resultType = new TypeToken<List<IntermediateNewsEntry>>(){}.getType();
        List<IntermediateNewsEntry> list = new Gson().fromJson(data, resultType);

        List<NewsEntry> l;
        try {
            l = Executors.newSingleThreadExecutor()
                    .submit(new Callable<List<NewsEntry>>() {
                        @Override
                        public List<NewsEntry> call() throws Exception {
                            return mapDataToModel(list);
                        }
                    })
                    .get();

            return l;
        }catch (Exception e){
            Log.e(TAG, "_parse: ",e );
        }

        return new ArrayList<NewsEntry>();
    }

    private List<NewsEntry> mapDataToModel(List<IntermediateNewsEntry> list){
        List<NewsEntry> l = new ArrayList<>();

        for(IntermediateNewsEntry iNewsEntry: list){
            String id = iNewsEntry.source.id;
            String title = iNewsEntry.title;
            String link = iNewsEntry.url;

            Time  t = new Time();
            t.parse3339(iNewsEntry.publishedAt);
            long updated = t.toMillis(false);

            l.add(new NewsEntry(id,title,link,updated));
        }

        return l;
    }
}
