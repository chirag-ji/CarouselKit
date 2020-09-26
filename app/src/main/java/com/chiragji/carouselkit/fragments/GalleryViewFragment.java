package com.chiragji.carouselkit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chiragji.carouselkit.R;
import com.github.chiragji.gallerykit.GalleryKitView;
import com.github.chiragji.gallerykit.callbacks.GalleryKitListener;

/**
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public class GalleryViewFragment extends Fragment {
    private GalleryKitListener listener;

    public static GalleryViewFragment newInstance(@NonNull GalleryKitListener listener) {
        GalleryViewFragment fragment = new GalleryViewFragment(listener);
        fragment.setArguments(new Bundle());
        return fragment;
    }

    public GalleryViewFragment(@NonNull GalleryKitListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GalleryKitView galleryKitView = view.findViewById(R.id.galleryKitView);
        galleryKitView.attachToFragment(this);
        galleryKitView.registerGalleryKitListener(listener);
    }
}