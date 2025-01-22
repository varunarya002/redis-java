package model.protocol;

public class BulkStringProtocol extends BaseProtocol<String>
{
  private final String CRLF = "\r\n";
  @Override
  public String serialize(String payload)
  {
    return "$" + payload.length() + CRLF + payload + CRLF;
  }
}
