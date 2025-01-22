package model.command;

import handler.server.ServerResponse;
import model.protocol.BaseProtocol;
import model.protocol.BulkStringProtocol;

import java.util.List;

public class EchoCommand extends BaseCommand<String>
{
  private final BaseProtocol<String> stringProtocol = new BulkStringProtocol();

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    String payload = String.join(",", arguments);
    return new ServerResponse<>(payload, stringProtocol);
  }
}
