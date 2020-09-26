package com.github.chiragji.carouselkit.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.github.chiragji.carouselkit.callbacks.CarouselVideoPlaybackListener;

/**
 * A {@link com.github.chiragji.carouselkit.CarouselView}'s internal model, used to forward
 * the XML attribute's or setter flags to the {@link com.github.chiragji.carouselkit.core.CarouselItemFragment}
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public class Arguments {
    private int errorImageRes, placeholderImageRes;
    private float thumbnailLoadFactor;

    @ColorInt
    private int primaryColor, accentColor;

    private ImageView.ScaleType imageScaleType;

    private CarouselVideoPlaybackListener playbackListener;

    public int getErrorImageRes() {
        return errorImageRes;
    }

    public void setErrorImageRes(int errorImageRes) {
        this.errorImageRes = errorImageRes;
    }

    public int getPlaceholderImageRes() {
        return placeholderImageRes;
    }

    public void setPlaceholderImageRes(int placeholderImageRes) {
        this.placeholderImageRes = placeholderImageRes;
    }

    public float getThumbnailLoadFactor() {
        return thumbnailLoadFactor;
    }

    public void setThumbnailLoadFactor(float thumbnailLoadFactor) {
        this.thumbnailLoadFactor = thumbnailLoadFactor;
    }

    @ColorInt
    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(@ColorInt int primaryColor) {
        this.primaryColor = primaryColor;
    }

    @ColorInt
    public int getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(@ColorInt int accentColor) {
        this.accentColor = accentColor;
    }

    public ImageView.ScaleType getImageScaleType() {
        return imageScaleType;
    }

    public void setImageScaleType(ImageView.ScaleType imageScaleType) {
        this.imageScaleType = imageScaleType;
    }

    public boolean hasVideoPlaybackListener() {
        return this.playbackListener != null;
    }

    public CarouselVideoPlaybackListener getPlaybackListener() {
        return playbackListener;
    }

    public void setPlaybackListener(CarouselVideoPlaybackListener playbackListener) {
        this.playbackListener = playbackListener;
    }

    @NonNull
    @Override
    public String toString() {
        return "Arguments{" +
                "errorImageRes=" + errorImageRes +
                ", placeholderImageRes=" + placeholderImageRes +
                ", thumbnailLoadFactor=" + thumbnailLoadFactor +
                ", primaryColor=" + primaryColor +
                ", accentColor=" + accentColor +
                ", imageScaleType=" + imageScaleType +
                '}';
    }
}