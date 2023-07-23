package com.minsoft.moviepedia.DataModel.SearchResponse;

import com.google.gson.annotations.SerializedName;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;

import java.util.List;

public class SearchResponse {
    @SerializedName("results")
    private List<Movie> searchMovieList;

    public List<Movie> getSearchMovieList() {
        return searchMovieList;
    }
}
