package com.minsoft.moviepedia.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.Paging.PagingSource.TopRatedPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class TopRatedViewModel extends ViewModel {
    private Flowable<PagingData<Movie>> topRatedPagingDataFlowable;

    public TopRatedViewModel() {
        getPagingData();
    }

    void getPagingData() {
        TopRatedPagingSource topRatedPagingSource = new TopRatedPagingSource();

        Pager<Integer, Movie> topRatedPager = new Pager(
                new PagingConfig(20),
                () -> topRatedPagingSource);

        topRatedPagingDataFlowable = PagingRx.getFlowable(topRatedPager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(topRatedPagingDataFlowable, coroutineScope);
    }

    public Flowable<PagingData<Movie>> getTopRatedPagingDataFlowable() {
        return topRatedPagingDataFlowable;
    }
}


