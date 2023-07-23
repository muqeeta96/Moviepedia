package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider;

import com.google.gson.annotations.SerializedName;

public class ProviderResponse {
    @SerializedName("results")
    private Provider provider;

    public Provider getProvider() {
        return provider;
    }
}
