package model.protocol;

public class BulkStringProtocol extends BaseProtocol<String>
{
  private final String CRLF = "\r\n";
  @Override
  public String serialize(String payload)
  {
    String[] strings = payload.split(",");
    int stringLength = strings.length;

    StringBuilder stringBuilder = new StringBuilder(CRLF);

    for (String s: strings) {
      stringBuilder.append(s);
      stringBuilder.append(CRLF);
    }
    return "$" + stringLength + stringBuilder;
  }
}
