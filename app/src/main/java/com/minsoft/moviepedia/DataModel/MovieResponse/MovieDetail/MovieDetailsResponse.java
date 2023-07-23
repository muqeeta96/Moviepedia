package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail;

import com.google.gson.annotations.SerializedName;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Credit.CreditResponse;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider.ProviderResponse;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Video.VideoResponse;

import java.util.List;

public class MovieDetailsResponse {
    private List<Genre> genres;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String poster;
    private String release_date;
    private Integer runtime;
    private String status;
    private String title;
    private Double vote_average;
    @SerializedName("videos")
    private VideoResponse trailer;
    @SerializedName("watch/providers")
    private ProviderResponse provider;
    private CreditResponse credits;

    public List<Genre> getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public VideoResponse getTrailer() {
        return trailer;
    }

    public ProviderResponse getProvider() {
        return provider;
    }

    public CreditResponse getCredits() {
        return credits;
    }
}
