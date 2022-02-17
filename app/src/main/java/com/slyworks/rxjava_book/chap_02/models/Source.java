package com.slyworks.rxjava_book.chap_02.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Source implements Parcelable {
    //region Vars
    public String id;
    public String name;
    //endregion


    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source() {
    }

    protected Source(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }
}
