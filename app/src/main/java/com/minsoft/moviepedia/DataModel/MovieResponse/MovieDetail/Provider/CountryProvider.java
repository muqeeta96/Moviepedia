package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryProvider {

    @SerializedName("flatrate")
    private List<TypeProvider> flaterateProviderList;
    @SerializedName("buy")
    private List<TypeProvider> buyProviderList;
    @SerializedName("rent")
    private List<TypeProvider> rentProviderList;

    public List<TypeProvider> getFlaterateProviderList() {
        return flaterateProviderList;
    }

    public List<TypeProvider> getRentProviderList() {
        return rentProviderList;
    }

    public List<TypeProvider> getBuyProviderList() {
        return buyProviderList;
    }
}
