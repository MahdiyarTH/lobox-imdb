package org.lobox.common.http.filter.requestcounter;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestCounter {

    private final AtomicLong requestCounts = new AtomicLong(0);

    public void countRequest() {
        requestCounts.incrementAndGet();
    }

    public long getRequestCount() {
        return requestCounts.get();
    }

}
