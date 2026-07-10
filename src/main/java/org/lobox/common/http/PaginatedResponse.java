package org.lobox.common.http;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PaginatedResponse<T> {

    private final List<T> data;

    private final int page;

    private final int size;

    private final long total;

    public PaginatedResponse(Page<T> page) {
        this.data = page.getContent();

        this.size = page.getSize();
        this.page = page.getNumber() + 1;
        this.total = page.getTotalElements();
    }
}
