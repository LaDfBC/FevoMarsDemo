package com.georgemausshardt.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Default, basic, extremely simply implementation of {@link ApiCache} that uses an In-Memory Map Data Structure.  This
 * cache exists to lessen the load on the NASA API by retrieving already-fetched values from here instead of making network
 * calls and retrieving data over the wire a second (or third...or fourth...) time.
 *
 * If this was a real project, the first improvement I would add would be a MemCached, Redis, or similar caching
 *  implementation.  Or perhaps even a Database since the Mars images are unlikely to ever change and a cache
 *  with no TTL is, well, basically just a database.  Slap Postgres in here and call it a day.
 *
 * @param <Key> Type of the Key to be used in the cache's key-value pairs.
 * @param <Value>
 */
public class InMemoryApiCache<Key, Value> implements ApiCache<Key, Value> {
    private final Map<Key, Value> cache = new HashMap<>();

    @Override
    public Optional<Value> get(final Key key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public Value getOrElse(final Key key, final Function<Key, Value> apiRequest) {
        return get(key).orElse(put(key, apiRequest.apply(key)));
    }

    @Override
    public Value put(final Key key, final Value value) {
        cache.put(key, value);
        return value;
    }

    @Override
    public Optional<Value> remove(Key key) {
        if (cache.containsKey(key)) {
            return Optional.of(cache.remove(key));
        }

        return Optional.empty();
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
