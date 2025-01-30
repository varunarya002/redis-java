package model.storage;

import java.util.List;

public abstract class Storage<K, V>
{
  public abstract void set(K key, V value);

  public abstract V get(K key);

  public abstract void remove(K key);

  public abstract List<K> getAllKeys();
}
