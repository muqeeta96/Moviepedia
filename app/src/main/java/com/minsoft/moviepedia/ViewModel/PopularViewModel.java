package com.minsoft.moviepedia.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.Paging.PagingSource.PopularPagingSource;


import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class PopularViewModel extends ViewModel {
    private Flowable<PagingData<Movie>> popularPagingDataFlowable;

    public PopularViewModel() {
        getPagingData();
    }

    void getPagingData() {
        PopularPagingSource popularPagingSource = new PopularPagingSource();

        Pager<Integer, Movie> popularPager = new Pager(
                new PagingConfig(20),
                () -> popularPagingSource);

        popularPagingDataFlowable = PagingRx.getFlowable(popularPager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(popularPagingDataFlowable, coroutineScope);
    }

    public Flowable<PagingData<Movie>> getPopularPagingDataFlowable() {
        return popularPagingDataFlowable;
    }
}

