package model.command;

import handler.server.ServerResponse;
import model.protocol.BaseProtocol;
import model.protocol.SimpleStringProtocol;

import java.util.List;

public class PingCommand extends BaseCommand<String>
{
  private String payload = "PONG";
  private final BaseProtocol<String> stringProtocol = new SimpleStringProtocol();

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    return new ServerResponse<>(payload, stringProtocol);
  }
}
