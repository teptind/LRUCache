package org.teptind.lru

class LRUCacheImpl<K, V>(private val capacity: UInt) : LRUCache<K, V> {

    private val state: CacheState<K, V> = CacheState(null, null, mutableMapOf())

    override fun put(key: K, value: V) {
        TODO("Not yet implemented")
    }

    override fun get(key: K): V {
        TODO("Not yet implemented")
    }

    override fun has(key: K): Boolean {
        TODO("Not yet implemented")
    }

    data class Node<K, V>(val key: K, val value: V) {
        var prev: Node<K, V>? = null
        var next: Node<K, V>? = null
    }

    data class CacheState<K, V>(
        var tail: Node<K, V>?,
        var head: Node<K, V>?,

        val nodeMap: MutableMap<K, Node<K, V>>
    )
}
