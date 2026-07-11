package org.lobox.statistics;

import lombok.RequiredArgsConstructor;
import org.lobox.common.http.SimpleResponse;
import org.lobox.common.http.filter.requestcounter.RequestCounter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/statistics")
public class StatisticsController {

    private final RequestCounter requestCounter;

    @GetMapping("requests-count")
    public SimpleResponse<Long> requestsCount() {
        return new SimpleResponse<>(requestCounter.getRequestCount());
    }

}
