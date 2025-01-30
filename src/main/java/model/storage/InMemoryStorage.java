package model.storage;

import java.util.List;
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

  @Override
  public void remove(K key)
  {
    DATA_STORAGE.remove(key);
  }

  @Override
  public List<K> getAllKeys()
  {
    return DATA_STORAGE.keySet().stream().toList();
  }
}
