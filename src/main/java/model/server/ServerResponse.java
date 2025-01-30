package model.server;

import model.protocol.BaseProtocol;

public class ServerResponse<K>
{
  private K payload;
  private BaseProtocol protocol;

  private ServerResponse(Builder<K> builder)
  {
    this.payload = builder.payload;
    this.protocol = builder.protocol;
  }

  public static<T> Builder<T> builder() {
    return new Builder<>();
  }

  public String serialize() {
    return protocol.serialize(payload);
  }

  public static class Builder<K> {
    private K payload;
    private BaseProtocol protocol;

    public Builder<K> withPayload(K payload)
    {
      this.payload = payload;
      return this;
    }

    public Builder<K> withProtocol(BaseProtocol protocol)
    {
      this.protocol = protocol;
      return this;
    }

    public ServerResponse<K> build() {
      return new ServerResponse<>(this);
    }
  }
}
