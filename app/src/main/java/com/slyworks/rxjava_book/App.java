package com.slyworks.rxjava_book;

import android.app.Application;
import android.content.Context;

/**
 * Created by Joshua Sylvanus, 7:19 PM, 25-Dec-21.
 */
public class App extends Application {
    //region Vars
    private static Context mContext;
    //endregion

    public static Context getContext(){
        return mContext;
    }

    private void setContext(Context ctx){
        mContext = ctx;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setContext(this);
    }
}
