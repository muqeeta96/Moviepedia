package com.minsoft.moviepedia.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.minsoft.moviepedia.Fragments.SearchFragment;
import com.minsoft.moviepedia.R;
import com.minsoft.moviepedia.Util.Util;
import com.minsoft.moviepedia.ViewPagerAdapter.ViewPagerAdapter;
import com.minsoft.moviepedia.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private Bundle savedInstanceState;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.savedInstanceState = savedInstanceState;
        util = new Util();

        addToolbar();
        createFragmentTabs();

    }

    void addToolbar() {
        util.setStatusBarColor(this, R.color.primary_color);
        setSupportActionBar(binding.toolbarLayout.getRoot());
    }

    void createFragmentTabs() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        binding.viewPager2.setAdapter(viewPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.popular);
                        break;
                    case 1:
                        tab.setText(R.string.top_rated);
                        break;
                    case 2:
                        tab.setText(R.string.upcoming);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        setSearchViewUI(searchView);
        onSearchIconClickListener(searchItem, searchView);

        return true;
    }

    public void onSearchIconClickListener(MenuItem searchItem, SearchView searchView) {
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                binding.viewPager2.setVisibility(View.GONE);
                binding.tabLayout.setVisibility(View.GONE);

                if (savedInstanceState == null) {
                    SearchFragment searchFragment = new SearchFragment(searchView);
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack("searchFrag")
                            .add(R.id.searchFrag, searchFragment, "searchFragment")
                            .commit();
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                binding.viewPager2.setVisibility(View.VISIBLE);
                binding.tabLayout.setVisibility(View.VISIBLE);

                HomeActivity.super.onBackPressed();

                return true;
            }
        });

    }

    public void setSearchViewUI(SearchView searchView) {
        searchView.setQueryHint("Search Movie by Name");

        TextView text = searchView.findViewById(R.id.search_src_text);
        text.setTextColor(getResources().getColor(R.color.white, null));
        text.setHintTextColor(getResources().getColor(R.color.trans_white, null));

        ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
        closeIcon.setColorFilter(getResources().getColor(R.color.white, null));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}