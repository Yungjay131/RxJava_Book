package com.slyworks.rxjava_book;

/**
 * Created by Joshua Sylvanus, 4:11 PM, 26-Dec-21.
 */
public class Constants {
    //region Vars
    public static final String URL_1 = "https://news.google.com/?output=atom";
    public static final String URL_2 = "http://www.theregister.co.uk/software/headlines.atom";
    public static final String URL_3 = "https://newsapi.org/v2/top-headlines?country=ng";
    public static final String URL_4 = "http://www.linux.com/news/software?format=feed&type=atom";

    public static final String EVENT_GET_FRAGMENT_MANAGER = "com.slyworks.rxjava_book.EVENT_GET_FRAGMENT_MANAGER";
    public static final String EVENT_UPDATE_NAV_VIEW = "com.slyworks.rxjava_book.EVENT_UPDATE_NAV_VIEW";
    //endregion

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("cannot create instance of Constants class");
    }
}
