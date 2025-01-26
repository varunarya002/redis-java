package storage;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage<K, V> extends Storage<K, V>
{
  private final ConcurrentHashMap<K, V> DATA_STORAGE = new ConcurrentHashMap<>();

  @Override
  public void set(K key, V value)
  {
    DATA_STORAGE.put(key, value);
  }

  @Override
  public V get(K key)
  {
    if (DATA_STORAGE.containsKey(key)) {
      return DATA_STORAGE.get(key);
    }
    return null;
  }
}
