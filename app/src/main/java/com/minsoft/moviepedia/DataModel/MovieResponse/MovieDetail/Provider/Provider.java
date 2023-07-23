package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider;

import com.google.gson.annotations.SerializedName;

public class Provider {

    @SerializedName("US")
    private CountryProvider ProviderToUS;

    public CountryProvider getProviderToUS() {
        return ProviderToUS;
    }
}
