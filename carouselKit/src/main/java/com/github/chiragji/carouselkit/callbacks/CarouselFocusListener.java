package com.github.chiragji.carouselkit.callbacks;

import androidx.annotation.RestrictTo;

/**
 * An internal callback to notify the {@link com.github.chiragji.carouselkit.core.CarouselItemFragment}
 * about the currently focused item
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
@FunctionalInterface
public interface CarouselFocusListener {
    /**
     * This will be called by the {@link com.github.chiragji.carouselkit.adapters.CarouselAdapter}
     * with the flag weather the content is currently focused or not
     *
     * @param focused Has focus or not
     */
    void onFocusUpdated(boolean focused);
}