package seedu.address.commons.structures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class to Map unique keys to values and values to keys.
 */
public class SetMap<K, V> {

    private Map<K, V> keyToValue;
    private Map<V, Set<K>> valueToKeys;

    /**
     * Constructs SetMap.
     */
    public SetMap() {
        this.keyToValue = new HashMap<>();
        this.valueToKeys = new HashMap<>();
    }

    /**
     * Inserts or replaces key value pair.
     *
     * @param key The key.
     * @param value The value.
     * @return The replaced value the key initially mapped to, if any.
     */
    public V put(K key, V value) {
        V replacedValue = this.removeKey(key);
        this.keyToValue.put(key, value);
        if (!this.valueToKeys.containsKey(value)) {
            this.valueToKeys.put(value, new HashSet<>());
        }
        this.valueToKeys.get(value).add(key);
        return replacedValue;
    }

    /**
     * Removes a key.
     *
     * @param key The key to remove.
     * @return The value the key was mapped to, if any.
     */
    public V removeKey(K key) {
        V removedValue = this.keyToValue.remove(key);
        if (removedValue == null) {
            return null;
        }
        Set<K> mappedKeys = this.valueToKeys.get(removedValue);
        mappedKeys.remove(key);
        if (mappedKeys.size() == 0) {
            this.valueToKeys.remove(removedValue);
        }
        return removedValue;
    }

    /**
     * Removes a value and all keys that mapped to it.
     *
     * @param value The value to remove.
     * @return The set of all keys mapped to the value, if any.
     */
    public Set<K> removeValue(V value) {
        Set<K> removedKeys = this.valueToKeys.remove(value);
        if (removedKeys == null) {
            return null;
        }
        for (K key : removedKeys) {
            this.keyToValue.remove(key);
        }
        return removedKeys;
    }

    public V getValue(K key) {
        return this.keyToValue.get(key);
    }

    public Set<K> getKeys(V value) {
        return this.valueToKeys.get(value);
    }

}
