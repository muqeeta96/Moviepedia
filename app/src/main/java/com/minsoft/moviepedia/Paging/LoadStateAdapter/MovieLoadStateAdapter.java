package com.minsoft.moviepedia.Paging.LoadStateAdapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.minsoft.moviepedia.R;
import com.minsoft.moviepedia.databinding.LoadStateItemBinding;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MovieLoadStateAdapter extends LoadStateAdapter<MovieLoadStateAdapter.MovieLoadStateViewHolder> {
    private final Context context;
    private final RetryConnection retryConnection;

    public MovieLoadStateAdapter(Context context, RetryConnection retryConnection) {
        this.context = context;
        this.retryConnection = retryConnection;

    }

    @Override
    public void onBindViewHolder(@NotNull MovieLoadStateViewHolder movieLoadStateViewHolder, @NotNull LoadState loadState) {
        movieLoadStateViewHolder.bind(loadState);
    }

    @NotNull
    @Override
    public MovieLoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        return new MovieLoadStateViewHolder(viewGroup, context, retryConnection);
    }

    public class MovieLoadStateViewHolder extends RecyclerView.ViewHolder {
        private final SpinKitView barLoading;
        private final ImageView imgRetry;

        public MovieLoadStateViewHolder(@NonNull ViewGroup parent, Context context, RetryConnection retryConnection) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_state_item, parent, false));

            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            barLoading = binding.barLoading;
            imgRetry = binding.imgRetry;
        }


        public void bind(LoadState loadState) {
            if (loadState instanceof LoadState.Loading) {
                barLoading.setVisibility(View.VISIBLE);
                imgRetry.setVisibility(View.GONE);

            } else if (loadState instanceof LoadState.Error) {

                barLoading.setVisibility(View.VISIBLE);
                imgRetry.setVisibility(View.GONE);

                checkInternetConnectivity();

            } else {
                barLoading.setVisibility(View.GONE);
            }

        }

        public void checkInternetConnectivity() {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo == null || !networkInfo.isConnected()) {
                        NoConnectionViewChange();
                    } else {

                        if (isInternetAvailable()) {
                            ConnectionViewChange();
                        } else {
                            NoConnectionViewChange();
                        }
                    }

                }
            }, 1000);

        }

        public boolean isInternetAvailable() {
            try {
                InetAddress address = InetAddress.getByName("www.google.com");
                return !address.equals("");
            } catch (UnknownHostException ignored) {
            }
            return false;
        }

        public void NoConnectionViewChange() {
            barLoading.setVisibility(View.GONE);
            imgRetry.setVisibility(View.VISIBLE);

            Toast.makeText(context, "Unable to connect to Internet", Toast.LENGTH_SHORT).show();

            imgRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retryConnection.retry();
                }
            });
        }

        public void ConnectionViewChange() {
            barLoading.setVisibility(View.GONE);
            imgRetry.setVisibility(View.GONE);

            retryConnection.retry();
        }

    }

}
