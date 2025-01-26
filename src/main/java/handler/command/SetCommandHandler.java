package handler.command;

import enums.RedisResponseEnums;
import handler.server.ServerResponse;
import handler.storage.StorageHandler;
import model.protocol.BaseProtocol;

import java.util.List;

public class SetCommandHandler extends BaseCommandHandler<String>
{
  private final StorageHandler storageHandler;
  private String key;
  private String value;

  public SetCommandHandler(BaseProtocol<String> protocol, StorageHandler storageHandler)
  {
    super(protocol);
    this.storageHandler = storageHandler;
  }

  private void fetchKeyValueFromArguments(List<String> arguments) {
    key = arguments.removeFirst();
    value = arguments.removeFirst();
  }

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    fetchKeyValueFromArguments(arguments);
    storageHandler.set(key, value);
    return buildResponse(RedisResponseEnums.OK.getResponse());
  }
}
