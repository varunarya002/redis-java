package handler.server;

import model.protocol.BaseProtocol;

public class ServerResponse<K>
{
  private K payload;
  private BaseProtocol protocol;

  public ServerResponse(K payload, BaseProtocol protocol)
  {
    this.payload = payload;
    this.protocol = protocol;
  }

  public String serialize() {
    return protocol.serialize(payload);
  }
}
