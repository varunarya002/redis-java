package model.storage.value;

import java.util.Date;

public class SingleValueStorage<V>
{
  private V value;
  private long ttl;
  private final Date createdAt;

  private SingleValueStorage(Builder<V> builder)
  {
    this.value = builder.value;
    this.createdAt = new Date();
    this.ttl = builder.ttl;
  }

  public Date getCreatedAt()
  {
    return createdAt;
  }

  public V getValue()
  {
    return value;
  }

  public void setValue(V value)
  {
    this.value = value;
  }

  public long getTtl()
  {
    return ttl;
  }

  public void setTtl(Integer ttl)
  {
    this.ttl = ttl;
  }

  public static<V> Builder<V> builder() {
    return new Builder<>();
  }

  public static class Builder<V> {
    private V value;
    private long ttl = -1;

    public Builder<V> withValue(V value)
    {
      this.value = value;
      return this;
    }

    public Builder<V> withTtl(long ttl)
    {
      this.ttl = ttl;
      return this;
    }

    public SingleValueStorage<V> build() {
      return new SingleValueStorage<>(this);
    }
  }
}
