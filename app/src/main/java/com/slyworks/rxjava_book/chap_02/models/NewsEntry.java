package com.slyworks.rxjava_book.chap_02.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Joshua Sylvanus, 2:14 PM, 24-Dec-21.
 */
public class NewsEntry implements Comparable<NewsEntry>, Parcelable {
    //region Vars
    public final String id;
    public final String title;
    public final String link;
    public final Long updated;

    public FeedSource source = null;


    //endregion

    public NewsEntry(String id, String title, String link, long updated){
        this.id = id;
        this.title = title;
        this.link = link;
        this.updated = updated;

    }

    public NewsEntry(String id, String title, String link, long updated,FeedSource source){
        this.id = id;
        this.title = title;
        this.link = link;
        this.updated = updated;

        this.source = source;

    }

    protected NewsEntry(Parcel in) {
        id = in.readString();
        title = in.readString();
        link = in.readString();
        if (in.readByte() == 0) {
            updated = null;
        } else {
            updated = in.readLong();
        }
    }

    public static final Creator<NewsEntry> CREATOR = new Creator<NewsEntry>() {
        @Override
        public NewsEntry createFromParcel(Parcel in) {
            return new NewsEntry(in);
        }

        @Override
        public NewsEntry[] newArray(int size) {
            return new NewsEntry[size];
        }
    };

    public void setSource(FeedSource source){ this.source = source; }

    public static String _toString(Object o) {
        NewsEntry e = (NewsEntry) o;
        return new Date(e.updated).toString() + "\n" + e.title;
    }

    @Override
    public String toString(){
        return new Date(updated).toString() + "\n" + title;
    }
    @Override
    public int compareTo(NewsEntry another) {
        if (this.updated > another.updated) {
            return -1;
        } else if (this.updated < another.updated) {
            return 1;
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(link);
        if (updated == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(updated);
        }
    }

}
