package org.example;
/*
CacheRequest (Food Seller): The CacheRequest is like a food seller who purchases food (data) from a
farmer (web source) and stores it in a designated storage (cache). The exact place to store the data is
specified by public abstract OutputStream getBody(). You could also tell it store data in a file using
FileStream.

CacheResponse (Food Packaging): The CacheResponse acts as packaging for the food (data) obtained by the
CacheRequest. It includes important information about the food, such as macronutrients,
ingredients, etc., making it easily accessible and providing valuable details about the data. For example,
we could get rice, potatoes etc from the farmer. But we would want to package it first before giving
it to the customer. CacheResponse packages the request.
Its kind of similar to how time  is just minutes, seconds and hours which are basically integers.
But keeping three integers can be annoying so we can create a Time class to handle that.
So CacheResponse is analogous to the Time class for the minutes, secs and hours (CacheRequest data)

Finally, there is the Food Inventory (ResponseCache). Imagine someone wants to buy a specific type of food.
The food seller may not know if that particular food item is available in the storage because they
can't remember every possible food item they have. However, if they maintain an inventory, they can
quickly check if the food item exists in stock or not. The Food Inventory (ResponseCache) serves as
this inventory, enabling the seller to check rapidly if they have the desired food item stored
in the cache.
*/


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.net.*;
import java.util.*;
import jakarta.ws.rs.core.CacheControl;

public class SimpleCacheResponse extends  CacheResponse {
    private final Map<String, List<String>> headers;
    private final SimpleCacheRequest request;
    private final Date expires;
    private final CacheControl control;
    public SimpleCacheResponse(
            SimpleCacheRequest request, URLConnection uc, CacheControl control) throws IOException {
        this.request = request;
        this.control = control;
        this.expires = new Date(uc.getExpiration());
        this.headers = Collections.unmodifiableMap(uc.getHeaderFields());
    }
    @Override
    public InputStream getBody() {
        return new ByteArrayInputStream(request.getData());
    }
    @Override
    public Map<String, List<String>> getHeaders() throws IOException {
        return headers;
    }
    public CacheControl getControl() {
        return control;
    }
    public boolean isExpired() {
        Date now = new Date();
        if (expires != null && control.getMaxAge() != -1) {
            return expires.before(now);
        } else {
            return false;
        }
    }
}