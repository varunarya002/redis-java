package model.command;

import handler.server.ServerResponse;
import model.protocol.SimpleStringProtocol;

public class PingCommand extends BaseCommand
{
  private String payload = "PONG";
  private SimpleStringProtocol stringProtocol = new SimpleStringProtocol();

  @Override
  public void validate() throws Exception
  {
    return;
  }

  @Override
  public ServerResponse execute()
  {
    return new ServerResponse<>(payload, stringProtocol);
  }
}
