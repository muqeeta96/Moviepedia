package com.minsoft.moviepedia.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minsoft.moviepedia.Activities.MovieDetailsActivity;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.DiffUtil.SearchMovieDiffUtil;
import com.minsoft.moviepedia.databinding.MovieItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder> {
    private final Context context;
    private ArrayList<Movie> searchMovieList;

    public SearchMovieAdapter(Context context, ArrayList<Movie> firstSearchMovieList) {
        super();
        this.context = context;
        this.searchMovieList = firstSearchMovieList;
    }

    @NonNull
    @Override
    public SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchMovieAdapter.SearchMovieViewHolder(MovieItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieViewHolder holder, int position) {

        setScaleAnimation(holder.binding.getRoot());
        Movie curr_movie = searchMovieList.get(position);
        if (curr_movie != null) {
            setUi(curr_movie, holder);
            openDetailActivity(curr_movie.getId(), holder);
        }

    }

    public void setUi(Movie curr_movie, SearchMovieViewHolder holder) {
        holder.binding.txtTitle.setText(curr_movie.getTitle());

        DecimalFormat df = new DecimalFormat("#.#");
        holder.binding.txtRating.setText(String.valueOf(df.format(curr_movie.getVote_average())));

        setPoster(curr_movie, holder);
    }

    public void setPoster(Movie curr_movie, SearchMovieViewHolder holder) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + curr_movie.getPoster())
                .into(holder.binding.imgPoster);
    }

    public void openDetailActivity(int movie_id, SearchMovieViewHolder holder) {
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
        return searchMovieList.size();
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        view.startAnimation(anim);
    }

    public void setData(ArrayList<Movie> newSearchMovieList) {
        SearchMovieDiffUtil searchMovieDiffUtil = new SearchMovieDiffUtil(searchMovieList, newSearchMovieList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(searchMovieDiffUtil);
        searchMovieList.clear();
        searchMovieList.addAll(newSearchMovieList);
        diffResult.dispatchUpdatesTo(this);
    }

    public ArrayList<Movie> getData() {
        return searchMovieList;
    }

    public void clearData() {
        int itemCount = searchMovieList.size();
        searchMovieList.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    public class SearchMovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieItemBinding binding;

        public SearchMovieViewHolder(@NonNull MovieItemBinding movieItemListBinding) {
            super(movieItemListBinding.getRoot());
            this.binding = movieItemListBinding;
        }

    }
}
