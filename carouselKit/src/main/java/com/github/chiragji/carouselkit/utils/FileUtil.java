package com.github.chiragji.carouselkit.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.github.chiragji.carouselkit.enums.ContentType;

import java.io.File;

/**
 * An utility class that contains some common methods
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 3
 * @since 1.0.0
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public abstract class FileUtil {
    public static String getFileExtension(@NonNull String fileName) {
        int idx = fileName.lastIndexOf('.');
        return idx < 0 ? "" : fileName.substring(idx + 1);
    }

    public static ContentType getContentType(@NonNull String fileName) {
        String ext = getFileExtension(fileName);
        if (TextUtils.isEmpty(ext)) return ContentType.TEXT;
        else if (ext.equalsIgnoreCase("jpg")) return ContentType.IMAGE;
        else if (ext.equalsIgnoreCase("jpeg")) return ContentType.IMAGE;
        else if (ext.equalsIgnoreCase("png")) return ContentType.IMAGE;
        else if (ext.equalsIgnoreCase("mp4")) return ContentType.VIDEO;
        else return ContentType.TEXT;
    }

    public static String getFileExtension(@NonNull File file) {
        return getFileExtension(file.getName());
    }

    public static ContentType getContentType(@NonNull File file) {
        return getContentType(file.getName());
    }
}
