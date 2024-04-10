package com.likevel.kaloriinnhold.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CacheManager<K, V> extends LinkedHashMap<K, V> {
    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        int maxSize = 2;
        return size() > maxSize;
    }
}
