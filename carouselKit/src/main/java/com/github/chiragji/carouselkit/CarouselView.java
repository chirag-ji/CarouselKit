package com.github.chiragji.carouselkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.util.Preconditions;
import com.github.chiragji.carouselkit.adapters.CarouselAdapter;
import com.github.chiragji.carouselkit.callbacks.CarouselVideoPlaybackListener;
import com.github.chiragji.carouselkit.enums.PagingStyle;
import com.github.chiragji.carouselkit.models.Arguments;
import com.rd.PageIndicatorView;

import java.util.Arrays;
import java.util.List;

/**
 * The main logic behind this implementor is to show the images/videos/text in a horizontal view
 * that can be shown in a ViewPager2
 * <p>
 * This is the layout which can be embedded to the views created with XML. And manages all the
 * process
 * <p>
 * It's an direct implementor of {@link FrameLayout}.
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public class CarouselView extends FrameLayout {
    private static final String TAG = "CarouselView";

    private ViewPager2 carouselViewPager;
    private PageIndicatorView pageIndicator;
    private LinearLayoutCompat currentPageTxtWrap;
    private TextView currentItemTxt;

    private CarouselAdapter adapter;

    private boolean attached, usingCustomPageIndicator;

    private final Arguments arguments = new Arguments();

    @ColorInt
    private int primaryColor, accentColor;
    private PagingStyle pagingStyle;
    private boolean showPageNumbers;

    public CarouselView(@NonNull Context context) {
        super(context);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public CarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.CarouselView);
        arguments.setErrorImageRes(array.getResourceId(R.styleable.CarouselView_failedImage, R.drawable.ic_failed));
        arguments.setPlaceholderImageRes(array.getResourceId(R.styleable.CarouselView_placeholderImage, NO_ID));
        arguments.setThumbnailLoadFactor(array.getFloat(R.styleable.CarouselView_thumbnailLoadFactor, 0.1F));
        primaryColor = array.getColor(R.styleable.CarouselView_primaryColor, getColor(R.color.colorPrimaryDark));
        accentColor = array.getColor(R.styleable.CarouselView_accentColor, getColor(R.color.colorGray));
        pagingStyle = PagingStyle.values()[array.getInt(R.styleable.CarouselView_pagingStyle, 7)];
        showPageNumbers = array.getBoolean(R.styleable.CarouselView_showPageNumbers, true);
        arguments.setImageScaleType(ImageView.ScaleType.values()[array.getInt(R.styleable.CarouselView_imageScaling, 3)]);
        array.recycle();

        arguments.setPrimaryColor(primaryColor);
        arguments.setAccentColor(accentColor);
    }

    @ColorInt
    private int getColor(@ColorRes int colorRes) {
        return ResourcesCompat.getColor(getResources(), colorRes, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = inflate(getContext(), R.layout.carousel_main_view, this);
        carouselViewPager = view.findViewById(R.id.carouselViewPager);
        pageIndicator = view.findViewById(R.id.pageIndicator);
        currentPageTxtWrap = view.findViewById(R.id.currentPageTxtWrap);
        currentItemTxt = view.findViewById(R.id.currentItemTxt);

        init();
    }

    private void init() {
        currentPageTxtWrap.setVisibility(GONE);

        pageIndicator.setSelectedColor(primaryColor);
        pageIndicator.setUnselectedColor(accentColor);
        pageIndicator.setAnimationType(pagingStyle.getAnimationType());
        pageIndicator.setCount(0);
    }

    private ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        private int lastPos;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            pageIndicator.setProgress(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            pageIndicator.setSelection(position);
            updatePageIndication();
            if (lastPos != position) {
                adapter.onPageChanged(position, lastPos);
                lastPos = position;
            }
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        carouselViewPager.registerOnPageChangeCallback(pageChangeCallback);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        carouselViewPager.unregisterOnPageChangeCallback(pageChangeCallback);
    }

    private void ensureAttached() {
        if (!attached)
            throw new IllegalStateException("CarouselView is not yet attached");
    }

    private void updatePageIndication() {
        if (showPageNumbers && adapter.getItemCount() > 1) {
            currentPageTxtWrap.setVisibility(VISIBLE);
            currentItemTxt.setText(getContext().getString(R.string.fmt_page_number,
                    carouselViewPager.getCurrentItem() + 1, adapter.getItemCount()));
        } else {
            currentPageTxtWrap.setVisibility(GONE);
        }
    }

    /**
     * This will attach the {@link CarouselView} to the passed {@link FragmentActivity}
     *
     * @param fragmentActivity an instance of {@link FragmentActivity} or {@link androidx.appcompat.app.AppCompatActivity}
     */
    public void attach(@NonNull FragmentActivity fragmentActivity) {
        tryCleaningAdapter();
        adapter = new CarouselAdapter(arguments, fragmentActivity);
        carouselViewPager.setAdapter(adapter);
        attached = true;
    }

    /**
     * This will attach the {@link CarouselView} to the passed {@link Fragment}
     *
     * @param fragment an instance of {@link Fragment}
     */
    public void attach(@NonNull Fragment fragment) {
        tryCleaningAdapter();
        adapter = new CarouselAdapter(arguments, fragment);
        carouselViewPager.setAdapter(adapter);
        attached = true;
    }

    private void tryCleaningAdapter() {
        if (adapter != null) adapter.clear();
    }

    /**
     * This will reset the {@link CarouselView} and then adds the passed items to view
     *
     * @param items an instance of {@link List} containing the {@link CarouselItem}
     * @see #setCarouselItems(CarouselItem...)
     */
    public void setCarouselItems(@NonNull List<CarouselItem> items) {
        ensureAttached();
        setCarouselItems(items.toArray(new CarouselItem[0]));
    }

    /**
     * This will reset the {@link CarouselView} and then adds the passed items to view
     *
     * @param items a dynamic var-args array of {@link CarouselItem}
     */
    public void setCarouselItems(@NonNull CarouselItem... items) {
        ensureAttached();
        adapter.setCarouselItems(items);
        pageIndicator.setCount(adapter.getItemCount());
        updatePageIndication();
        invalidate();
    }

    /**
     * This will adds the {@link CarouselItem} to the {@link CarouselView} keeping the previous items intact
     *
     * @param items an instance of {@link List} containing the {@link CarouselItem}
     * @see #addCarouselItems(CarouselItem...)
     */
    public void addCarouselItems(@NonNull List<CarouselItem> items) {
        ensureAttached();
        addCarouselItems(items.toArray(new CarouselItem[0]));
    }

    /**
     * This will adds the {@link CarouselItem} to the {@link CarouselView} keeping the previous items intact
     *
     * @param items a dynamic var-args array of {@link CarouselItem}
     */
    public void addCarouselItems(@NonNull CarouselItem... items) {
        ensureAttached();
        adapter.applyCarouselItem(items);
        pageIndicator.setCount(adapter.getItemCount());
        updatePageIndication();
    }

    /**
     * Set the paging style to the internal page indicator
     *
     * @param pagingStyle an instance of enum from {@link PagingStyle}
     */
    public void setPageIndicatorStyle(@NonNull PagingStyle pagingStyle) {
        if (this.pageIndicator == null || usingCustomPageIndicator) return;
        pageIndicator.setAnimationType(pagingStyle.getAnimationType());
        invalidate();
    }

    /**
     * Defines the custom page indicator to be bind to this {@link CarouselView}
     *
     * @param pageIndicator an instance of {@link PageIndicatorView}
     */
    public void setUsingCustomPageIndicator(@NonNull PageIndicatorView pageIndicator) {
        Preconditions.checkNotNull(pageIndicator, "PageIndicator is found null");
        this.pageIndicator.setVisibility(GONE);
        this.pageIndicator = pageIndicator;
        this.pageIndicator.setVisibility(VISIBLE);
        this.pageIndicator.setCount(adapter == null ? 0 : adapter.getItemCount());
        invalidate();
        this.usingCustomPageIndicator = true;
    }

    /**
     * Set the primary color for the internal components
     *
     * @param primaryColor primary color for internal components
     */
    public void setPrimaryColor(@ColorInt int primaryColor) {
        this.primaryColor = primaryColor;
        arguments.setPrimaryColor(primaryColor);
        if (this.pageIndicator != null && !usingCustomPageIndicator)
            this.pageIndicator.setSelectedColor(primaryColor);
        invalidate();
    }

    /**
     * Set the accent color for the internal components
     *
     * @param accentColor accent color for internal components
     */
    public void setAccentColor(@ColorInt int accentColor) {
        this.accentColor = accentColor;
        arguments.setAccentColor(accentColor);
        if (this.pageIndicator != null && !usingCustomPageIndicator)
            this.pageIndicator.setUnselectedColor(accentColor);
        invalidate();
    }

    /**
     * Defines the page indicator style
     *
     * @param pagingStyle any constant from the enum {@link PagingStyle}
     */
    public void setPagingStyle(@NonNull PagingStyle pagingStyle) {
        if (usingCustomPageIndicator) return;
        this.pagingStyle = pagingStyle;
        invalidate();
    }

    /**
     * Defines to show page number on top of carousel elements
     *
     * @param showPageNumbers true to show page number on top of element
     */
    public void setShowPageNumbers(boolean showPageNumbers) {
        this.showPageNumbers = showPageNumbers;
        invalidate();
    }

    /**
     * Defines the error image for the carousel image elements to be sown if image loading fails
     *
     * @param errorImage Resource id for the error image
     */
    public void setErrorImage(@DrawableRes int errorImage) {
        this.arguments.setErrorImageRes(errorImage);
    }

    /**
     * defines the placeholder image for the image elements to be shown while the load is in progress
     *
     * @param placeholderImage Resource Id of placeholder image
     */
    public void setPlaceholderImage(@DrawableRes int placeholderImage) {
        this.arguments.setPlaceholderImageRes(placeholderImage);
    }

    /**
     * Defines the thumbnail load factor for the image elements in carousel
     *
     * @param thumbnailLoadFactor Load factor for images (default 0.1F)
     */
    public void setThumbnailLoadFactor(float thumbnailLoadFactor) {
        this.arguments.setThumbnailLoadFactor(thumbnailLoadFactor);
    }

    /**
     * defines the scaling type for the images for the carousel image elements
     *
     * @param imageScaling Scaling for image, can be an instance of {@link ImageView.ScaleType}
     */
    public void setImageScaling(@NonNull ImageView.ScaleType imageScaling) {
        this.arguments.setImageScaleType(imageScaling);
    }

    /**
     * Register {@link CarouselVideoPlaybackListener} to listen back the events on video elements
     * in the {@link CarouselView} list
     *
     * <b>Note:</b> Only for the video elements
     *
     * @param playbackListener Implementor of {@link CarouselVideoPlaybackListener}
     */
    public void registerVideoPlaybackListener(@NonNull CarouselVideoPlaybackListener playbackListener) {
        arguments.setPlaybackListener(playbackListener);
    }

    /**
     * Method to indicate that the {@link CarouselView} is either attached to
     * {@link FragmentActivity} / {@link Fragment} or not
     *
     * @return <code>true</code> if attached to {@link Fragment} or {@link FragmentActivity},
     * otherwise <code>false</code>
     */
    public boolean isAttached() {
        return attached;
    }
}