package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView RVMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RVMovies = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList<Integer> movieImages = new ArrayList<Integer>(
                Arrays.asList(R.drawable.movie1, R.drawable.movie2, R.drawable.movie3,
                        R.drawable.movie4, R.drawable.movie5, R.drawable.movie6,
                        R.drawable.movie7, R.drawable.movie8, R.drawable.movie9));

        rvClickListener listener = (view, position) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getStringArray(R.array.movieslink)[position]));
            startActivity(browserIntent);
        };

        cmClickListener cmlistener = (view, position, item) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            if (item.getTitle().toString().equals("View Video Clip")) {
                browserIntent.setData(Uri.parse(getResources().getStringArray(R.array.extramovieslink)[position]));
            } else if (item.getTitle().toString().equals("Wikipedia Page")) {
                browserIntent.setData(Uri.parse(getResources().getStringArray(R.array.wikimovies)[position]));
            } else {
                browserIntent.setData(Uri.parse(getResources().getStringArray(R.array.directorswiki)[position]));
            }
            startActivity(browserIntent);
        };

        MyAdapter adapter = new MyAdapter(getResources().getStringArray(R.array.movies), getResources().getStringArray(R.array.directors), movieImages, listener, cmlistener);
        RVMovies.setHasFixedSize(true);
        RVMovies.setAdapter(adapter);
        RVMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        menu.getItem(0).setOnMenuItemClickListener(onMenu);
        menu.getItem(1).setOnMenuItemClickListener(onMenu);
        return true;
    }

    private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem item){
            if (item.getTitle().toString().equals("List View")) {
                RVMovies.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            } else {
                RVMovies.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
            }
            return true;
        }
    };
}