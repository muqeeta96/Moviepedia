package com.minsoft.moviepedia.Fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.minsoft.moviepedia.APIKeys.APIKey;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.DataModel.SearchResponse.SearchResponse;
import com.minsoft.moviepedia.HttpService.HttpClient;
import com.minsoft.moviepedia.ItemDecoration.GridSpaceItemDecoration;
import com.minsoft.moviepedia.RecyclerViewAdapter.SearchMovieAdapter;
import com.minsoft.moviepedia.databinding.FragmentSearchBinding;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private boolean initializeRecycleView;
    private SearchMovieAdapter searchAdapter;
    private final SearchView searchView;

    public SearchFragment(SearchView searchView) {
        initializeRecycleView = false;
        this.searchView = searchView;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setTopMargin();
        searchViewQueryListener();
    }

    public void searchViewQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = newText.trim();
                if (query.length() >= 1) {
                    getSearchMovieResults(query);
                }
                return true;
            }
        });
    }

    public void getSearchMovieResults(String searchQuery) {
        HttpClient.getInstance().getSearchMovie(APIKey.getInstance().getTMDB_API_KEY(),
                searchQuery).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                ArrayList<Movie> searchMovieList = (ArrayList<Movie>) response.body().getSearchMovieList();

                if (searchMovieList.isEmpty()) {
                    if (initializeRecycleView && !searchAdapter.getData().isEmpty()) {
                        searchAdapter.clearData();
                    }
                    binding.txtEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.txtEmpty.setVisibility(View.GONE);

                    if (!initializeRecycleView) {
                        createRecycleView(searchMovieList);
                        initializeRecycleView = true;

                    } else {
                        searchAdapter.setData(searchMovieList);
                    }

                }


                binding.txtNoConnection.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {

                if (t instanceof IOException) {
                    if (initializeRecycleView && !searchAdapter.getData().isEmpty()) {
                        searchAdapter.clearData();
                    }
                    binding.txtEmpty.setVisibility(View.GONE);
                    binding.txtNoConnection.setVisibility(View.VISIBLE);

                }

            }
        });
    }

    public void createRecycleView(ArrayList<Movie> searchMovieList) {
        searchAdapter = new SearchMovieAdapter(requireActivity(), searchMovieList);
        binding.recycleView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recycleView.setAdapter(searchAdapter);
        binding.recycleView.addItemDecoration(new GridSpaceItemDecoration(20, 27, 13));
    }

    public void setTopMargin() {
        int dp = 56;
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, px, 0, 0);

        binding.recycleView.setLayoutParams(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}