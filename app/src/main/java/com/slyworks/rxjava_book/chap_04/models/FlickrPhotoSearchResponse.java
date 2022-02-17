package com.slyworks.rxjava_book.chap_04.models;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Joshua Sylvanus, 6:38 PM, 09-Feb-22.
 */
public class FlickrPhotoSearchResponse {
    //region Vars
    private final Photos mPhotos;
    //endregion

    private static class Photos{
      //region Vars
        private final List<Photo> mPhoto;
      //endregion

      public Photos(List<Photo> photo){
          this.mPhoto = photo;
      }

        public List<Photo> getPhoto() {
            return mPhoto;
        }
    }

    public static class Photo{
        //region Vars
        private final String mId;
        private final String mOwner;
        private final String mTitle;
        //endregion


        public Photo(String id, String owner, String title) {
            mId = id;
            mOwner = owner;
            mTitle = title;
        }

        public String getId(){ return mId; }

        @NonNull
        @Override
        public String toString() {
            return mTitle;
        }
    }

    private FlickrPhotoSearchResponse(Photos photos){
        this.mPhotos = photos;
    }

    public List<Photo> getPhotos(){
        return mPhotos.getPhoto();
    }

}
