package com.example.booklistapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<ArrayList<BookData>> {
    private String mURL;
    private static final String TAG = "BookLoader";

    public BookLoader(@NonNull Context context, String url) {
        super(context);
        mURL = url;
    }
    @Override
    protected void onStartLoading() {
        Log.i(TAG, "Now onStartLoading method");
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<BookData> loadInBackground() {
        ArrayList<BookData> bookData;
        Log.i(TAG, "Now LoadinBackground method");
        if (mURL==null){
            return null ;
        }
        bookData  = Network.extractbooksfromurl(mURL);
        return bookData;
    }
}
