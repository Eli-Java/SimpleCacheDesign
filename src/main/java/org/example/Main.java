package org.example;

import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        MemoryCache mc = new MemoryCache(10);
        URL url = null;
        try {
            // Example URI and URL
            URI uri = new URI("https://www.oreilly.com");
            url = uri.toURL();

            // Make the first request
            URLConnection conn = url.openConnection();
            mc.put(uri, conn);

            String cacheControl = conn.getHeaderField("Cache-Control");

            CacheResponse res = mc.get(uri,"GET", null);
            System.out.println("stored response: "+res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}