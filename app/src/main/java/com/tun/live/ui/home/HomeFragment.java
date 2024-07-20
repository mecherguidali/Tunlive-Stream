package com.tun.live.ui.home;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tun.live.R;
import com.tun.live.databinding.FragmentHomeBinding;
import com.tun.live.models.movies;
import com.tun.live.models.trailer;
import com.tun.live.movies.movieListActivity;
import com.tun.live.ui.FeaturedAdapter.FeaturedAdapter;
import com.tun.live.ui.exoplayer.exoplayerActivity;
import com.tun.live.ui.slider.SliderAdapterExample;

import java.util.LinkedList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LinkedList<trailer> trailerlsit = new LinkedList<>();
    LinkedList<movies> moviesList = new LinkedList<>();
    SliderAdapterExample traileradapter;
    FeaturedAdapter movieAdapter;

    TextView moviebtn;
    public HomeFragment() {

    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        loadTrailersFromFirebase();
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
      //  LinearLayoutManager llm = new LinearLayoutManager(inflater.getContext());
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        moviebtn = (TextView) v.findViewById(R.id.moviebtn);
        moviebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), movieListActivity.class);
                v.getContext().startActivity(i);
                // Handle the click event here
              //  Toast.makeText(v.getContext(), "TextView clicked", Toast.LENGTH_SHORT).show();
            }
        });
        // setContentView(R.layout.activity_main);
        SliderView sliderView = (SliderView) v.findViewById(R.id.imageSlider);
        traileradapter = new SliderAdapterExample(v.getContext(),trailerlsit);
        sliderView.setSliderAdapter(traileradapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(6); ///set scroll delay in seconds :
        sliderView.startAutoCycle();
        ///load the movieAdapter
        loadfeaturedatamovie(v);



        for (trailer trailer : trailerlsit) {
            Log.d("Trailer Info", "Title: " + trailer.getTtitle() + ", Poster: " + trailer.getTposter() + ", Video ID: " + trailer.getTvid());
        }
        //renwitems(sliderView);
       // renwitems(sliderView);
        return v;
    }
  void loadfeaturedatamovie(View v){


     loadmoviesFromFirebase();
      RecyclerView recyclerViewMovie = (RecyclerView) v.findViewById(R.id.recMovie);
      movieAdapter = new FeaturedAdapter(v.getContext(),moviesList);
      recyclerViewMovie.setAdapter(movieAdapter);
      LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
      layoutManager.setOrientation(RecyclerView.HORIZONTAL);
      layoutManager.setReverseLayout(true);
      layoutManager.setStackFromEnd(true);
      layoutManager.canScrollHorizontally();
      recyclerViewMovie.setLayoutManager(layoutManager );

  }
    private void renwitems(View sliderView) {
        trailerlsit = new LinkedList<>();
        trailer dataitem = new trailer();
        trailerlsit.add(dataitem);
        traileradapter.renewItems(trailerlsit);
        traileradapter.deleteItem(0);

    }

    // Call this method to load trailers from Firebase
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
                            }

                            // Log the trailer list after it's populated
                           // logTrailerList();

                        } else {
                            Log.e("Firebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void loadTrailersFromFirebase() {
        db.collection("trailers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String ttitle = document.getString("ttitle");
                                String tposter = document.getString("tposter");
                                String tvid = document.getString("tvid");
                                // Create a Trailer object and add it to the list
                                trailer trailer = new trailer(ttitle, tposter, tvid);
                                trailerlsit.add(trailer);
                                traileradapter.notifyDataSetChanged();
                            }

                            // Log the trailer list after it's populated
                            logTrailerList();

                        } else {
                            Log.e("Firebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    // Call this method to log the contents of the trailerList
    private void logTrailerList() {
        for (trailer trailer : trailerlsit) {
            Log.d("Trailer Info", "Title: " + trailer.getTtitle() + ", Poster: " + trailer.getTposter() + ", Video ID: " + trailer.getTvid());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}