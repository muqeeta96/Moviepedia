package com.minsoft.moviepedia.HttpService;

import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.MovieDetailsResponse;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieResponse;
import com.minsoft.moviepedia.DataModel.SearchResponse.SearchResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpRequest {

    @GET("movie/popular")
    Single<MovieResponse> getPopularMovieList(@Query("api_key") String TMDB_Api_Key, @Query("page") int page);

    @GET("movie/top_rated")
    Single<MovieResponse> getTopRatedMovieList(@Query("api_key") String TMDB_Api_Key, @Query("page") int page);

    @GET("movie/upcoming")
    Single<MovieResponse> getUpcomingMovieList(@Query("api_key") String TMDB_Api_Key, @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int movie_id, @Query("api_key") String TMDB_Api_Key
            , @Query("append_to_response") String Query);

    @GET("search/movie")
    Call<SearchResponse> getSearchMovie(@Query("api_key") String TMDB_Api_Key
            , @Query("query") String movieName);

    @GET("movie/{movie_id}/recommendations")
    Call<MovieResponse> getRecommendationMovieList(@Path("movie_id") int movie_id, @Query("api_key") String TMDB_Api_Key);

    @GET("movie/{movie_id}/similar")
    Call<MovieResponse> getSimilarMovieList(@Path("movie_id") int movie_id, @Query("api_key") String TMDB_Api_Key);
}
