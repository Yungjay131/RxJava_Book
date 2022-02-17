package com.slyworks.rxjava_book.chap_04.network;

import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoInfoResponse;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoSearchResponse;
import com.slyworks.rxjava_book.chap_04.models.FlickrPhotoSizeResponse;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Joshua Sylvanus, 4:14 PM, 07-Feb-22.
 */
public interface FlickrApi {

    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Observable<FlickrPhotoSearchResponse> searchPhotos(@Query("api_key") String apiKey,
                                                       @Query("tags") String query,
                                                       @Query("per_page") int limit );

    @GET("/services/rest/?method=flickr.photos.getInfo&format=json&nojsoncallback=1")
    Observable<FlickrPhotoInfoResponse> getPhotoInfo(@Query("api_key") String apiKey,
                                                     @Query("photo_id") String photoID);

    @GET("/services/rest/?method=flickr.photos.getSizes&format=json&nojsoncallback=1")
    Observable<FlickrPhotoSizeResponse> getSizes(@Query("api_key") String apiKey,
                                                 @Query("photo_id") String photoID);



}
