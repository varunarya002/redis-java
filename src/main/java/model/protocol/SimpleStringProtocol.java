package model.protocol;

public class SimpleStringProtocol extends BaseProtocol<String>
{
  private static final String SIMPLE_STRING_PREFIX = "+";

  @Override
  public String serialize(String payload)
  {
    return SIMPLE_STRING_PREFIX + payload + this.CRLF;
  }
}
