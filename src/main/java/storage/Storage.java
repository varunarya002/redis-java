package storage;

public abstract class Storage<K, V>
{
  public abstract void set(K key, V value);
  public abstract V get(K key);
}
