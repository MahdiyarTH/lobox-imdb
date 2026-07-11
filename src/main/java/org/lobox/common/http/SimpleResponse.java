package org.lobox.common.http;

import lombok.Getter;

@Getter
public class SimpleResponse<T> {

    private final T data;

    public SimpleResponse(T data) {
        this.data = data;
    }

}
