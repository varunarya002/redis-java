package handler.command;

import enums.RedisResponseEnums;
import model.server.ServerResponse;
import handler.storage.StorageHandler;
import model.protocol.BaseProtocol;

import java.util.List;

public class SetCommandHandler extends BaseCommandHandler<String>
{
  private static final String SET_EXPIRY_COMMAND = "PX";
  private final StorageHandler storageHandler;
  private String key;
  private String value;
  private long ttl = -1;

  public SetCommandHandler(BaseProtocol<String> protocol, StorageHandler storageHandler)
  {
    super(protocol);
    this.storageHandler = storageHandler;
  }

  private void fetchKeyValueFromArguments(List<String> arguments) {
    key = arguments.removeFirst();
    value = arguments.removeFirst();
    if (arguments.isEmpty()) return;

    if (arguments.removeFirst().toUpperCase().equals(SET_EXPIRY_COMMAND)) {
      ttl = Long.parseLong(arguments.removeFirst());
    }
  }

  @Override
  public ServerResponse<String> execute(List<String> arguments)
  {
    fetchKeyValueFromArguments(arguments);
    storageHandler.set(key, value, ttl);
    return buildResponse(RedisResponseEnums.OK.getResponse());
  }
}
