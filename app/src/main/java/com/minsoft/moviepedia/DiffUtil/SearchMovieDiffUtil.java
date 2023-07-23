package com.minsoft.moviepedia.DiffUtil;

import androidx.recyclerview.widget.DiffUtil;

import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;

import java.util.ArrayList;

public class SearchMovieDiffUtil extends DiffUtil.Callback {
    private ArrayList<Movie> oldList;
    private ArrayList<Movie> newList;

    public SearchMovieDiffUtil(ArrayList<Movie> oldList, ArrayList<Movie> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
    }
}
