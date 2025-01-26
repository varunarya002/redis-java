package handler.command;

import handler.server.ServerResponse;
import model.protocol.BaseProtocol;

import java.util.List;

public class EchoCommandHandler extends BaseCommandHandler<String>
{
  public EchoCommandHandler(BaseProtocol<String> protocol)
  {
    super(protocol);
  }

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    String payload = String.join(",", arguments);
    return buildResponse(payload);
  }
}
