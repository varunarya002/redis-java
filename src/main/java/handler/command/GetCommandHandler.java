package handler.command;

import handler.server.ServerResponse;
import handler.storage.StorageHandler;
import model.protocol.BaseProtocol;

import java.util.List;

public class GetCommandHandler extends BaseCommandHandler<String>
{
  private final StorageHandler storageHandler;
  public GetCommandHandler(BaseProtocol<String> protocol, StorageHandler storageHandler)
  {
    super(protocol);
    this.storageHandler = storageHandler;
  }

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    String key = arguments.removeFirst();
    String response = storageHandler.get(key);

    return buildResponse(response);
  }
}
