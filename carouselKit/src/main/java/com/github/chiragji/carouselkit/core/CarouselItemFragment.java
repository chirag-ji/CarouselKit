package com.github.chiragji.carouselkit.core;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chiragji.carouselkit.CarouselItem;
import com.github.chiragji.carouselkit.R;
import com.github.chiragji.carouselkit.callbacks.CarouselFocusListener;
import com.github.chiragji.carouselkit.enums.ContentType;
import com.github.chiragji.carouselkit.models.Arguments;
import com.github.ybq.android.spinkit.SpinKitView;

/**
 * The fragment, used to show a single content on a page
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
@SuppressLint("RestrictedApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public class CarouselItemFragment extends Fragment implements CarouselFocusListener {
    private final CarouselItem carouselItem;
    private final Arguments arguments;

    private ImageView carouselImage;
    private VideoView carouselVideo;
    private TextView carouselText;
    private SpinKitView carouselProgress;

    private final boolean enableImage, enableVideo, enableText;
    private boolean reRender;

    public CarouselItemFragment(@NonNull CarouselItem carouselItem, @NonNull Arguments arguments) {
        this.carouselItem = carouselItem;
        this.arguments = arguments;
        enableImage = carouselItem.getContentType() == ContentType.IMAGE;
        enableVideo = carouselItem.getContentType() == ContentType.VIDEO;
        enableText = carouselItem.getContentType() == ContentType.TEXT;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.carousel_item_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        carouselImage = view.findViewById(R.id.carouselImage);
        carouselVideo = view.findViewById(R.id.carouselVideo);
        carouselText = view.findViewById(R.id.carouselText);
        carouselProgress = view.findViewById(R.id.carouselProgress);

        carouselProgress.setColor(arguments.getPrimaryColor());

        doRender();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (reRender)
            doRender();
        else reRender = true;
    }

    public void doRender() {
        carouselImage.setVisibility(enableImage ? View.VISIBLE : View.GONE);
        carouselVideo.setVisibility(enableVideo ? View.VISIBLE : View.GONE);
        carouselText.setVisibility(enableText ? View.VISIBLE : View.GONE);

        if (enableImage) {
            carouselImage.setScaleType(arguments.getImageScaleType());
            loadImage();
        } else if (enableVideo) {
            carouselVideo.setVideoURI(Uri.parse(carouselItem.getDataUri()));
            carouselVideo.setOnPreparedListener(mediaPlayer -> {
                carouselProgress.setVisibility(View.GONE);
                if (arguments.hasVideoPlaybackListener())
                    arguments.getPlaybackListener().onPrepared(carouselItem, mediaPlayer);
            });
            carouselVideo.setOnCompletionListener(mediaPlayer -> {
                if (arguments.hasVideoPlaybackListener())
                    arguments.getPlaybackListener().onComplete(carouselItem, mediaPlayer);
            });
        } else {
            carouselText.setText(carouselItem.getDataUri());
            carouselProgress.setVisibility(View.GONE);
        }
    }

    private void loadImage() {
        Glide.with(requireContext()).load(carouselItem.getDataUri()).thumbnail(arguments.getThumbnailLoadFactor())
                .error(arguments.getErrorImageRes()).transition(DrawableTransitionOptions.withCrossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        carouselProgress.setVisibility(View.GONE);
                        carouselImage.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        carouselProgress.setVisibility(View.GONE);
                        carouselImage.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(carouselImage);
    }


    @Override
    public void onFocusUpdated(boolean focused) {
        if (carouselVideo != null && enableVideo) {
            if (focused) {
                if (!carouselVideo.isPlaying())
                    carouselVideo.start();
                else carouselVideo.resume();
            } else carouselVideo.pause();
        }
    }
}