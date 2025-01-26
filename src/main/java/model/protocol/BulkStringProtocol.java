package model.protocol;

public class BulkStringProtocol extends BaseProtocol<String>
{
  private final String CRLF = "\r\n";
  @Override
  public String serialize(String payload)
  {
    int payLoadLength = !payload.isEmpty() ? payload.length() : -1;
    return "$" + payLoadLength + CRLF + payload + CRLF;
  }
}
