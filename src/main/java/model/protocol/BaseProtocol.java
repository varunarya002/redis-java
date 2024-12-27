package model.protocol;

public abstract class BaseProtocol<K>
{
  protected final String CRLF = "\r\n";

  public abstract String serialize(K payload);
}
