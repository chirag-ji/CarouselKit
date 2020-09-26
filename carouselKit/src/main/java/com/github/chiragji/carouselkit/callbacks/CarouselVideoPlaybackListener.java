package com.github.chiragji.carouselkit.callbacks;

import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import com.github.chiragji.carouselkit.CarouselItem;

/**
 * The callback, used to receive the events of {@link CarouselItem} who is a representation of
 * video element
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public interface CarouselVideoPlaybackListener {
    /**
     * This will be called when video is ready with {@link MediaPlayer}
     *
     * @param carouselItem an instance of {@link CarouselItem} on which the media is ready
     * @param mediaPlayer  an instance of {@link MediaPlayer}
     */
    void onPrepared(@NonNull CarouselItem carouselItem, @NonNull MediaPlayer mediaPlayer);

    /**
     * This will be called when video playback is completed
     *
     * @param carouselItem an instance of {@link CarouselItem} on which the media is ready
     * @param mediaPlayer  an instance of {@link MediaPlayer}
     */
    void onComplete(@NonNull CarouselItem carouselItem, @NonNull MediaPlayer mediaPlayer);
}