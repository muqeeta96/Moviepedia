package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Video;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("results")
    private List<Video> videoList;

    public List<Video> getVideoList() {
        return videoList;
    }
}
