package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Credit;

import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("name")
    private String actorName;

    public String getActorName() {
        return actorName;
    }
}
