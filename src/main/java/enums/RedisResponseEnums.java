package enums;

public enum RedisResponseEnums
{
  OK("OK");

  final String response;

  RedisResponseEnums(String response)
  {
    this.response = response;
  }

  public String getResponse()
  {
    return response;
  }
}
