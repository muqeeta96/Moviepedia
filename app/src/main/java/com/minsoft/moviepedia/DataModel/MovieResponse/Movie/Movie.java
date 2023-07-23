package com.minsoft.moviepedia.DataModel.MovieResponse.Movie;

import com.google.gson.annotations.SerializedName;

public class Movie {

    private Integer id;
    private String title;
    @SerializedName("poster_path")
    private String poster;
    private Double vote_average;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public Double getVote_average() {
        return vote_average;
    }
}
