package com.minsoft.moviepedia.Paging.PagingSource;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.minsoft.moviepedia.APIKeys.APIKey;
import com.minsoft.moviepedia.HttpService.HttpClient;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieResponse;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopRatedPagingSource extends RxPagingSource<Integer, Movie> {
    @NotNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NotNull LoadParams<Integer> loadParams) {

        int pageNo;
        pageNo = loadParams.getKey() == null ? 1 : loadParams.getKey();

        return HttpClient.getInstance()
                .getTopRatedMovieList(APIKey.getInstance().getTMDB_API_KEY(), pageNo)
                .subscribeOn(Schedulers.io())
                .map(MovieResponse::getMovieList)
                .map(topRatedMovieList -> toLoadResult(topRatedMovieList, pageNo))
                .onErrorReturn(LoadResult.Error::new);

    }

    public LoadResult<Integer, Movie> toLoadResult(List<Movie> topRatedMovieList, Integer page) {
        return new LoadResult.Page(topRatedMovieList, page == 1 ? null : page - 1, page + 1);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, Movie> pagingState) {

        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Movie> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}


