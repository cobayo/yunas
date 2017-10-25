package yunas.util;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cache in Memory.
 *
 * @param <K>	Type of Cache Key
 * @param <V>	Type of Cache Value
 */
public class LruCache<K, V> {

    /* Cache Data. */
    private final LinkedHashMap<K, V> map;

    /* Current Cache Size. */
    private int size;
    /* Max Cache Size. */
    private int maxSize;

    /**
     * Constructor.
     *
     * @param maxSize MaxSize
     */
    public LruCache(int maxSize) {

        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }

        this.maxSize = maxSize;
        this.map = new LinkedHashMap<>(0, 0.75f, true);

    }

    /**
     * Update Max Size.
     *
     * @param maxSize MaxSize
     */
    public void resize(int maxSize) {

        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }

        synchronized (this) {

            // Keep MaxSize.
            this.maxSize = maxSize;

        }

        trimToSize(maxSize);

    }

    /**
     * Get a value from Cache in Memory.
     *
     * @param key Cache Key
     * @return	Value
     */
    public final V get(K key) {

        if (key == null) {
            throw new NullPointerException("key == null");
        }

        V mapValue;
        synchronized (this) {

            mapValue = map.get(key);

            if (mapValue != null) {
                return mapValue;
            }

        }

        V createdValue = create(key);
        if (createdValue == null) {
            return null;
        }

        synchronized (this) {

            mapValue = map.put(key, createdValue);

            if (mapValue != null) {
                map.put(key, mapValue);
            } else {
                size += sizeOf(key, createdValue);
            }

        }

        if (mapValue != null) {

            entryRemoved(false, key, createdValue, mapValue);
            return mapValue;

        } else {

            trimToSize(maxSize);
            return createdValue;

        }

    }

    /**
     * Add a value in Cache.
     *
     * @param key	Cache Key
     * @param value	Cache Value
     * @return	Cache Value
     */
    public final V put(K key, V value) {

        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }

        V previous;
        synchronized (this) {

            size += sizeOf(key, value);
            previous = map.put(key, value);
            if (previous != null) {
                size -= sizeOf(key, previous);
            }

        }

        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }

        trimToSize(maxSize);

        return previous;

    }

    /**
     * trim a value.
     *
     * @param maxSize MaxSize
     */
    private void trimToSize(int maxSize) {

        while (true) {

            K key;
            V value;
            synchronized (this) {

                if (size < 0 || (map.isEmpty() && size != 0)) {
                    throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
                }

                if (size <= maxSize) {
                    break;
                }

                Map.Entry<K, V> toEvict = null;
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    toEvict = entry;
                }

                if (toEvict == null) {
                    break;
                }

                key = toEvict.getKey();
                value = toEvict.getValue();
                map.remove(key);
                size -= sizeOf(key, value);

            }

            entryRemoved(true, key, value, null);

        }

    }

    /**
     * Delete a Cache.
     *
     * @param key Cache Key
     * @return	Cache Value
     */
    public final V remove(K key) {

        if (key == null) {
            throw new NullPointerException("key == null");
        }

        V previous;
        synchronized (this) {
            previous = map.remove(key);
            if (previous != null) {
                size -= sizeOf(key, previous);
            }
        }

        if (previous != null) {
            entryRemoved(false, key, previous, null);
        }

        return previous;

    }

    /**
     * Delete a value.
     *
     */
    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {

    }

    /**
     * Create a value.
     *
     */
    protected V create(K key) {

        return null;

    }

    /**
     * Get a size of a value.
     */
    protected int sizeOf(K key, V value) {

        return 1;

    }

    /**
     * Clear ALL Values.
     */
    public final void evictAll() {

        trimToSize(-1);

    }

}