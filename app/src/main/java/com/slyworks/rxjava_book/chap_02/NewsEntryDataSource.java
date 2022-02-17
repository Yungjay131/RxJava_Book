package com.slyworks.rxjava_book.chap_02;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import com.slyworks.rxjava_book.chap_02.models.NewsEntry;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.coroutines.Continuation;

/**
 * Created by Joshua Sylvanus, 5:20 PM, 31-Dec-21.
 */
public class NewsEntryDataSource extends PagingSource<Integer, NewsEntry> {
    //region Vars
    private  final String TAG = NewsEntryDataSource.class.getSimpleName();

    private static NewsEntryDataSource INSTANCE = null;

    //endregion
    public NewsEntryDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NewsEntryDataSource();
        }

        return INSTANCE;
    }

    @Override
    public boolean getKeyReuseSupported() {
        return true;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, NewsEntry> pagingState) {
        return 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public Object load(@NonNull LoadParams<Integer> loadParams, @NonNull Continuation<? super LoadResult<Integer, NewsEntry>> continuation) {
        List<NewsEntry> list = NewsEntryDataManager.getData()
                .subscribeOn(Schedulers.io())
                .blockingFirst();


        if(list != null && !list.isEmpty()){
            return new LoadResult.Page<Integer, NewsEntry>(
                    list,
                    null,
                    null
            );
        }

        return new LoadResult.Error<Integer,NewsEntry>(
                new Throwable("error getting data")
        );
    }
}
