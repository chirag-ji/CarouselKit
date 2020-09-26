package com.chiragji.carouselkit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chiragji.carouselkit.AppActivity;
import com.chiragji.carouselkit.R;
import com.github.chiragji.carouselkit.CarouselItem;
import com.github.chiragji.carouselkit.CarouselView;
import com.github.chiragji.gallerykit.callbacks.GalleryKitListener;

import java.util.List;

/**
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public class MainViewFragment extends Fragment implements GalleryKitListener {
    private CarouselView carousel;

    public static MainViewFragment newInstance() {
        MainViewFragment fragment = new MainViewFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        carousel = view.findViewById(R.id.carousel);
        carousel.attach(this);
        carousel.setUsingCustomPageIndicator(view.findViewById(R.id.pageIndicatorCustom));
        view.findViewById(R.id.galleryBtn).setOnClickListener(l -> AppActivity.toggleGallery(true));
    }

    @Override
    public void onGalleryKitBackAction() {
        AppActivity.toggleGallery(false);
    }

    @Override
    public void onGalleryKitSelectionConfirmed(@NonNull List<String> selectedDataUris) {
        AppActivity.toggleGallery(false);
        CarouselItem[] items = new CarouselItem[selectedDataUris.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = new CarouselItem(selectedDataUris.get(i));
        }
        carousel.setCarouselItems(items);
    }
}