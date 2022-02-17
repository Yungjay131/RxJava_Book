package com.slyworks.rxjava_book.chap_04.models;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Joshua Sylvanus, 11:16 AM, 10-Feb-22.
 */
public class FlickrPhotoInfoResponse {
    //region Vars
    private final PhotoInfo mPhotoInfo;
    //endregion

    public static class PhotoInfo{
        //region Vars
        private final String mId;
        private final FlickrOwner mFlickrOwner;
        private final FlickrTitle mFlickrTitle;
        private int mViews;
        private FlickrUrls mFlickrUrls;
        //endregion


        public PhotoInfo(String id, FlickrOwner flickrOwner, FlickrTitle flickrTitle, int views, FlickrUrls flickrUrls) {
            this.mId = id;
            this.mFlickrOwner = flickrOwner;
            this.mFlickrTitle = flickrTitle;
            this.mViews = views;
            this.mFlickrUrls = flickrUrls;
        }

        public String getId() {
            return mId;
        }

        public String getTitle() {
            return mFlickrTitle._content;
        }

        public String getUsername(){
            return mFlickrOwner.username;
        }

        @NonNull
        @Override
        public String toString() {
            return mFlickrTitle._content + "\n" + mFlickrOwner.username;
        }
    }


    private static class FlickrTitle{
        //region Vars
        private final String _content;
        //endregion


        public FlickrTitle(String _content) {
            this._content = _content;
        }
    }

    private static class FlickrUrls{
        //region Vars
        private final List<FlickrUrl> mUrls;
        //endregion


        public FlickrUrls(List<FlickrUrl> urls) {
            this.mUrls = urls;
        }
    }

    private static class FlickrUrl{
        //region Vars
        private final String mType;
        private final String mContent;
        //endregion


        public FlickrUrl(String type, String content) {
            this.mType = type;
            this.mContent = content;
        }
    }

    private static class FlickrOwner{
        //region Vars
        private final String username;
        //endregion


        public FlickrOwner(String username) {
            this.username = username;
        }
    }


    public FlickrPhotoInfoResponse(PhotoInfo photoInfo) {
        this.mPhotoInfo = photoInfo;
    }

    public PhotoInfo getPhotoInfo() {
        return mPhotoInfo;
    }
}
