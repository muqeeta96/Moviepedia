package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("name")
    private String genre;

    public String getGenre() {
        return genre;
    }
}
