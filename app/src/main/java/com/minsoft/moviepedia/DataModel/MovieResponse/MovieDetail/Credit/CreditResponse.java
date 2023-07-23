package com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Credit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditResponse {
    @SerializedName("cast")
    private List<Cast> castList;

    public List<Cast> getCastList() {
        return castList;
    }
}
