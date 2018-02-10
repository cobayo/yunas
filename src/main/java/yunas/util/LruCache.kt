package yunas.util


import java.util.*

/**
 * Cache in Memory.
 *
 * @param <K>	Type of Cache Key
 * @param <V>	Type of Cache Value
</V></K> */
open class LruCache<K, V>
/**
 * Constructor.
 *
 * @param maxSize MaxSize
 */
(/* Max Cache Size. */
        private var maxSize: Int) {

    /* Cache Data. */
    private val map: LinkedHashMap<K, V>

    /* Current Cache Size. */
    private var size: Int = 0

    init {

        if (maxSize <= 0) {
            throw IllegalArgumentException("maxSize <= 0")
        }
        this.map = LinkedHashMap(0, 0.75f, true)

    }

    /**
     * Update Max Size.
     *
     * @param maxSize MaxSize
     */
    fun resize(maxSize: Int) {

        if (maxSize <= 0) {
            throw IllegalArgumentException("maxSize <= 0")
        }

        synchronized(this) {

            // Keep MaxSize.
            this.maxSize = maxSize

        }

        trimToSize(maxSize)

    }

    /**
     * Get a value from Cache in Memory.
     *
     * @param key Cache Key
     * @return    Value
     */
    fun get(key: K?): V? {

        if (key == null) {
            throw NullPointerException("key == null")
        }

        synchronized(this) {

            val mapValue = map[key]

            if (mapValue != null) {
                return mapValue
            }

        }

        val createdValue = create(key) ?: return null

        synchronized(this) {

            val mapValue = map.put(key, createdValue)

            if (mapValue != null) {
                map.put(key, mapValue)
            } else {
                size += sizeOf(key, createdValue)
            }

            if (mapValue != null) {

                entryRemoved(false, key, createdValue, mapValue)
                return mapValue

            } else {

                trimToSize(maxSize)
                return createdValue

            }

        }



    }

    /**
     * Add a value in Cache.
     *
     * @param key    Cache Key
     * @param value    Cache Value
     * @return    Cache Value
     */
    fun put(key: K?, value: V?): V? {

        if (key == null || value == null) {
            throw NullPointerException("key == null || value == null")
        }

        var previous: V? = null
        synchronized(this) {

            size += sizeOf(key, value)
            previous = map.put(key, value)
            if (previous != null) {
                size -= sizeOf(key, previous)
            }

        }

        if (previous != null) {
            entryRemoved(false, key, previous, value)
        }

        trimToSize(maxSize)

        return previous

    }

    /**
     * trim a value.
     *
     * @param maxSize MaxSize
     */
    private fun trimToSize(maxSize: Int) {

         while (true) {

            var key: K? = null
            var value: V? = null

             synchronized(this) {

                if (size < 0 || map.isEmpty() && size != 0) {
                    throw IllegalStateException(javaClass.name + ".sizeOf() is reporting inconsistent results!")
                }

                if (size <= maxSize) {
                    return
                }

                var toEvict: Map.Entry<K, V>? = null
                for (entry in map.entries) {
                    toEvict = entry
                }

                if (toEvict == null) {
                    return
                }

                val key: K = toEvict!!.key
                value = toEvict!!.value
                map.remove(key)
                size -= sizeOf(key, value)

            }

            entryRemoved(true, key, value, null)

        }

    }

    /**
     * Delete a Cache.
     *
     * @param key Cache Key
     * @return    Cache Value
     */
    fun remove(key: K?): V? {

        if (key == null) {
            throw NullPointerException("key == null")
        }

        var previous: V? = null
        synchronized(this) {
            previous = map.remove(key)
            if (previous != null) {
                size -= sizeOf(key, previous)
            }
        }

        if (previous != null) {
            entryRemoved(false, key, previous, null)
        }

        return previous

    }

    /**
     * Delete a value.
     *
     */
    protected fun entryRemoved(evicted: Boolean, key: K?, oldValue: V?, newValue: V?) {

    }

    /**
     * Create a value.
     *
     */
    protected fun create(key: K?): V? {

        return null

    }

    /**
     * Get a size of a value.
     */
    protected open fun sizeOf(key: K, value: V?): Int {

        return 1

    }

    /**
     * Clear ALL Values.
     */
    fun evictAll() {

        trimToSize(-1)

    }

}