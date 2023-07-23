package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Video;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("key")
    private String link;
    private String type;

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }
}
