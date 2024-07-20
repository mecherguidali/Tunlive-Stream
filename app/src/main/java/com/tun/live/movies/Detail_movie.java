package com.tun.live.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tun.live.R;
import com.tun.live.models.movies;
import com.tun.live.ui.exoplayer.exoplayerActivity;

public class Detail_movie extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
   private movies movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Button button = findViewById(R.id.playbtn);




        Intent intent = getIntent();
       // String movieId=  intent.getStringExtra("movie");
movies movie = (movies) intent.getSerializableExtra("movie");
        if (movie != null) {
           // getMovieById(movieId);
            TextView tx = findViewById(R.id.titled);
            TextView txdescription = findViewById(R.id.description);
            ImageView tmgp = findViewById(R.id.thumpd);
            ImageView imgp = findViewById(R.id.posterp);
            Glide.with(imgp)
                    .load(movie.getPoster())
                    .fitCenter()
                    .into(imgp);
            tx.setText("Title: "+movie.getTitle());
            Glide.with(tmgp)
                    .load(movie.getThmub())
                    .fitCenter()
                    .into(tmgp);
            txdescription.setText(movie.getDescription());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform action when button is clicked
                    Intent i = new Intent(getApplicationContext(), exoplayerActivity.class);
                    i.putExtra("url",movie.getSource());
                    v.getContext().startActivity(i);

                }
            });

        }


    }
    private void getMovieById(String movieId) {
        db.collection("movies").document(movieId) // Specify the document ID
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Document found, extract movie details
                                String title = document.getString("title");
                                String poster = document.getString("poster");
                                String source = document.getString("source");
                                String thumbnail = document.getString("thumbnail");

                                // Create a Movie object with the retrieved details
                                 movie = new movies(source, thumbnail, title, poster);

                                // Now you can use 'movie' object as needed
                                // For example, display movie details on UI
                            } else {
                                // Document not found
                                Log.e("Firebase", "No such document");
                            }
                        } else {
                            // Error occurred while fetching document
                            Log.e("Firebase", "Error getting document", task.getException());
                        }
                    }
                });
    }


}