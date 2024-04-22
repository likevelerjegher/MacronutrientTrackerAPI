package com.likevel.kaloriinnhold.services;

import lombok.Data;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public final class RequestCounterService {

    private RequestCounterService() { }
    private static AtomicInteger requestCount = new AtomicInteger(0);

    public static synchronized void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    public static synchronized int getRequestCount() {
        return requestCount.get();
    }
}
