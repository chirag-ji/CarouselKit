package com.chiragji.carouselkit.adapters;

import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.chiragji.carouselkit.fragments.GalleryViewFragment;
import com.chiragji.carouselkit.fragments.MainViewFragment;
/**
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public class ViewAdapter extends FragmentStateAdapter {
    private Fragment[] fragments;

    public ViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        initViews();
    }

    private void initViews() {
        fragments = new Fragment[2];
        fragments[0] = MainViewFragment.newInstance();
        fragments[1] = GalleryViewFragment.newInstance((MainViewFragment) fragments[0]);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }


    @Override
    public int getItemCount() {
        return fragments.length;
    }
}