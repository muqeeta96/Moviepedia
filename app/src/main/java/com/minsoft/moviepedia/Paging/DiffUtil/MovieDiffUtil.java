package com.minsoft.moviepedia.Paging.DiffUtil;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;

import org.jetbrains.annotations.NotNull;

public class MovieDiffUtil extends DiffUtil.ItemCallback<Movie> {

    @Override
    public boolean areItemsTheSame(@NonNull @NotNull Movie oldItem, @NonNull @NotNull Movie newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull @NotNull Movie oldItem, @NonNull @NotNull Movie newItem) {
        return (oldItem.getTitle().equals(newItem.getTitle()));
    }
}
