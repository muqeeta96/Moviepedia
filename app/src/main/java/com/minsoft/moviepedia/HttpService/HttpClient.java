package com.minsoft.moviepedia.HttpService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static HttpRequest instance;

    public static HttpRequest getInstance() {
        if (instance == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            instance = retrofit.create(HttpRequest.class);
        }
        return instance;
    }
}
