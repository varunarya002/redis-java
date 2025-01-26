package handler.command;

import handler.server.ServerResponse;
import model.protocol.BaseProtocol;

import java.util.List;

public class PingCommandHandler extends BaseCommandHandler<String>
{
  private final String PAYLOAD = "PONG";

  public PingCommandHandler(BaseProtocol<String> protocol)
  {
    super(protocol);
  }

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    return buildResponse(PAYLOAD);
  }
}
