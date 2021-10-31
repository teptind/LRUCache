package org.teptind.lru

import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.lang.AssertionError
import kotlin.test.assertEquals

class LRUCacheTest {

    private fun fillCache(keyPrefix: String, keys: IntRange, cache: LRUCache<String, Int>) {
        keys.forEach { cache.put("$keyPrefix$it", it) }
    }

    @Test
    fun baseTest() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(10)

        val keys = (1..8)
        val keyPrefix = "key"
        fillCache(keyPrefix, keys, cache)

        keys.forEach {
            assertEquals(true, cache.has("$keyPrefix$it"))
            assertEquals(it, cache.get("$keyPrefix$it"))
        }
    }

    @Test
    fun capacityOverflowTest() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(5)

        val keys = (1..8)
        val displacedKeys = (1..3)
        val leftKeys = keys.minus(displacedKeys)

        val keyPrefix = "key"
        fillCache(keyPrefix, keys, cache)

        displacedKeys.forEach {
            assertEquals(false, cache.has("$keyPrefix$it"))
        }

        leftKeys.forEach {
            assertEquals(true, cache.has("$keyPrefix$it"))
            assertEquals(it, cache.get("$keyPrefix$it"))
        }
    }

    @Test
    fun illegalCapacityTest() {
        assertThrows<AssertionError> { LRUCacheImpl<Int, Int>(0) }
            .also { assertEquals("The capacity must be positive", it.message) }
    }

}

