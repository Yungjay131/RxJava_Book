package com.slyworks.rxjava_book.chap_02;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.slyworks.rxjava_book.chap_02.models.NewsEntry;

import java.util.ArrayList;

import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.CoroutineScope;

/**
 * Created by Joshua Sylvanus, 6:18 PM, 31-Dec-21.
 */
public class NewsEntryViewModel extends ViewModel {

    CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);

    Pager<Integer, NewsEntry> pager = new Pager<>(
            new PagingConfig(10),
            () -> new NewsEntryDataSource()/*MDataSource::new*/
    );

    public LiveData<PagingData<NewsEntry>> newsEntryPagingLiveData = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

    public MutableLiveData<ArrayList<NewsEntry>> newsEntryLiveData = new MutableLiveData<>();
    public void getData(){
        NewsEntryDataManager.getData()
                .observeOn(Schedulers.io())
                .subscribe(list ->{
                    newsEntryLiveData.postValue((ArrayList<NewsEntry>) list);
                });
    }
}
