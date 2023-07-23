package com.minsoft.moviepedia.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubePlayer;
import com.minsoft.moviepedia.Activities.MovieDetailsActivity;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.databinding.SeeMoreMovieItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SeeMoreMovieAdapter extends RecyclerView.Adapter<SeeMoreMovieAdapter.SeeMoreMovieViewHolder> {
    private final Context context;
    private final ArrayList<Movie> seeMoreMovieList;
    public SeeMoreMovieAdapter(Context context, ArrayList<Movie> seeMoreMovieList) {
        super();
        this.context = context;
        this.seeMoreMovieList = seeMoreMovieList;
    }

    @NonNull
    @Override
    public SeeMoreMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeeMoreMovieAdapter.SeeMoreMovieViewHolder(SeeMoreMovieItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeeMoreMovieViewHolder holder, int position) {
        Movie curr_movie = seeMoreMovieList.get(position);
        if (curr_movie != null) {
            setUi(curr_movie, holder);
            openDetailActivity(curr_movie.getId(), holder);
        }
    }

    public void setUi(Movie curr_movie, SeeMoreMovieViewHolder holder) {
        holder.binding.txtTitle.setText(curr_movie.getTitle());

        DecimalFormat df = new DecimalFormat("#.#");
        holder.binding.txtRating.setText(String.valueOf(df.format(curr_movie.getVote_average())));

        setPoster(curr_movie, holder);
    }

    public void setPoster(Movie curr_movie, SeeMoreMovieViewHolder holder) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + curr_movie.getPoster())
                .into(holder.binding.imgPoster);
    }

    public void openDetailActivity(int movie_id, SeeMoreMovieViewHolder holder) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie_id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seeMoreMovieList.size();
    }

    public class SeeMoreMovieViewHolder extends RecyclerView.ViewHolder {
        private final SeeMoreMovieItemBinding binding;

        public SeeMoreMovieViewHolder(@NonNull SeeMoreMovieItemBinding seeMoreMovieItemBinding) {
            super(seeMoreMovieItemBinding.getRoot());
            this.binding = seeMoreMovieItemBinding;
        }
    }
}
