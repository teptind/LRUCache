package org.teptind.lru

interface LRUCache<K, V> {
    fun put(key: K, value: V)

    fun get(key: K) : V

    fun has(key: K) : Boolean
}