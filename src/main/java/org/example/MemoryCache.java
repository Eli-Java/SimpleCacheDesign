package org.example;
import jakarta.ws.rs.core.CacheControl;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
public class MemoryCache extends ResponseCache{
    private final Map<URI, SimpleCacheResponse> responses = new ConcurrentHashMap<URI, SimpleCacheResponse>();
    private final int maxEntries;
    public MemoryCache() {
        this(100);
    }
    public MemoryCache(int maxEntries) {
        this.maxEntries = maxEntries;
    }
    @Override
    public CacheRequest put(URI uri, URLConnection conn)  throws IOException {
        if (responses.size() >= maxEntries) return null;
        String cacheControlHeaderValue = conn.getHeaderField("Cache-Control");
        CacheControl control = CacheControl.valueOf(cacheControlHeaderValue);

        if (control.isNoStore()) {
            return null;
        }
        SimpleCacheRequest request = new SimpleCacheRequest();
        SimpleCacheResponse response = new SimpleCacheResponse(request, conn, control);
        responses.put(uri, response);
        return request;
    }
    @Override
    public CacheResponse get(URI uri, String requestMethod,  Map<String, List<String>> requestHeaders) throws IOException {
        if ("GET".equals(requestMethod)) {
            SimpleCacheResponse response = responses.get(uri);
// check expiration date
            if (response != null && response.isExpired()) {
                responses.remove(response);
                response = null;
            }
            return response;
        } else {
            return null;
        }
    }
}