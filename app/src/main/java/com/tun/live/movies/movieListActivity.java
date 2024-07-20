package com.tun.live.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tun.live.R;
import com.tun.live.databinding.FragmentHomeBinding;
import com.tun.live.models.movies;
import com.tun.live.ui.FeaturedAdapter.FeaturedAdapter;

import java.util.LinkedList;

public class movieListActivity extends AppCompatActivity {
    LinkedList<movies> moviesList = new LinkedList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FeaturedAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        loadmoviesFromFirebase();
        for (movies trailer : moviesList) {
            Log.d("Trailer Info", "Title: " + trailer.getTitle() + ", Poster: " + trailer.getPoster() + ", Video ID: " + trailer.getSource());
        }
        RecyclerView recyclerView = findViewById(R.id.movieListcycle);
        movieAdapter = new FeaturedAdapter(getApplicationContext(), moviesList);
        recyclerView.setAdapter(movieAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        layoutManager.canScrollHorizontally();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

// Set the activity title
        setTitle("Movie List");
      /*  View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN

        );

       */


    }
    private void loadmoviesFromFirebase() {
        db.collection("movies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getString("title");
                                String poster = document.getString("poster");
                                String source = document.getString("source");
                                String thmub = document.getString("thmub");
                                String description = document.getString("description");
                                // Create a Trailer object and add it to the list
                                movies mv = new movies(description,source,thmub,title,poster);
                                moviesList.add(mv);
                                movieAdapter.notifyDataSetChanged();
                                Log.e("Firebase", " work fire documents: ", task.getException());
                            }

                            // Log the trailer list after it's populated
                            // logTrailerList();

                        } else {
                            Log.e("Firebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}