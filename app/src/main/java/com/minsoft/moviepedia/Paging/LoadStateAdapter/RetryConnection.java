package com.minsoft.moviepedia.Paging.LoadStateAdapter;

import com.minsoft.moviepedia.Paging.PagingDataAdapter.MovieDataAdapter;

public class RetryConnection {
    private final MovieDataAdapter movieDataAdapter;

    public RetryConnection(MovieDataAdapter movieDataAdapter) {
        this.movieDataAdapter = movieDataAdapter;

    }

    public void retry() {
        movieDataAdapter.retry();
    }
}
