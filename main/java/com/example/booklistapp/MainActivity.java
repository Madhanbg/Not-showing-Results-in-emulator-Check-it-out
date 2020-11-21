package com.example.booklistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<BookData>> {
    private static final String GOOGLEBOOKURL = "https://www.googleapis.com/books/v1/volumes?q=java";
    private static final String TAG = "Mainactivity";
  private   RecyclerView recyclerview;
    private RecyclerAdapter recyclerAdapter;
    private static final int BOOK_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<BookData> arrayList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(this,arrayList);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerView);
       recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(recyclerAdapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        Log.i(TAG, "Now calling initLoader");
       loaderManager.initLoader(BOOK_LOADER_ID, null, this);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerview.addItemDecoration(dividerItemDecoration);


    }

    @NonNull
    @Override
    public Loader<ArrayList<BookData>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i(TAG,"Problem with onCreateloader");
        return new BookLoader(MainActivity.this,GOOGLEBOOKURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<BookData>> loader, ArrayList<BookData> data) {
        Log.i(TAG,"Problem with OnloadFinished method");
        recyclerAdapter.clear();
        if (data != null && !data.isEmpty()) {
          recyclerAdapter.addAll(data);
        }else
            recyclerAdapter.add(new BookData("EEEE"));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<BookData>> loader) {
        recyclerAdapter.clear();
        Log.i(TAG, "Now loader is resetting");
    }
}