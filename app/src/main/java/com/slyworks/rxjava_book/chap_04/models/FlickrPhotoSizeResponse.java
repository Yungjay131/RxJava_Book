package com.slyworks.rxjava_book.chap_04.models;

import java.util.List;

/**
 * Created by Joshua Sylvanus, 11:34 AM, 10-Feb-22.
 */
public class FlickrPhotoSizeResponse {
    //region Vars
    private final Sizes mSizes;
    //endregion

    public static class Sizes{
        //region Vars
        private final List<Size> size;
        //endregion

        public Sizes(List<Size> size) {
            this.size = size;
        }
    }

    public static class Size{
        //region Vars
        private final String mLabel;
        private final int mWidth;
        private final int mHeight;
        private final String mSource;
        //endregion


        public Size(String label, int width, int height, String source) {
            this.mLabel = label;
            this.mWidth = width;
            this.mHeight = height;
            this.mSource = source;
        }

        public String getLabel() {
            return mLabel;
        }

        public String getSource() {
            return mSource;
        }
    }

    public FlickrPhotoSizeResponse(Sizes sizes) {
        this.mSizes = sizes;
    }

    public List<Size> getSizes(){
        return mSizes.size;
    }

}
