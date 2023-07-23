package com.minsoft.moviepedia.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.Paging.PagingSource.UpcomingPagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class UpcomingViewModel extends ViewModel {
    private Flowable<PagingData<Movie>> upcomingPagingDataFlowable;

    public UpcomingViewModel() {
        getPagingData();
    }

    void getPagingData() {
        UpcomingPagingSource upcomingPagingSource = new UpcomingPagingSource();

        Pager<Integer, Movie> upcomingPager = new Pager(
                new PagingConfig(20),
                () -> upcomingPagingSource);

        upcomingPagingDataFlowable = PagingRx.getFlowable(upcomingPager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(upcomingPagingDataFlowable, coroutineScope);
    }

    public Flowable<PagingData<Movie>> getUpcomingPagingDataFlowable() {
        return upcomingPagingDataFlowable;
    }
}



