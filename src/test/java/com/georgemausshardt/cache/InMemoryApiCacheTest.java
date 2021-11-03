package com.georgemausshardt.cache;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class InMemoryApiCacheTest {
    private final InMemoryApiCache<String, Integer> fixture = new InMemoryApiCache<>();

    @Before
    public void beforeEachTest() {
        fixture.clear();
    }

    @Test
    public void testGet_OptionalContainsValueIfExists() {
        fixture.put("key1", 10);
        assertEquals(Optional.of(10), fixture.get("key1"));
    }

    @Test
    public void testGet_OptionalEmptyIfValueDoesNotExist() {
        assertEquals(Optional.empty(), fixture.get("thisLasagnaIsDelicious"));
        assertEquals(Optional.empty(), fixture.get("key1"));
    }

    @Test
    public void testGetOrElse_DefaultsToExistingValue() {
        fixture.put("godzilla", -180);
        assertEquals(Integer.valueOf(-180), fixture.getOrElse("godzilla", s -> 5));
    }

    @Test
    public void tetGetOrElse_ReturnsComputedValueIfNotExistent() {
        assertEquals(Integer.valueOf(5), fixture.getOrElse("godzilla", s -> 5));
    }

    @Test
    public void tetGetOrElse_PersistComputedValueAfterComputation() {
        assertEquals(Integer.valueOf(5), fixture.getOrElse("godzilla", s -> 5));
        assertEquals(Optional.of(5), fixture.get("godzilla"));
    }

    @Test
    public void testPut_AddsValueToCache() {
        assertEquals(Optional.empty(), fixture.get("key3"));
        fixture.put("key3", 153);
        assertEquals(Optional.of(153), fixture.get("key3"));
    }

    @Test
    public void testPut_OverridesValueAlreadyInCache() {
        assertEquals(Optional.empty(), fixture.get("key3"));
        fixture.put("key3", 153);
        assertEquals(Optional.of(153), fixture.get("key3"));
        fixture.put("key3", 200);
        assertEquals(Optional.of(200), fixture.get("key3"));
    }

    @Test
    public void testRemove_ReturnsValueIfRemovingExistingValue() {
        fixture.put("skeleton", -3); // Skeleton Key - funny, right?

        assertEquals(Optional.of(-3), fixture.get("skeleton"));
        assertEquals(Optional.of(-3), fixture.remove("skeleton"));
    }

    @Test
    public void testRemove_ReturnsEmptyIfValueDoesNotExist() {
        assertEquals(Optional.empty(), fixture.remove("brass"));
    }

    @Test
    public void testRemove_ActuallyRemovesValueThatExistedBefore() {
        fixture.put("copper", 72);

        assertEquals(Optional.of(72), fixture.get("copper"));
        assertEquals(Optional.of(72), fixture.remove("copper"));
        assertEquals(Optional.empty(), fixture.get("copper"));
    }

    @Test
    public void testClear_RemovesAllValues() {
        fixture.put("first", 1);
        fixture.put("second", 2);

        assertEquals(Optional.of(1), fixture.get("first"));
        assertEquals(Optional.of(2), fixture.get("second"));

        fixture.clear();

        assertEquals(Optional.empty(), fixture.get("first"));
        assertEquals(Optional.empty(), fixture.get("second"));
    }

}
