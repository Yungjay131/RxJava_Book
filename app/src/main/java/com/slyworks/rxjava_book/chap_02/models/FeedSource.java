package com.slyworks.rxjava_book.chap_02.models;

/**
 * Created by Joshua Sylvanus, 4:05 PM, 26-Dec-21.
 */
public enum FeedSource{
    NOT_SET{
        @Override
        public String getLink() {
            return "not_set";
        }
    },
    GOOGLE_NEWS{
        @Override
        public String getLink() {
            return "google news";
        }
    },
    THE_REGISTER{
        @Override
        public String getLink() {
            return "the register";
        }
    },
    NEWS_API{
        @Override
        public String getLink() {
            return "news api";
        }
    };

    abstract public String getLink();
}
