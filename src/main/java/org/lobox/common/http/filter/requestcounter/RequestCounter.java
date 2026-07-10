package org.lobox.common.http.filter.requestcounter;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RequestCounter {

    private final AtomicInteger requestCounts = new AtomicInteger(0);

    public void countRequest() {
        requestCounts.incrementAndGet();
    }

    public int getRequestCount() {
        return requestCounts.get();
    }

}
