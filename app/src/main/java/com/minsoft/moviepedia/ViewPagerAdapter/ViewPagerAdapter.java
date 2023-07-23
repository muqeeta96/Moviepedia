package com.minsoft.moviepedia.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.minsoft.moviepedia.Fragments.PopularFragment;
import com.minsoft.moviepedia.Fragments.TopRatedFragment;
import com.minsoft.moviepedia.Fragments.UpcomingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PopularFragment();
            case 1:
                return new TopRatedFragment();
            case 2:
                return new UpcomingFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
