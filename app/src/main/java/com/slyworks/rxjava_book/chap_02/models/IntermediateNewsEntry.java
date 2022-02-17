package com.slyworks.rxjava_book.chap_02.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joshua Sylvanus, 1:22 PM, 31-Dec-21.
 */
public class IntermediateNewsEntry implements Parcelable{
    //region Vars
    public Source source;
    public String title;
    public String publishedAt;
    public String url;
    //endregion

    public IntermediateNewsEntry(Source source, String title, String publishedAt, String url) {
        this.source = source;
        this.title = title;
        this.publishedAt = publishedAt;
        this.url = url;
    }

    public IntermediateNewsEntry() { }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected IntermediateNewsEntry(Parcel in) {
        source = in.readParcelable(Source.class.getClassLoader());
        title = in.readString();
        publishedAt = in.readString();
    }

    public static final Creator<IntermediateNewsEntry> CREATOR = new Creator<IntermediateNewsEntry>() {
        @Override
        public IntermediateNewsEntry createFromParcel(Parcel in) {
            return new IntermediateNewsEntry(in);
        }

        @Override
        public IntermediateNewsEntry[] newArray(int size) {
            return new IntermediateNewsEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(source, i);
        parcel.writeString(title);
        parcel.writeString(publishedAt);
    }
}
