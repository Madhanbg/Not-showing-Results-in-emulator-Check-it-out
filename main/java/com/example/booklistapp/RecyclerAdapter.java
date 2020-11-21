package com.example.booklistapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerApdater";
    ArrayList<BookData> mArrayList;
    Context mContext;

    public RecyclerAdapter(Context context, ArrayList<BookData> arrayList) {
        mArrayList = arrayList;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: now creating views");
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.rowrepresent, parent, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Now executing");
        BookData currentPosition = mArrayList.get(position);
          holder.authorname.setText(currentPosition.getAuthorName());
        holder.titlename.setText(currentPosition.getTitleName());
       //   holder.mImageView.setImageResource(Integer.parseInt(currentPosition.getImage()));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public void add(BookData eeee) {
        mArrayList.add(eeee);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView titlename, authorname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        //      mImageView = (ImageView)itemView.findViewById(R.id.bookImage);
            titlename = (TextView) itemView.findViewById(R.id.titleName);
             authorname=(TextView)itemView.findViewById(R.id.authorName);
        }
    }

    public void clear() {
        if (mArrayList != null && !mArrayList.isEmpty()) {
            int size = mArrayList.size();
            mArrayList.clear();
            notifyItemRangeRemoved(0, size);
            notifyDataSetChanged();
        }
    }

    public void addAll(ArrayList<BookData> data) {
        Log.d(TAG, "addAll: Now addall is executing");
        mArrayList.addAll(data);
        notifyDataSetChanged();
    }
}

