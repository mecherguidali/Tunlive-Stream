package com.tun.live.ui.FeaturedAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tun.live.R;
import com.tun.live.models.movies;
import com.tun.live.movies.Detail_movie;
import com.tun.live.movies.movieListActivity;
import com.tun.live.ui.exoplayer.exoplayerActivity;

import java.util.LinkedList;
import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {
    private LinkedList<movies> mData;
    private LayoutInflater mInflater;
    private Context context;

    public FeaturedAdapter(Context context, LinkedList<movies> mData) {
        this.context =context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
       // Log.v("adapter", "aaaaaaaaaaa");
        View view = mInflater.inflate(R.layout.movie_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        movies item = mData.get(position);
        holder.mtitle.setText(item.getTitle());
        Glide.with(holder.itemView)
                .load(item.getThmub())
                .fitCenter()
                .into(holder.imagetump);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Detail_movie.class);
                i.putExtra("movie",item);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);


                ///  Toast.makeText(context, "This is item in position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mtitle;
        ImageView imagetump;
        TextView decr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagetump = itemView.findViewById(R.id.thump);
           mtitle = itemView.findViewById(R.id.mtitle);
            decr = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

        }
    }

}
