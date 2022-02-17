package com.slyworks.rxjava_book.chap_04.models;

import java.util.List;

/**
 * Created by Joshua Sylvanus, 11:45 AM, 10-Feb-22.
 */
public class FlickrPhoto {
    //region Vars
    private final String mId;
    private final String mTitle;
    private final String mUsername;
    private final String mThumbnailUrl;
    //endregion

    public static FlickrPhoto createPhoto(FlickrPhotoInfoResponse.PhotoInfo photoInfo,
                                          List<FlickrPhotoSizeResponse.Size> sizes){
        String thumbnailUrl = null;
        for (FlickrPhotoSizeResponse.Size size: sizes) {
            if(size.getLabel().equals("Square")){
                thumbnailUrl = size.getSource();
                break;
            }
        }

        return new FlickrPhoto(
                photoInfo.getId(),
                photoInfo.getTitle(),
                photoInfo.getUsername(),
                thumbnailUrl );
    }
    public FlickrPhoto(String id, String title, String username, String thumbnail) {
        this.mId = id;
        this.mTitle = title;
        this.mUsername = username;
        this.mThumbnailUrl = thumbnail;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }
}
