package tools;

import java.util.HashMap;
import java.util.Map;

public class BidirectionalMap<K, V> {
    private Map<K, V> forwardMap = new HashMap<>();
    private Map<V, K> reverseMap = new HashMap<>();

    public BidirectionalMap() {}

    public void put(K key, V value) {
        forwardMap.put(key, value);
        reverseMap.put(value, key);
    }

    public V getByKey(K key) {
        return forwardMap.get(key);
    }

    public K getByValue(V value) {
        return reverseMap.get(value);
    }
}

