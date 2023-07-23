package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider;

import com.google.gson.annotations.SerializedName;

public class TypeProvider {
    private String provider_name;
    @SerializedName("logo_path")
    private String provider_logo;

    public String getProvider_name() {
        return provider_name;
    }

    public String getProvider_logo() {
        return provider_logo;
    }
}
