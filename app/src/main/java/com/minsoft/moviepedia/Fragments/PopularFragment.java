package com.minsoft.moviepedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.minsoft.moviepedia.Paging.DiffUtil.MovieDiffUtil;
import com.minsoft.moviepedia.ItemDecoration.GridSpaceItemDecoration;
import com.minsoft.moviepedia.Paging.LoadStateAdapter.MovieLoadStateAdapter;
import com.minsoft.moviepedia.Paging.LoadStateAdapter.RetryConnection;
import com.minsoft.moviepedia.Paging.PagingDataAdapter.MovieDataAdapter;
import com.minsoft.moviepedia.ViewModel.PopularViewModel;
import com.minsoft.moviepedia.databinding.MovieListBinding;

import org.jetbrains.annotations.NotNull;

import autodispose2.AutoDispose;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;

public class PopularFragment extends Fragment {
    private MovieListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = MovieListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loadingBar.setVisibility(View.VISIBLE);

        PopularViewModel popularViewModel = new ViewModelProvider(requireActivity())
                .get(PopularViewModel.class);

        MovieDiffUtil movieDiffUtil = new MovieDiffUtil();
        MovieDataAdapter movieDataAdapter = new MovieDataAdapter(movieDiffUtil, requireActivity(), binding.loadingBar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);

        RetryConnection retryConnection = new RetryConnection(movieDataAdapter);
        binding.recycleView.setLayoutManager(gridLayoutManager);
        binding.recycleView.setAdapter(movieDataAdapter);
        binding.recycleView.setAdapter(movieDataAdapter.withLoadStateFooter(new MovieLoadStateAdapter(requireActivity(), retryConnection)));
        binding.recycleView.addItemDecoration(new GridSpaceItemDecoration(20, 26, 9));

        popularViewModel.getPopularPagingDataFlowable()
                .to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(requireActivity())))
                .subscribe(popularPagingData -> movieDataAdapter.submitData(getLifecycle(), popularPagingData));

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return movieDataAdapter.getItemViewType(position) == MovieDataAdapter.LOADING_ITEM ? 1 : 2;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
