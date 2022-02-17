package com.slyworks.rxjava_book.chap_04;

import androidx.lifecycle.ViewModel;

import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoInfoResponse;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoSearchResponse;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoSizeResponse;
import com.slyworks.rxjava_book.chap_04.network.FlickrApi;
import com.slyworks.rxjava_book.chap_04.network.FlickrApiClient;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;

/**
 * Created by Joshua Sylvanus, 7:40 AM, 14-Jan-22.
 */
class FlickrViewModel extends ViewModel {
    //region Vars
    private FlickrApi mDelegate;
    private static final String API_KEY = "f071a231eb7d7873665423bd7b685b6c";
    private static final String API_SECRET = "425e3299a7adcae4";
    //endregion
    FlickrViewModel(){
        mDelegate = new FlickrApiClient().getApiInterface();
    }

    static{ }
    public Observable<FlickrPhotoSearchResponse> getData(String query){
        return mDelegate.searchPhotos(API_KEY,query, 3);
    }

    public Observable<FlickrPhotoInfoResponse> getPhotoInfo(String photoID){
        return mDelegate.getPhotoInfo(API_KEY, photoID);
    }

    public Observable<FlickrPhotoSizeResponse> getSizes(String photoID){
        return mDelegate.getSizes(API_KEY, photoID);
    }
}
