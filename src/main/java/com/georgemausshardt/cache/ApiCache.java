package com.georgemausshardt.cache;

import java.util.Optional;
import java.util.function.Function;

/**
 * Generic form of a cache to prevent the overloading of an API from rapid calls to it by caching the results.
 * @param <Key> Type of the cache key.  Used to look up cached values to see if they already exist.
 * @param <Value> Type of the cache value.  The data we're trying to get that may already exist in the cache.
 */
public interface ApiCache<Key, Value> {
    /**
     * Fetches the value stored in the cache if it exists or {@link Optional#empty()} if it does not.
     *
     * @param key The key for which we are hoping to find a value
     * @return Either the value stored in this cache for 'key' or {@link Optional#empty()} if it is not cached.
     */
    Optional<Value> get(final Key key);

    /**
     * First attempts to pull the value with the associated key from the cache.  If this key is not cached, executes
     * the 'apiRequest' parameter with the given Key to retrieve a value, <b>inserts it into the cache</b> and returns
     * this fetched value.
     *
     * Please note that the value returned from this function CAN be null!  This is an intentional design decision as some
     *  APIs choose to use null to indicate failures, no results, or various other things.  In those cases, wrapping the
     *  returned value in an Optional could actually end up more confusing than helpful.
     *
     * @param key
     * @param apiRequest
     * @return
     */
    Value getOrElse(final Key key, final Function<Key, Value> apiRequest);

    /**
     * Puts the key-value pair in the cache and returns the value parameter.  Note that this will clobber a key-value
     *  pair already in the cache associated with the same key!  If you're worried about hash conflicts or keys with
     *  multiple values, consider a different caching solution or extending this interface!
     *
     * @param key The key to be stored in the cache and associated with the value.
     * @param value The value to be stored and associated with the key.
     * @return The value pushed to this cache.
     */
    Value put(final Key key, final Value value);

    /**
     * Removes the given key from the cache.  If it exists, this returns the value removed.  Otherwise, the object
     * returned from this function is {@link Optional#empty()} to indicate that this key was not cached even before
     * the call to this method.
     *
     * @param key The key and its associated value to remove from the cache.
     * @return The value if this key existed in the cache and {@link Optional#empty()} if it did not.
     */
    Optional<Value> remove(final Key key);

    /**
     * Empties the cache.  <b>WARNING:</b> This is irreversible, save for making the same uncached calls again
     * and refilling this cache.  Be careful and use sparingly!
     */
    void clear();
}
