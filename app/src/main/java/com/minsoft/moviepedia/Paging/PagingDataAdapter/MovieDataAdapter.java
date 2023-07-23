package com.minsoft.moviepedia.Paging.PagingDataAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minsoft.moviepedia.Activities.MovieDetailsActivity;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.databinding.MovieItemBinding;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class MovieDataAdapter extends PagingDataAdapter<Movie, MovieDataAdapter.MovieViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int MOVIE_ITEM = 1;
    private final Context context;
    private final ProgressBar barStartLoad;

    public MovieDataAdapter(@NotNull DiffUtil.ItemCallback<Movie> diffCallback, Context context, ProgressBar barStartLoad) {
        super(diffCallback);
        this.context = context;
        this.barStartLoad = barStartLoad;
    }

    @NonNull
    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(MovieItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieViewHolder holder, int position) {
        setScaleAnimation(holder.binding.getRoot());

        Movie curr_movie = getItem(position);
        if (curr_movie != null) {
            setUi(curr_movie, holder);
            openDetailActivity(curr_movie.getId(), holder);

        }
    }

    public void setPoster(Movie curr_movie, MovieViewHolder holder) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + curr_movie.getPoster())
                .into(holder.binding.imgPoster);
    }

    public void setUi(Movie curr_movie, MovieViewHolder holder) {
        holder.binding.txtTitle.setText(curr_movie.getTitle());

        DecimalFormat df = new DecimalFormat("#.#");
        holder.binding.txtRating.setText(String.valueOf(df.format(curr_movie.getVote_average())));

        setPoster(curr_movie, holder);
    }

    public void openDetailActivity(int movie_id, MovieViewHolder holder) {
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
    public int getItemViewType(int position) {
        return position == getItemCount() ? MOVIE_ITEM : LOADING_ITEM;
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        view.startAnimation(anim);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieItemBinding binding;

        public MovieViewHolder(@NonNull @NotNull MovieItemBinding movieItemListBinding) {
            super(movieItemListBinding.getRoot());
            this.binding = movieItemListBinding;
            barStartLoad.setVisibility(View.GONE);
        }
    }
}


