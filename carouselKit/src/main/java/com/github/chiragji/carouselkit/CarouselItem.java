package com.github.chiragji.carouselkit;

import androidx.annotation.NonNull;

import com.github.chiragji.carouselkit.enums.ContentType;
import com.github.chiragji.carouselkit.utils.FileUtil;

/**
 * A wrapper, that contains the represents a single data that needs to be rendered into the
 * {@link com.github.chiragji.carouselkit.core.CarouselItemFragment} as child via
 * {@link com.github.chiragji.carouselkit.adapters.CarouselAdapter}
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
public class CarouselItem {
    private final String dataUri;
    private final ContentType contentType;

    public CarouselItem(String dataUri) {
        this.dataUri = dataUri;
        contentType = FileUtil.getContentType(dataUri);
    }

    public CarouselItem(String dataUri, ContentType contentType) {
        this.dataUri = dataUri;
        this.contentType = contentType;
    }

    public String getDataUri() {
        return dataUri;
    }

    public ContentType getContentType() {
        return contentType;
    }

    @NonNull
    @Override
    public String toString() {
        return "CarouselItem{" +
                "dataUri='" + dataUri + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}