package com.cainz;

import io.micronaut.core.annotation.NonNull;

public interface ThumbnailConfiguration {

    @NonNull
    Integer getWidth();

    @NonNull
    Integer getHeight();
}
