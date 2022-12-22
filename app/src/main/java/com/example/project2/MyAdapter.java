package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String nameList[];
    private String directorList[];//data: the names displayed
    rvClickListener rvListener;
    ArrayList<Integer> movieImages;
    private Context context;
    cmClickListener cmListener;

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(String theList[], String theList2[], ArrayList<Integer> theImages, rvClickListener listener, cmClickListener cListener){
        movieImages = theImages;
        nameList = theList;
        directorList = theList2;
        rvListener = listener;
        cmListener = cListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, rvListener, cmListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movieName.setText(nameList[position]);
        holder.directorName.setText(directorList[position]);
        holder.image.setPadding(8, 8, 8, 8);
        holder.image.setImageResource(movieImages.get(position));
    }

    @Override
    public int getItemCount() {
        return 9;
    }




    /*
        This class creates a wrapper object around a view that contains the layout for
         an individual item in the list. It also implements the onClickListener so each ViewHolder in the list is clickable.
        It's onclick method will call the onClick method of the RVClickListener defined in
        the main activity.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView movieName;
        public TextView directorName;
        public ImageView image;
        private rvClickListener listener;
        private cmClickListener listener2;
        private View itemView;

        public ViewHolder(@NonNull View itemView, rvClickListener passed, cmClickListener cpassed) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.movie_name);
            directorName = (TextView) itemView.findViewById(R.id.director_name);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this);
            this.listener = passed;
            this.listener2 = cpassed;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu);
            menu.getItem(0).setOnMenuItemClickListener(onMenu);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);
            menu.getItem(2).setOnMenuItemClickListener(onMenu);
        }

        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                listener2.onClick(itemView, getAdapterPosition(), item);
                return true;
            }
        };
    }
}
