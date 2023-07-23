package com.minsoft.moviepedia.DataModel.MovieResponse;

import com.google.gson.annotations.SerializedName;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> MovieList;

    public List<Movie> getMovieList() {
        return MovieList;
    }
}
