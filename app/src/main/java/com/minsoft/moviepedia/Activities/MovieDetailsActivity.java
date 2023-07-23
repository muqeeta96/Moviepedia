package com.minsoft.moviepedia.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.minsoft.moviepedia.APIKeys.APIKey;
import com.minsoft.moviepedia.DataModel.MovieResponse.Movie.Movie;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Credit.Cast;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Genre;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.MovieDetailsResponse;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider.Provider;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Provider.TypeProvider;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Video.Video;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieDetail.Video.VideoResponse;
import com.minsoft.moviepedia.DataModel.MovieResponse.MovieResponse;
import com.minsoft.moviepedia.HttpService.HttpClient;
import com.minsoft.moviepedia.ItemDecoration.GridSpaceItemDecoration;
import com.minsoft.moviepedia.R;
import com.minsoft.moviepedia.RecyclerViewAdapter.SeeMoreMovieAdapter;
import com.minsoft.moviepedia.Util.Util;
import com.minsoft.moviepedia.databinding.ActivityMovieDetailsBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private Util util;
    private int movie_id;
    private String video_link;
    private YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.shimmerLayout.startShimmer();

        util = new Util();
        movie_id = 0;
        video_link = null;

        addToolbar();
        getMovieDetails();
        showMoreMovies();

    }

    void addToolbar() {
        util.setStatusBarColor(this, R.color.primary_color);
        util.setToolbar(this, binding.toolbarLayout.getRoot());
    }

    void getMovieDetails() {
        movie_id = getIntent().getExtras().getInt("movie_id");
        String TMDB_Api_Key = APIKey.getInstance().getTMDB_API_KEY();
        String Query = "videos,watch/providers,credits";

        HttpClient.getInstance()
                .getMovieDetails(movie_id, TMDB_Api_Key, Query)
                .enqueue(new Callback<MovieDetailsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieDetailsResponse> call, @NonNull Response<MovieDetailsResponse> response) {
                        MovieDetailsResponse movieDetailsResponse = response.body();

                        try {
                            showTrailer(movieDetailsResponse.getTrailer());

                        } catch (Exception ignored) {
                        }

                        try {
                            showCast(movieDetailsResponse.getCredits().getCastList());

                        } catch (Exception ignored) {
                        }

                        showGenres(movieDetailsResponse.getGenres());

                        showPoster(movieDetailsResponse.getPoster());

                        showReleaseDate(movieDetailsResponse.getRelease_date());

                        showRating(movieDetailsResponse.getVote_average());

                        try {
                            showAvailableOnStreaming(movieDetailsResponse.getProvider().getProvider());

                        } catch (Exception ignored) {
                        }

                        showRunningMinutes(movieDetailsResponse.getRuntime());

                        showDescription(movieDetailsResponse.getDescription());

                        showTitle(movieDetailsResponse.getTitle());

                        showBullets();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieDetailsResponse> call, @NonNull Throwable t) {
                        Toast.makeText(MovieDetailsActivity.this, "No Connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void showTrailer(VideoResponse videoResponse) {
        if (videoResponse.getVideoList().isEmpty()) {
            binding.youtubeError.setVisibility(View.VISIBLE);
        } else {

            for (Video v : videoResponse.getVideoList()) {
                if (v.getType().equalsIgnoreCase("Trailer")) {
                    video_link = v.getLink();
                    break;
                }
            }
            if (video_link == null) {
                for (Video v : videoResponse.getVideoList()) {
                    if (v.getType().equalsIgnoreCase("Teaser")) {
                        video_link = v.getLink();
                        break;
                    }
                }
            }
            if (video_link == null) {
                for (Video v : videoResponse.getVideoList()) {
                    if (v.getType().equalsIgnoreCase("Clip")) {
                        video_link = v.getLink();
                        break;
                    }
                }
            }
            if (video_link == null) {
                binding.youtubeError.setVisibility(View.VISIBLE);
            } else {
                initializeYoutubePlayer();
            }
        }
    }

    public void initializeYoutubePlayer() {
        YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.fragYoutube);
        youTubePlayerFragment.initialize(APIKey.getInstance().getYOUTUBE_API_KEY(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer1, boolean b) {
                youTubePlayer = youTubePlayer1;
                youTubePlayer.cueVideo(video_link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    public void showBullets() {
        binding.bullet1.setVisibility(View.VISIBLE);
        binding.bullet2.setVisibility(View.VISIBLE);
    }

    void showCast(List<Cast> castList) {
        binding.Cast.setText("Cast:-");
        StringBuilder fullCast = new StringBuilder();
        int count = 1;
        for (Cast cast : castList) {
            if (count == 5) {
                break;
            }
            if (count == 1) {
                fullCast.append(cast.getActorName());
            } else {
                fullCast.append(",\n" + cast.getActorName());
            }
            count++;
        }

        binding.txtCast.setText(fullCast);
    }

    void showPoster(String imgLink) {
        binding.imgCard.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + imgLink)
                .into(binding.imgWallpaper);

        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);

    }

    void showGenres(List<Genre> genres) {
        StringBuilder genreslist = new StringBuilder();
        for (Genre genre : genres) {
            if (genre.getGenre().equals(genres.get(0).getGenre())) {
                genreslist.append(genre.getGenre());
            } else {
                genreslist.append(", " + genre.getGenre());
            }
        }

        binding.txtGenre.setText(genreslist);

    }

    void showTitle(String title) {
        binding.txtMovieTitle.setText(title);

    }

    void showReleaseDate(String releaseDate) {
        String[] date = releaseDate.split("-");
        binding.txtDate.setText(date[0]);
    }

    void showRunningMinutes(Integer runtime) {
        String runningTime = String.valueOf(runtime) + "min";
        binding.txtTime.setText(runningTime);
    }

    void showRating(Double vote_average) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        binding.txtRating.setText(String.valueOf(decimalFormat.format(vote_average)));
    }

    void showAvailableOnStreaming(Provider provider) {
        if (provider.getProviderToUS() != null) {

            if (provider.getProviderToUS().getFlaterateProviderList() != null &&
                    !provider.getProviderToUS().getFlaterateProviderList().isEmpty()) {

                for (TypeProvider flatrateProvider : provider.getProviderToUS().getFlaterateProviderList()) {
                    if (flatrateProvider.getProvider_name().equalsIgnoreCase("Netflix")) {
                        Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500" + flatrateProvider.getProvider_logo())
                                .into(binding.streamOnNetflix);

                        binding.streamAvailable.setText("Available On:");
                        binding.streamAvailable.setVisibility(View.VISIBLE);
                        binding.streamOnNetflix.setVisibility(View.VISIBLE);

                    } else if (flatrateProvider.getProvider_name().equalsIgnoreCase("Amazon Prime Video")
                            || flatrateProvider.getProvider_name().equalsIgnoreCase("Amazon Video")) {
                        Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500" + flatrateProvider.getProvider_logo())
                                .into(binding.streamOnAmazon);
                        binding.streamAvailable.setText("Available On:");
                        binding.streamAvailable.setVisibility(View.VISIBLE);
                        binding.streamOnAmazon.setVisibility(View.VISIBLE);
                    }
                }
            } else if (provider.getProviderToUS().getBuyProviderList() != null &&
                    !provider.getProviderToUS().getBuyProviderList().isEmpty()) {

                for (TypeProvider buyProvider : provider.getProviderToUS().getBuyProviderList()) {

                    if (buyProvider.getProvider_name().equalsIgnoreCase("Netflix")) {
                        Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500" + buyProvider.getProvider_logo())
                                .into(binding.streamOnNetflix);
                        binding.streamAvailable.setText("Available On:");
                        binding.streamAvailable.setVisibility(View.VISIBLE);
                        binding.streamOnNetflix.setVisibility(View.VISIBLE);
                    } else if (buyProvider.getProvider_name().equalsIgnoreCase("Amazon Prime Video")
                            || buyProvider.getProvider_name().equalsIgnoreCase("Amazon Video")) {

                        Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500" + buyProvider.getProvider_logo())
                                .into(binding.streamOnAmazon);
                        binding.streamAvailable.setText("Available On:");
                        binding.streamAvailable.setVisibility(View.VISIBLE);
                        binding.streamOnAmazon.setVisibility(View.VISIBLE);
                    }
                }
            } else if (provider.getProviderToUS().getRentProviderList() != null &&
                    !provider.getProviderToUS().getRentProviderList().isEmpty()) {

                for (TypeProvider rentProvider : provider.getProviderToUS().getRentProviderList()) {
                    if (rentProvider.getProvider_name().equalsIgnoreCase("Netflix")) {
                        Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500" + rentProvider.getProvider_logo())
                                .into(binding.streamOnNetflix);
                        binding.streamAvailable.setText("Available On:");
                        binding.streamAvailable.setVisibility(View.VISIBLE);
                        binding.streamOnNetflix.setVisibility(View.VISIBLE);

                    } else if (rentProvider.getProvider_name().equalsIgnoreCase("Amazon Prime Video")
                            || rentProvider.getProvider_name().equalsIgnoreCase("Amazon Video")) {
                        Glide.with(this)
                                .load("https://image.tmdb.org/t/p/w500" + rentProvider.getProvider_logo())
                                .into(binding.streamOnAmazon);
                        binding.streamAvailable.setText("Available On:");
                        binding.streamAvailable.setVisibility(View.VISIBLE);
                        binding.streamOnAmazon.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    void showDescription(String description) {
        binding.txtstory.setText("Story:");

        binding.txtDescription.setText("       " + description);
        binding.txtDescription.post(new Runnable() {
            @Override
            public void run() {
                int line_count = 6;
                if (binding.txtDescription.getLineCount() > line_count) {
                    binding.txtDescription.setMaxLines(line_count);
                    binding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);

                    binding.txtDescription.setOnClickListener(new View.OnClickListener() {
                        private boolean expandDescription = false;

                        @Override
                        public void onClick(View view) {
                            if (!expandDescription) {
                                binding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                                expandDescription = true;
                            } else {
                                binding.txtDescription.setMaxLines(line_count);
                                binding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                                expandDescription = false;
                            }
                        }
                    });
                }
                binding.txtDescription.setVisibility(View.VISIBLE);
            }

        });

    }

    void showMoreMovies() {
        final boolean[] recommendationMoviesFound = {false};
        HttpClient.getInstance().getRecommendationMovieList(movie_id, APIKey.getInstance().getTMDB_API_KEY()).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                ArrayList<Movie> recommendationMovieList = (ArrayList<Movie>) response.body().getMovieList();

                if (recommendationMovieList != null && !recommendationMovieList.isEmpty()) {
                    createRecycleView(recommendationMovieList);
                    recommendationMoviesFound[0] = true;

                }

            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });

        if (!recommendationMoviesFound[0]) {
            HttpClient.getInstance().getSimilarMovieList(movie_id, APIKey.getInstance().getTMDB_API_KEY()).enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    ArrayList<Movie> similarMovieList = (ArrayList<Movie>) response.body().getMovieList();

                    if (similarMovieList != null && !similarMovieList.isEmpty()) {
                        createRecycleView(similarMovieList);

                    }

                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.d("ERROR", t.getMessage());
                }
            });
        }
    }

    public void createRecycleView(ArrayList<Movie> moreMovieList) {
        if (moreMovieList != null || !moreMovieList.isEmpty()) {
            SeeMoreMovieAdapter seeMoreMovieAdapter = new SeeMoreMovieAdapter(this, moreMovieList);
            binding.moreMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.moreMoviesRecyclerView.setAdapter(seeMoreMovieAdapter);
            binding.moreMoviesRecyclerView.addItemDecoration(new GridSpaceItemDecoration(0, 0, 5, 5));
        } else {
            binding.more.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onPause() {
        youTubePlayer.release();
        super.onPause();
    }
}