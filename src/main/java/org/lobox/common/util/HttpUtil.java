package org.lobox.common.util;

public class HttpUtil {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int MAX_PAGE_SIZE = 50;

    public static int getPage(Integer page) {
        if (page == null || page < 1)
            return 0;

        //because spring's page starts from 0, but clients usually send 1 as first page
        return page - 1;
    }

    public static int getSize(Integer size) {
        if (size == null || size < 1)
            return DEFAULT_PAGE_SIZE;

        if (size > MAX_PAGE_SIZE)
            return MAX_PAGE_SIZE;

        return size;
    }

}
