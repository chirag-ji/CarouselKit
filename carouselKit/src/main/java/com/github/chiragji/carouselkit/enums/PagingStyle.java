package com.github.chiragji.carouselkit.enums;

import androidx.annotation.NonNull;

import com.rd.animation.type.AnimationType;

/**
 * Defines the paging style for internal indicator
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public enum PagingStyle {

    NONE(AnimationType.NONE),
    COLOR(AnimationType.COLOR),
    SCALE(AnimationType.SCALE),
    SLIDE(AnimationType.SLIDE),
    WORM(AnimationType.WORM),
    FILL(AnimationType.FILL),
    THIN_WORM(AnimationType.THIN_WORM),
    DROP(AnimationType.DROP),
    SWAP(AnimationType.SWAP),
    SCALE_DOWN(AnimationType.SCALE_DOWN);

    private final AnimationType animationType;

    PagingStyle(@NonNull AnimationType animationType) {
        this.animationType = animationType;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }
}