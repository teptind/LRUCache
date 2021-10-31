package org.teptind.lru

class LRUCacheImpl<K, V>(private val capacity: Int) : LRUCache<K, V> {
    companion object {
        const val THE_KEY_MUST_BE_IN_MAP_MSG = "The key must be in map"
    }

    init {
        assert(capacity > 0) { "The capacity must be positive" }
    }

    private val state: CacheState<K, V> = CacheState(null, null, mutableMapOf())

    override fun put(key: K, value: V) {
        if (state.nodeMap.containsKey(key)) {
            cutOutNode(state.nodeMap[key]!!)
        }

        val node = Node(key, value)
        state.nodeMap[key] = node
        appendNode(node)

        if (state.nodeMap.size > capacity) {
            moveHead()
        }

        assert(state.nodeMap.containsKey(key)) { THE_KEY_MUST_BE_IN_MAP_MSG }
        assert(state.nodeMap.size <= capacity) { "Size must be less than capacity" }
    }

    override fun get(key: K): V {
        assert(state.nodeMap.containsKey(key)) { THE_KEY_MUST_BE_IN_MAP_MSG }

        val node = state.nodeMap[key]!!

        updatePriority(node)

        return node.value
    }

    override fun has(key: K): Boolean {
        return state.nodeMap.containsKey(key)
    }

    private fun updatePriority(node: Node<K, V>) {
        cutOutNode(node)
        appendNode(node)
    }

    private fun cutOutNode(node: Node<K, V>) {
        node.prev?.run { next = node.next }
        node.next?.run { prev = node.prev }
    }

    private fun appendNode(node: Node<K, V>) {
        state.tail?.let {
            it.next = node
            node.prev = it
            node.next = null
        }

        state.head = state.head ?: node
        state.tail = node
    }

    private fun moveHead() {
        assert(state.head != null) {"head must not be null"}

        state.nodeMap.remove(state.head!!.key)

        state.head = state.head?.next
        state.head?.prev = null
    }

    data class Node<K, V>(val key: K, val value: V) {
        var prev: Node<K, V>? = null
        var next: Node<K, V>? = null
    }

    data class CacheState<K, V>(
        var head: Node<K, V>?,
        var tail: Node<K, V>?,

        val nodeMap: MutableMap<K, Node<K, V>>
    )
}
